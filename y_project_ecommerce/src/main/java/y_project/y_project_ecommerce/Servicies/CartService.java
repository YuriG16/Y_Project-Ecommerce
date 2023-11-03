package y_project.y_project_ecommerce.Servicies;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import y_project.UTILITY.UserMapper;
import y_project.UTILITY.DTOs.CartDTO;
import y_project.UTILITY.DTOs.ProductDTO;
import y_project.UTILITY.DTOs.ProductInCartDTO;
import y_project.UTILITY.DTOs.PurchaseDTO;
import y_project.UTILITY.DTOs.UsersDTO;
import y_project.UTILITY.Exceptions.ProductException.CartIsEmptyException;
import y_project.UTILITY.Exceptions.ProductException.ProductNotInCartException;
import y_project.UTILITY.Exceptions.ProductException.QuantityNotEnoughException;
import y_project.UTILITY.Exceptions.UsersException.BudgetNotEnoughException;
import y_project.y_project_ecommerce.entities.Cart;
import y_project.y_project_ecommerce.entities.Product;
import y_project.y_project_ecommerce.entities.ProductInCart;
import y_project.y_project_ecommerce.entities.ProductInPurchase;
import y_project.y_project_ecommerce.entities.Purchase;
import y_project.y_project_ecommerce.entities.Users;
import y_project.y_project_ecommerce.repositories.CartRepository;
import y_project.y_project_ecommerce.repositories.ProductInCartRepository;
import y_project.y_project_ecommerce.repositories.ProductInPurchaseRepository;
import y_project.y_project_ecommerce.repositories.ProductRepository;
import y_project.y_project_ecommerce.repositories.PurchaseRepository;
import y_project.y_project_ecommerce.repositories.UsersRepository;

@Service
@RequiredArgsConstructor
public class CartService {
    
    private final UsersService us;
    private final ProductService ps;
    private final ProductInCartRepository pInCartR;
    private final CartRepository cr;
    private final UserMapper um;
    private final PurchaseRepository purchaseR;
    private final ProductInPurchaseRepository pInPurchaseR;
    private final ProductRepository pr;
    private final UsersRepository ur;

    public Cart getCart (String email) throws RuntimeException {
        Users u = us.getUsers(email);
        Cart c = u.getCart();
        return c;
    }

    @Transactional
    public CartDTO addToCart (String email, String codProdotto, int q) throws RuntimeException {
        Cart c = getCart(email);
        Product p = ps.getProduct(codProdotto);

        ProductInCart pInCart = pInCartR.findByCAndP(c, p);

        if (pInCart != null) {
            pInCart.setQ(q+pInCart.getQ());
            pInCart.setCostoTotale(pInCart.getQ()*pInCart.getP().getPrezzo());
            pInCart = pInCartR.save(pInCart);

            c = cr.save(c);
            
        } else {
            ProductInCart pInCart2 = new ProductInCart(null, p, c, q, null);
            pInCart2.setCostoTotale(pInCart2.getQ()*pInCart2.getP().getPrezzo());
            pInCart2 = pInCartR.save(pInCart2);
            c.getPListInCart().add(pInCart2);

            c = cr.save(c);
        }
        
        CartDTO cd = um.cartToCartDTO(c);
        return cd;
    }

    public CartDTO modifyQ (String email, String codProdotto, int q) throws RuntimeException {
        Cart c = getCart(email);
        Product p = ps.getProduct(codProdotto);
        ProductInCart pInCart = pInCartR.findByCAndP(c, p);
        if (pInCart == null) {
            throw new ProductNotInCartException();
        } else {
            if (q > 0) {
                pInCart.setQ(q);
                pInCart.setCostoTotale(q*pInCart.getP().getPrezzo());
                pInCart = pInCartR.save(pInCart);
            } else {
                pInCartR.delete(pInCart);
            }
        }
        
        c = cr.save(c);
        CartDTO cd = um.cartToCartDTO(c);
        return cd;
    }

    public CartDTO removeToCart (String email, String codProdotto) throws RuntimeException {
        Cart c = getCart(email);
        Product p = ps.getProduct(codProdotto);
        ProductInCart pInCart = pInCartR.findByCAndP(c, p);
        if (pInCart == null) {
            throw new ProductNotInCartException();
        } else {
            pInCartR.delete(pInCart);
        }

        c = cr.save(c);
        CartDTO cd = um.cartToCartDTO(c);
        return cd;
    }

    @Transactional
    public UsersDTO buy (String email, String codProdotto) throws RuntimeException {
        Users u = us.getUsers(email);
        Cart c = getCart(email);
        Product p = ps.getProduct(codProdotto);
        ProductInCart pInCart = pInCartR.findByCAndP(c, p);
        if (pInCart == null) {
            throw new ProductNotInCartException();
        } if (u.getBudget() < pInCart.getCostoTotale()) {
            throw new BudgetNotEnoughException();
        } else {
            u.setBudget(u.getBudget()-pInCart.getCostoTotale());
        }

        if (pInCart.getP().getQd() >= pInCart.getQ()) {
            p.setQd(p.getQd()-pInCart.getQ());
        } else {
            throw new QuantityNotEnoughException();
        }

        Purchase purchase = new Purchase(null, u, new ArrayList<>(), null);
        purchase = purchaseR.save(purchase);
        u.setPurchase(purchase);

        ProductInPurchase pInPurchase = new ProductInPurchase(null, pInCart.getQ(), p, pInCart.getCostoTotale());
        pInPurchase = pInPurchaseR.save(pInPurchase);
 
        u.getPurchase().getPListPurchase().add(pInPurchase);
        purchase = purchaseR.save(purchase);
        pInCartR.delete(pInCart);

        p = pr.save(p);
        u = ur.save(u);

        UsersDTO uDto = um.usersToUsersDTO(u);
        return uDto;        
    }

    @Transactional
    public UsersDTO buyAll (String email) throws RuntimeException {
        Users u = us.getUsers(email);
        Cart c = getCart(email);

        if (c == null) {
            throw new CartIsEmptyException();
        }

        for (ProductInCart pInCart : c.getPListInCart()) {
            Product p = pInCart.getP();

            if (u.getBudget() < pInCart.getCostoTotale()) {
                throw new BudgetNotEnoughException();
            } else {
                u.setBudget(u.getBudget()-pInCart.getCostoTotale());
            } if (p.getQd() < pInCart.getQ()) {
                throw new QuantityNotEnoughException();
            } else {
                p.setQd(p.getQd()-pInCart.getQ());
            }

            Purchase purchase = new Purchase(null, u, new ArrayList<>(), null);
            purchase = purchaseR.save(purchase);
            u.setPurchase(purchase);

            ProductInPurchase pInPurchase = new ProductInPurchase(null, pInCart.getQ(), p, pInCart.getCostoTotale());
            pInPurchase = pInPurchaseR.save(pInPurchase);

            u.getPurchase().getPListPurchase().add(pInPurchase);

            purchase = purchaseR.save(purchase);
            pInCartR.delete(pInCart);

        }
        c.getPListInCart().clear();
        
        u = ur.save(u);
        UsersDTO uDto = um.usersToUsersDTO(u);
        return uDto;
    }
    
}

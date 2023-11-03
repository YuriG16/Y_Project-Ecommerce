package y_project.UTILITY;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import y_project.UTILITY.DTOs.CartDTO;
import y_project.UTILITY.DTOs.ProductDTO;
import y_project.UTILITY.DTOs.ProductInCartDTO;
import y_project.UTILITY.DTOs.ProductInPurchaseDTO;
import y_project.UTILITY.DTOs.PurchaseDTO;
import y_project.UTILITY.DTOs.UsersDTO;
import y_project.y_project_ecommerce.entities.Cart;
import y_project.y_project_ecommerce.entities.Product;
import y_project.y_project_ecommerce.entities.ProductInCart;
import y_project.y_project_ecommerce.entities.ProductInPurchase;
import y_project.y_project_ecommerce.entities.Purchase;
import y_project.y_project_ecommerce.entities.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UsersDTO usersToUsersDTO (Users u);

    CartDTO cartToCartDTO (Cart c);

    ProductInCartDTO productInCartToProductInCartDTO (ProductInCart pInCart);

    ProductDTO productToProductDTO (Product p);

    PurchaseDTO purchaseToPurchaseDTO (Purchase purchase);

    ProductInPurchaseDTO productInPurchaseToProductInPurchaseDTO (ProductInPurchase pInPurchase);
}

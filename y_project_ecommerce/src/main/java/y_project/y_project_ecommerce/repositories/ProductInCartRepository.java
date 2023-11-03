package y_project.y_project_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import y_project.y_project_ecommerce.entities.Cart;
import y_project.y_project_ecommerce.entities.Product;
import y_project.y_project_ecommerce.entities.ProductInCart;
import java.util.List;


public interface ProductInCartRepository extends JpaRepository<ProductInCart, Integer> {

    List<ProductInCart> findByP(Product product);
    ProductInCart findByCAndP(Cart c, Product p);
    
}

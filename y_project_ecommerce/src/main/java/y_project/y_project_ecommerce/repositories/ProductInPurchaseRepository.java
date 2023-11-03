package y_project.y_project_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import y_project.y_project_ecommerce.entities.ProductInPurchase;

public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Integer> {
    
}

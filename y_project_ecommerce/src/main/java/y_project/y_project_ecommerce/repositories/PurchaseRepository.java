package y_project.y_project_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import y_project.y_project_ecommerce.entities.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    
}

package y_project.y_project_ecommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import y_project.y_project_ecommerce.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByCodProdotto (String codProdotto);
    Boolean existsByCodProdotto (String codProdotto);
    Boolean existsByCategoria(String categoria);

    Page<Product> findByCategoria(String categoria, PageRequest pageRequest);
    Page<Product> findByCategoriaAndPrezzoBetween(String categoria, Double pMin, Double pMax, PageRequest pageRequest);
    Page<Product> findByTitoloContaining(String keyword, PageRequest pageRequest);
    Page<Product> findByTitoloContainingAndPrezzoBetween(String keyword, Double pMin, Double pMax, PageRequest pageRequest);
    
}

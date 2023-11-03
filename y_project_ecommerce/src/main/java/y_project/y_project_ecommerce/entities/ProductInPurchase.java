package y_project.y_project_ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "productInPurchase")
public class ProductInPurchase {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Integer id;

    @Column (name = "quantity")
    private int q;

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product p;

    @Column (name = "prezzoPurchase")
    private double prezzoPurchase;

}

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
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "productInCart")
public class ProductInCart {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product p;

    @ManyToOne
    @JsonIgnore
    @JoinColumn (name = "cart_id")
    private Cart c;

    @Column (name = "quantity")
    private int q;

    @Column (name = "costoTot")
    private Double costoTotale;
    
}

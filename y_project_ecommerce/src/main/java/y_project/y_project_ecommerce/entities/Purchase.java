package y_project.y_project_ecommerce.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Integer id;

    @JsonIgnore
    @OneToOne
    @JoinColumn (name = "Users_id")
    private Users u;

    @OneToMany
    @JoinColumn (name = "productInCart_id")
    private List<ProductInPurchase> pListPurchase = new ArrayList<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "purchase_time")
    private Date purchaseTime;
}
    

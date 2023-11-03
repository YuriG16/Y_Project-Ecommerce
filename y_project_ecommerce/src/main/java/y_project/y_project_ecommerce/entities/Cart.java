package y_project.y_project_ecommerce.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "cart")
public class Cart {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "Id")
    private Integer id;

    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn (name = "users_id")
    private Users u;

    @OneToMany
    @JoinColumn (name = "productInCart_id")
    private List<ProductInCart> pListInCart = new ArrayList<>();

    
}

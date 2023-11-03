package y_project.y_project_ecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "product")
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "Id")
    private Integer id;

    @Column (name = "titolo")
    private String titolo;

    @Column (name = "descrizione")
    private String descrizione;

    @Column (name = "codProdotto", nullable = false, unique = true)
    private String codProdotto;

    @Column (name = "categoria")
    private String categoria;

    @Column (name = "prezzo")
    private double prezzo;

    @Column (name = "qDisponibile")
    private int qd = 1000;

    @Version
    @Column(name = "version")
    private long version;
    
}

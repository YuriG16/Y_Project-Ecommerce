package y_project.UTILITY.DTOs;

import lombok.Data;

@Data
public class ProductDTO {

    private String titolo;
    private String descrizione;
    private String codProdotto;
    private String categoria;
    private double prezzo;
    private int qd;
    
}

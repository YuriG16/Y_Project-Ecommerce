package y_project.UTILITY.DTOs;

import lombok.Data;

@Data
public class ProductInCartDTO {

    private ProductDTO p;
    private int q;
    private Double costoTotale;
    
}

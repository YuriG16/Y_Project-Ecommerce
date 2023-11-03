package y_project.UTILITY.DTOs;

import lombok.Data;

@Data
public class ProductInPurchaseDTO {

    private ProductDTO p;
    private int q;
    private Double prezzoPurchase;

}

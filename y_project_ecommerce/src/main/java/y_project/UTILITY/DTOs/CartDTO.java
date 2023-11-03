package y_project.UTILITY.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    
    private List<ProductInCartDTO> pListInCart;
}

package y_project.UTILITY.DTOs;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseDTO {

    private List<ProductInPurchaseDTO> pListPurchase;
    private Date purchaseTime;
    
}

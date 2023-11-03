package y_project.UTILITY.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import y_project.y_project_ecommerce.entities.Cart;
import y_project.y_project_ecommerce.entities.Purchase;
import y_project.y_project_ecommerce.entities.Users;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsersDTO {
    
    private String nome;
    private String cognome;
    private String email;
    private Double budget = 1000.0;
    private CartDTO cart;
    private PurchaseDTO purchase;
}

// togliere il costruttore e inserire i DTO
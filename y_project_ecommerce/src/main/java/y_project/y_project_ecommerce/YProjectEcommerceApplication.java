package y_project.y_project_ecommerce;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import y_project.y_project_ecommerce.entities.Product;
import y_project.y_project_ecommerce.entities.ProductInCart;
import y_project.y_project_ecommerce.entities.Role;

import lombok.RequiredArgsConstructor;
import y_project.UTILITY.DTOs.ProductDTO;
import y_project.UTILITY.DTOs.ProductInCartDTO;
import y_project.y_project_ecommerce.Security.AuthService;
import y_project.y_project_ecommerce.Security.RegisterRequest;
import y_project.y_project_ecommerce.Servicies.ProductService;
import y_project.y_project_ecommerce.entities.Users;
import y_project.y_project_ecommerce.entities.Cart;
import y_project.y_project_ecommerce.repositories.CartRepository;
import y_project.y_project_ecommerce.repositories.ProductInCartRepository;
import y_project.y_project_ecommerce.repositories.UsersRepository;

@RequiredArgsConstructor
@SpringBootApplication
@ComponentScan(basePackages = "y_project")
public class YProjectEcommerceApplication {
	private final UsersRepository ur;
	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;
	private final ProductService ps;
	private final CartRepository cr;
	private final ProductInCartRepository pInCartR;


	public static void main(String[] args) {
		SpringApplication.run(YProjectEcommerceApplication.class, args);
	}

	@Bean
	CommandLineRunner  run () {
		return args -> {
			Users u = new Users(null, "Luca", "Verdi", "lverdi@email.com", passwordEncoder.encode("1234"), null, 1000, null, Role.USER);
			ur.save(u);
			Cart c = new Cart(null, u, new ArrayList<>());
			c = cr.save(c);
			u.setCart(c);
			u = ur.save(u);

			authService.register(new RegisterRequest("Mario", "Rossi", "mrossi@email.com", "3456"));
			
			Product p = new Product(null, "mouse", "bluetooth", "1M", "elettronica", 10, 1000, 0);
			ps.addProduct(p);

			ProductInCart pInCart = new ProductInCart(null, p, c, 5, null);
			pInCart.setCostoTotale(pInCart.getQ()*pInCart.getP().getPrezzo());
			pInCartR.save(pInCart);
			c.getPListInCart().add(pInCart);
			
			c = cr.save(c);

		};
		
		
	}





}

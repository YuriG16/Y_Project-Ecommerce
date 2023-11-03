package y_project.y_project_ecommerce.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import y_project.y_project_ecommerce.Security.JwtService;
import y_project.y_project_ecommerce.Servicies.CartService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    
    private final CartService cs;
    private final JwtService jwtService;

    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @GetMapping("/getCart")
    public ResponseEntity getCart (HttpServletRequest request) {
        String email = jwtService.extractEmailFromRequest(request);
        return ResponseEntity.ok(cs.getCart(email));
    }

    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @PostMapping("/addToCart")
    public ResponseEntity addToCart (HttpServletRequest request, @RequestParam("codProdotto") String codProdotto, @RequestParam("q") int q) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity (cs.addToCart(email, codProdotto, q), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @PutMapping("/modifyQ")
    public ResponseEntity modifyQ (HttpServletRequest request, @RequestParam("codProdotto") String codProdotto, @RequestParam("q") int q) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity (cs.modifyQ(email, codProdotto, q), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority ('USER')")
    @DeleteMapping("/removeToCart")
    public ResponseEntity removeToCart (HttpServletRequest request, @RequestParam("codProdotto") String codProdotto) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity (cs.removeToCart(email, codProdotto), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority ('USER')")
    @GetMapping("/buy")
    public ResponseEntity buy (HttpServletRequest request, @RequestParam("codProdotto") String codProdotto) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity (cs.buy(email, codProdotto), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority ('USER')")
    @GetMapping("/buyAll")
    public ResponseEntity buyAll (HttpServletRequest request) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity (cs.buyAll(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }
}

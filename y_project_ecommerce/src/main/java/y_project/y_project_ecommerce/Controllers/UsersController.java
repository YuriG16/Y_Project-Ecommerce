package y_project.y_project_ecommerce.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import y_project.y_project_ecommerce.Security.AuthService;
import y_project.y_project_ecommerce.Security.AuthenticationRequest;
import y_project.y_project_ecommerce.Security.AuthenticationResponse;
import y_project.y_project_ecommerce.Security.JwtService;
import y_project.y_project_ecommerce.Security.RegisterRequest;
import y_project.y_project_ecommerce.Servicies.UsersService;
import y_project.y_project_ecommerce.entities.Users;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService us;
    private final JwtService jwtService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity findAllUsers () {
        return ResponseEntity.ok(us.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllAdm")
    public ResponseEntity findAllAdm () {
        return ResponseEntity.ok(us.findAllAdmin());
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllUsr")
    public ResponseEntity findAllUsr () {
        return ResponseEntity.ok(us.findAllUser());
    }

    // getUsers per ADMIN
    @PreAuthorize("hasAuthotity('ADMIN')")
    @GetMapping("/getUsers")
    public ResponseEntity getUsers (HttpServletRequest request) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity(us.getUsers(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    //viewAccount per USER
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/viewAccount")
    public ResponseEntity viewAccount (HttpServletRequest request) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity(us.viewAccount(email), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/modifyUsers")
    public ResponseEntity modifyUsers (HttpServletRequest request, @RequestParam("nome") String nome, @RequestParam("cognome") String cognome) {
        try {
            String email = jwtService.extractEmailFromRequest(request);
            return new ResponseEntity(us.modifyUsers(email, nome, cognome), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteUsers")
    public ResponseEntity deleteUsers (@RequestParam("email") String email) {
        return ResponseEntity.ok(us.deleteUsers(email));

    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/upgradeRole")
    public ResponseEntity upgradeRole (@RequestParam("email") String email) {
        return ResponseEntity.ok(us.upgradeRole(email));
    }

    
    
}

package y_project.y_project_ecommerce.Security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.var;
import y_project.UTILITY.Exceptions.UsersException.UsersAlreadyExistException;
import y_project.UTILITY.Exceptions.UsersException.UsersNotExistException;
import y_project.y_project_ecommerce.entities.Cart;
import y_project.y_project_ecommerce.entities.Role;
import y_project.y_project_ecommerce.entities.Users;
import y_project.y_project_ecommerce.repositories.CartRepository;
import y_project.y_project_ecommerce.repositories.UsersRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository ur;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CartRepository cr;

    public boolean isValidEmail (String email) {
        String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";

        return email.matches(regex);
    }
    
    public AuthenticationResponse register(RegisterRequest request) throws RuntimeException {

        if (!isValidEmail(request.getEmail())) {
            throw new UsersNotExistException();
        }

        if (ur.existsByEmail(request.getEmail())) {
            throw new UsersAlreadyExistException();
        }
        
        Users u = Users.builder()
        .nome(request.getNome())
        .cognome(request.getCognome())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();
        ur.save(u);
        Cart c = new Cart(null, u, new ArrayList<>());
        c = cr.save(c);
        u.setCart(c);
        u = ur.save(u);

        
        var jwtToken = jwtService.generateToken(u);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var u = ur.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(u);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    
    public AuthenticationResponse registerUsers(RegisterRequest request) {
        
        Users u = Users.builder()
        .nome(request.getNome())
        .cognome(request.getCognome())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .budget(2000.0)
        .role(Role.USER)
        .build();

        ur.save(u);
        Cart c = new Cart(null, u, new ArrayList<>());
        c = cr.save(c);
        u.setCart(c);
        u = ur.save(u);
        var jwtToken = jwtService.generateToken(u);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticateUsers(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var u = ur.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(u);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    
}

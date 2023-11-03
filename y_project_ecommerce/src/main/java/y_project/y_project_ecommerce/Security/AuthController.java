package y_project.y_project_ecommerce.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping ("/register")
    public ResponseEntity <AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));


    }
    
    @PostMapping ("/authenticate")
    public ResponseEntity <AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
        
    }

    
    @PostMapping ("/registerUsers")
    public ResponseEntity <AuthenticationResponse> registerUsers (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUsers(request));


    }
    
    @PostMapping ("/authenticateUsers")
    public ResponseEntity <AuthenticationResponse> authenticateUsers (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticateUsers(request));
        
    }
    
}

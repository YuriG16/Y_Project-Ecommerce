package y_project.y_project_ecommerce.Servicies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import y_project.UTILITY.UserMapper;
import y_project.UTILITY.DTOs.UsersDTO;
import y_project.UTILITY.Exceptions.UsersException.RoleIsAlreadyAdminException;
import y_project.UTILITY.Exceptions.UsersException.UsersNotExistException;
import y_project.y_project_ecommerce.Security.AuthenticationRequest;
import y_project.y_project_ecommerce.Security.AuthenticationResponse;
import y_project.y_project_ecommerce.Security.JwtService;
import y_project.y_project_ecommerce.Security.RegisterRequest;
import y_project.y_project_ecommerce.entities.Role;
import y_project.y_project_ecommerce.entities.Users;
import y_project.y_project_ecommerce.repositories.UsersRepository;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository ur;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper um;

    public List<Users> findAll () {
        List<Users> uList = ur.findAll();       
        return uList;
    }

    public List<UsersDTO> findAllAdmin() {
        List<Users> uList = ur.findAll();
        List<UsersDTO> uListDTO = new ArrayList<>();
        for (Users u : uList) {
            if (u.getRole()==Role.ADMIN) {
                UsersDTO uDto = um.usersToUsersDTO(u);
                uListDTO.add(uDto);
            }
        }
        return uListDTO;
    }

    public List<UsersDTO> findAllUser () {
        List<Users> uList = ur.findAll();
        List<UsersDTO> uDtoList = new ArrayList<>();
        for (Users u : uList) {
            if (u.getRole() == Role.USER) {
                UsersDTO uDto = um.usersToUsersDTO(u);
                uDtoList.add(uDto);
            }
        }
        return uDtoList;
    }


    public Users getUsers (String email) throws RuntimeException {
        Users u = ur.findByEmail(email);
        if (email == null) {
            throw new UsersNotExistException();
        }
        return u;
    }

    public UsersDTO viewAccount (String email) {
        Users u = getUsers(email);
        UsersDTO uDto = um.usersToUsersDTO(u);
        return uDto;
    }

    public UsersDTO modifyUsers (String email, String nome, String cognome) throws RuntimeException {
        Users u = getUsers(email);
        u.setNome(nome);
        u.setCognome(cognome);
        u = ur.save(u);
        UsersDTO uDto = um.usersToUsersDTO(u);
        return uDto;
    }

    public String deleteUsers (String email) throws RuntimeException {
        Users u = getUsers(email);
        ur.delete(u);
        return email + " eliminato con successo";
    }

    public UsersDTO upgradeRole (String email) throws RuntimeException {
        Users u = getUsers(email);
        if (u.getRole() == Role.ADMIN) {
            throw new RoleIsAlreadyAdminException();
        }
        u.setRole(Role.ADMIN);
        u = ur.save(u);
        UsersDTO uDto = um.usersToUsersDTO(u);
        return uDto;
    }

    

    
}

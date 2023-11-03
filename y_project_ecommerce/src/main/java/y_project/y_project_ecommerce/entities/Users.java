package y_project.y_project_ecommerce.entities;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "Id")
    private Integer id;

    @Column (name = "nome")
    private String nome;

    @Column (name = "cognome")
    private String cognome;

    @Column (name = "email", nullable = false, unique = true)
    private String email;

    @Column (name = "password")
    private String password;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn (name = "cart_id")
    private Cart cart;

    @Column (name= "budget")
    private double budget = 500.0;

    @OneToOne
    @JoinColumn (name = "purchase_id")
    private Purchase purchase;

    @Enumerated (EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
       return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}

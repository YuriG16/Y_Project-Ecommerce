package y_project.y_project_ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import y_project.y_project_ecommerce.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    public Users findByEmail (String email);
    public boolean existsByEmail (String email);
    
}

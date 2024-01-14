package ACCC.ACCCproject.repository;

import java.util.Optional;

import ACCC.ACCCproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, String>{
    User findByUserEmail(String userEmail);
    Optional<User> findUserByuserEmail(String email);
}

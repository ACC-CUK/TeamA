package ACCC.ACCCproject.repository;

import ACCC.ACCCproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, String>{
    User findByUserEmail(String userEmail);
}

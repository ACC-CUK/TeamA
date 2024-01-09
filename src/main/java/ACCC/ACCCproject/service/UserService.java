package ACCC.ACCCproject.service;

import ACCC.ACCCproject.model.User;
import ACCC.ACCCproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticateUser(String userEmail, String password){
        User user = userRepository.findByUserEmail(userEmail);

        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return true;
        }
        if(user!=null){
            System.out.println("유저는 있음");
        }
        else{
            System.out.println("유저 없음");
        }
        return false;
    }
}

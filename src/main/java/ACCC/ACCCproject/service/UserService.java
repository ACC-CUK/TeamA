package ACCC.ACCCproject.service;

import java.util.Optional;
import java.util.Random;

import ACCC.ACCCproject.controller.dto.GetEmailRes;
import ACCC.ACCCproject.model.User;
import ACCC.ACCCproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Random random = new Random();

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

    public GetEmailRes getEmailCode(String email){
        User user = getEmail(email);
        user.setUserEmail(email);
        validEmail(user.getUserEmail());
        user.setRegistCode(generateRandomString(6));
        userRepository.save(user);
        return new GetEmailRes(user.getUserEmail());
    }
    private User getEmail(String email) {
        Optional<User> test = userRepository.findUserByuserEmail(email);
        return test.orElse(new User());
    }
    private void validEmail(String email) {
        if(!email.endsWith("@catholic.ac.kr")){
            throw new RuntimeException("Invalid email");
        }
    }
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}

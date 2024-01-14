package ACCC.ACCCproject.service;

import static org.springframework.security.core.context.SecurityContextHolder.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

import ACCC.ACCCproject.controller.dto.GetEmailRes;
import ACCC.ACCCproject.model.User;
import ACCC.ACCCproject.repository.UserRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;
    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Random random = new Random();

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JavaMailSender emailSender){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
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
        sendEmail(user);
        userRepository.save(user);
        return new GetEmailRes(user.getUserEmail());
    }

    private void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getUserEmail());
        message.setSubject("ACCC 회원가입 인증 메일입니다.");
        message.setText("인증번호는 " + user.getRegistCode() + "입니다.");
        emailSender.send(message);
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

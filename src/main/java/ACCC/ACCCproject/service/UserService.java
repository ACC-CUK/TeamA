package ACCC.ACCCproject.service;

import java.util.Optional;
import java.util.Random;

import ACCC.ACCCproject.controller.dto.GetEmailRes;
import ACCC.ACCCproject.controller.dto.PostRegisterReq;
import ACCC.ACCCproject.controller.dto.PostRegisterRes;
import ACCC.ACCCproject.model.User;
import ACCC.ACCCproject.repository.UserRepository;
import ACCC.ACCCproject.dto.GetUserDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public PostRegisterRes register(PostRegisterReq data) {
        User user = userRepository.findByUserEmail(data.getEmail());
        validCode(user, data.getRegistCode());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setUserName(data.getUserName());
        user.setGender(data.getGender());
        user.setDetail(data.getDetail());
        user.setPhoneNumber(data.getPhoneNumber());
        user.setDepartmentNumber(data.getDepartmentNumber());
        userRepository.save(user);
        return new PostRegisterRes(user.getUserEmail());
    }

    private void validCode(User user, String registCode) {
        if(!user.getRegistCode().equals(registCode)){
            throw new RuntimeException("Invalid code");
        }
    }

    public ResponseEntity<GetUserDto> getRegisterCode(String registCode) {
        User user = userRepository.findByRegistCode(registCode);
        if (user != null) {
            //dto로 변환
            GetUserDto userDto = new GetUserDto(user);


            //유저 output 찍기
            System.out.println(userDto);
            //로그 찍기
            System.out.println("유저 찾음");
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

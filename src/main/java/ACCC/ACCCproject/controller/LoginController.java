package ACCC.ACCCproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import ACCC.ACCCproject.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/login/index";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "index";
    }

    @PostMapping("/users/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> requestMap, Model model) {
        Map<String, String> responseMap = new HashMap<>();

        String userEmail = requestMap.get("userEmail");
        String password = requestMap.get("password");

        if (userEmail == null || password == null || userEmail.isEmpty() || password.isEmpty()) {
            // 아이디 또는 비밀번호가 입력되지 않은 경우 처리
            //System.out.println("Email or password is empty.");
            responseMap.put("error", "Email or password is empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }

        if (!userService.authenticateUser(userEmail, password)) {
            // 아이디와 비밀번호가 일치하지 않는 경우 처리
            //System.out.println("Invalid email or password.");
            responseMap.put("error", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
        }

        // 로그인 성공 시 처리
        responseMap.put("success", "Login success.");
        return ResponseEntity.ok(responseMap);
    }
}

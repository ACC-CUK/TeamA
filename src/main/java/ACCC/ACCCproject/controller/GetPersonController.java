package ACCC.ACCCproject.controller;

import ACCC.ACCCproject.model.User;
import ACCC.ACCCproject.service.UserService;
import org.springframework.http.ResponseEntity;
import ACCC.ACCCproject.dto.GetUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GetPersonController {
    private UserService userService;
    @GetMapping("/user/{registCode}")
    public ResponseEntity<GetUserDto> getPersonPage(@PathVariable String registCode) {
        return userService.getRegisterCode(registCode);
    }
}


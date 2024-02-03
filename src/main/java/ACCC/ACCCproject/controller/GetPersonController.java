package ACCC.ACCCproject.controller;

import ACCC.ACCCproject.model.User;
import ACCC.ACCCproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ACCC.ACCCproject.controller.dto.GetUserDto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetPersonController {
    private UserService userService;

    @Autowired
    public GetPersonController(UserService userService) {
        this.userService = userService;
    }

    //request param으로 받아오기

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        // Your logic here
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @ResponseBody
    @GetMapping("/user/")
    public ResponseEntity<GetUserDto> getPersonPage(@RequestParam("registCode") String registCode) {
        return userService.getRegisterCode(registCode);
    }
}


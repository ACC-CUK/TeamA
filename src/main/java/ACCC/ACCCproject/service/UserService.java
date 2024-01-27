package ACCC.ACCCproject.service;

import ACCC.ACCCproject.model.User;
import ACCC.ACCCproject.repository.UserRepository;
import ACCC.ACCCproject.controller.dto.GetUserDto;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
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

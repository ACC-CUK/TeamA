package ACCC.ACCCproject.controller.dto;
import ACCC.ACCCproject.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserDto {

    private String userEmail;
    private String password;
    private String registCode;
    private String userName;
    private String gender;
    private String detail;
    private String phoneNumber;
    private Long departmentNumber;

    // 생성자, 게터, 세터 등 필요한 메서드 추가
    public GetUserDto(User user) {
        this.userEmail = user.getUserEmail();
        this.password = user.getPassword();
        this.registCode = user.getRegistCode();
        this.userName = user.getUserName();
        this.gender = user.getGender();
        this.detail = user.getDetail();
        this.phoneNumber = user.getPhoneNumber();
        this.departmentNumber = user.getDepartmentNumber();
    }
}

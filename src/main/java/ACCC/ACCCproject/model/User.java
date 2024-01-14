package ACCC.ACCCproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User{
    @Id
    private String userEmail;
    private String password;

    @Column(name = "regist_code", nullable = false)
    private String registCode;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "detail")
    private String detail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "department_number")
    private Long departmentNumber;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

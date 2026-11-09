package edu.javaapp.member;

import jakarta.validation.constraints.*;

public class MemberForm {
    @NotBlank(message = "이름을 입력하세요.")
    @Size(max = 50, message = "이름은 50자 이내입니다.")
    private String name;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식을 확인하세요.")
    @Size(max = 120, message = "이메일은 120자 이내입니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp = "[0-9+() -]{7,20}", message = "전화번호는 숫자와 기호로 7~20자입니다.")
    private String phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}

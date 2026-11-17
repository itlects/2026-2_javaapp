package week07.member.dto;

public class MemberDTO {

    private int memberId;
    private String name;
    private String phone;
    private String email;
    private String grade;

    public MemberDTO() {
    }

    public MemberDTO(
            int memberId,
            String name,
            String phone,
            String email,
            String grade) {

        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.grade = grade;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

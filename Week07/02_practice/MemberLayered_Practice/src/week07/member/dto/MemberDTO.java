package week07.member.dto;

public class MemberDTO {
    private int memberId;
    private String name;
    private String phone;
    private String email;
    private String grade;
    public MemberDTO() {}
    public MemberDTO(int memberId,String name,String phone,String email,String grade){
        this.memberId=memberId; this.name=name; this.phone=phone; this.email=email; this.grade=grade;
    }
    public int getMemberId(){return memberId;} public void setMemberId(int v){memberId=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getGrade(){return grade;} public void setGrade(String v){grade=v;}
}

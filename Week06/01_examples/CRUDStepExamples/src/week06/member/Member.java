package week06.member;
public class Member {
  private String memberId,name,phone,email;
  public Member(String memberId,String name,String phone,String email){this.memberId=memberId;this.name=name;this.phone=phone;this.email=email;}
  public String getMemberId(){return memberId;} public String getName(){return name;}
  public String getPhone(){return phone;} public String getEmail(){return email;}
}
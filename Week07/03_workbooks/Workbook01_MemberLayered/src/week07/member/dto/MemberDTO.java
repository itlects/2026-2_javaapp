package week07.member.dto;
public class MemberDTO {
 private int memberId; private String name,phone,email,grade;
 public MemberDTO(){} public MemberDTO(int id,String n,String p,String e,String g){memberId=id;name=n;phone=p;email=e;grade=g;}
 public int getMemberId(){return memberId;} public void setMemberId(int v){memberId=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getGrade(){return grade;} public void setGrade(String v){grade=v;}
}
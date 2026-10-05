package week05.workbook;
import java.util.*;
public class MemberApp {
 public static void main(String[] args)throws Exception{
  Scanner sc=new Scanner(System.in); MemberDAO dao=new MemberDAO();
  while(true){
   System.out.print("\n==== 회원 관리 ====\n1. 회원 목록\n2. 회원 등록\n3. 이메일 수정\n4. 회원 삭제\n0. 종료\n선택 > ");
   int m=Integer.parseInt(sc.nextLine());
   if(m==0) break;
   if(m==1) dao.findAll();
   else if(m==2){System.out.print("ID: ");String id=sc.nextLine();System.out.print("PW: ");String pw=sc.nextLine();System.out.print("이름: ");String n=sc.nextLine();System.out.print("Email: ");String e=sc.nextLine();System.out.println(dao.insert(id,pw,n,e)+"행 등록");}
   else if(m==3){System.out.print("ID: ");String id=sc.nextLine();System.out.print("새 Email: ");String e=sc.nextLine();System.out.println(dao.updateEmail(id,e)+"행 수정");}
   else if(m==4){System.out.print("ID: ");System.out.println(dao.delete(sc.nextLine())+"행 삭제");}
  }
  sc.close();
 }
}
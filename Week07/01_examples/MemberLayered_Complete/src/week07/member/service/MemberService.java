package week07.member.service;

import java.sql.SQLException;
import java.util.List;
import week07.member.dao.MemberDAO;
import week07.member.dto.MemberDTO;

public class MemberService {
    private final MemberDAO dao=new MemberDAO();
    public List<MemberDTO> getMembers() throws SQLException { return dao.findAll(); }
    public void addMember(MemberDTO m) throws SQLException { validate(m); dao.insert(m); }
    public void updateMember(MemberDTO m) throws SQLException { validate(m); if(m.getMemberId()<=0) throw new IllegalArgumentException("수정할 회원을 선택하세요."); dao.update(m); }
    public void deleteMember(int id) throws SQLException { if(id<=0) throw new IllegalArgumentException("삭제할 회원을 선택하세요."); dao.delete(id); }
    private void validate(MemberDTO m){
        if(m.getName()==null||m.getName().isBlank()) throw new IllegalArgumentException("이름은 필수입니다.");
        if(m.getEmail()==null||!m.getEmail().contains("@")) throw new IllegalArgumentException("이메일 형식을 확인하세요.");
    }
}

package week07.member.service;

import java.sql.SQLException;
import java.util.List;
import week07.member.dao.MemberDAO;
import week07.member.dto.MemberDTO;

public class MemberService {
    private final MemberDAO dao=new MemberDAO();
    public List<MemberDTO> getMembers() throws SQLException { return dao.findAll(); }
    public void addMember(MemberDTO m) throws SQLException { /* TODO 5: 입력 검증 + insert */ }
    public void updateMember(MemberDTO m) throws SQLException { /* TODO 6: 선택 ID/입력 검증 + update */ }
    public void deleteMember(int id) throws SQLException { /* TODO 7: ID 검증 + delete */ }
}

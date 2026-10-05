package week07.member.dao;
import java.sql.*; import java.util.*; import week07.member.dto.MemberDTO;
public class MemberDAO {
 public List<MemberDTO> findAll() throws SQLException { /* TODO 1: SELECT + DTO 목록 */ return new ArrayList<>(); }
 public int insert(MemberDTO m) throws SQLException { /* TODO 2: INSERT PreparedStatement */ return 0; }
 public int update(MemberDTO m) throws SQLException { /* TODO 3: member_id 기준 UPDATE */ return 0; }
 public int delete(int id) throws SQLException { /* TODO 4: member_id 기준 DELETE */ return 0; }
}
package week06.member;
import java.sql.*; import java.util.*; import week06.common.DBConnection;
public class MemberDAO {
  public List<Member> findAll() throws SQLException {
    List<Member> list=new ArrayList<>();
    String sql="SELECT member_id,name,phone,email FROM member ORDER BY member_id";
    try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
      while(r.next()) list.add(new Member(r.getString("member_id"),r.getString("name"),r.getString("phone"),r.getString("email")));
    } return list;
  }
  public int insert(Member m) throws SQLException {
    String sql="INSERT INTO member(member_id,name,phone,email) VALUES(?,?,?,?)";
    try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setString(1,m.getMemberId());p.setString(2,m.getName());p.setString(3,m.getPhone());p.setString(4,m.getEmail());return p.executeUpdate();
    }
  }
  public int update(Member m) throws SQLException {
    String sql="UPDATE member SET name=?,phone=?,email=? WHERE member_id=?";
    try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
      p.setString(1,m.getName());p.setString(2,m.getPhone());p.setString(3,m.getEmail());p.setString(4,m.getMemberId());return p.executeUpdate();
    }
  }
  public int delete(String id) throws SQLException {
    try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM member WHERE member_id=?")){
      p.setString(1,id);return p.executeUpdate();
    }
  }
}
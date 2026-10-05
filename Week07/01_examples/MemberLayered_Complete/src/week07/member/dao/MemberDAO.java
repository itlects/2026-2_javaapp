package week07.member.dao;

import java.sql.*;
import java.util.*;
import week07.member.dto.MemberDTO;
import week07.member.util.DBConnection;

public class MemberDAO {
    public List<MemberDTO> findAll() throws SQLException {
        String sql="SELECT member_id,name,phone,email,grade FROM members ORDER BY member_id";
        List<MemberDTO> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
            while(r.next()) list.add(new MemberDTO(r.getInt("member_id"),r.getString("name"),r.getString("phone"),r.getString("email"),r.getString("grade")));
        }
        return list;
    }
    public int insert(MemberDTO m) throws SQLException {
        String sql="INSERT INTO members(name,phone,email,grade) VALUES(?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,m.getName()); p.setString(2,m.getPhone()); p.setString(3,m.getEmail()); p.setString(4,m.getGrade()); return p.executeUpdate();
        }
    }
    public int update(MemberDTO m) throws SQLException {
        String sql="UPDATE members SET name=?,phone=?,email=?,grade=? WHERE member_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,m.getName()); p.setString(2,m.getPhone()); p.setString(3,m.getEmail()); p.setString(4,m.getGrade()); p.setInt(5,m.getMemberId()); return p.executeUpdate();
        }
    }
    public int delete(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM members WHERE member_id=?")){
            p.setInt(1,id); return p.executeUpdate();
        }
    }
}

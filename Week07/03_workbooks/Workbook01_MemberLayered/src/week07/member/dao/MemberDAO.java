package week07.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import week07.member.dto.MemberDTO;

public class MemberDAO {

    public List<MemberDTO> findAll()
            throws SQLException {

        // TODO 1:
        // DBConnection import 추가 후
        // SELECT + ResultSet -> MemberDTO 목록 변환

        return new ArrayList<>();
    }

    public int insert(MemberDTO m)
            throws SQLException {

        // TODO 2:
        // INSERT PreparedStatement 구현

        return 0;
    }

    public int update(MemberDTO m)
            throws SQLException {

        // TODO 3:
        // member_id 기준 UPDATE 구현

        return 0;
    }

    public int delete(int id)
            throws SQLException {

        // TODO 4:
        // member_id 기준 DELETE 구현

        return 0;
    }
}

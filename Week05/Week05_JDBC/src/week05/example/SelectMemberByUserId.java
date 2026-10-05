package week05.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import week05.common.DBConnection;

public class SelectMemberByUserId {
    public static void main(String[] args) {
        String sql = "SELECT id, user_id, name, email, department, grade "
                   + "FROM member WHERE user_id = ?";

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("조회할 회원 ID > ");
            String userId = sc.nextLine();

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("번호: " + rs.getInt("id"));
                        System.out.println("ID: " + rs.getString("user_id"));
                        System.out.println("이름: " + rs.getString("name"));
                        System.out.println("이메일: " + rs.getString("email"));
                        System.out.println("학과: " + rs.getString("department"));
                        System.out.println("학년: " + rs.getInt("grade"));
                    } else {
                        System.out.println("조회 결과가 없습니다.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

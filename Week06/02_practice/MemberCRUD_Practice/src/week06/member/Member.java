package week06.member;

/**
 * 회원 한 명의 데이터 (5주차 member 테이블의 한 행)
 *
 * record는 생성자, 접근 메서드(id(), userId() ...), toString()을 자동으로 만들어 준다.
 * - email, phone, department는 DB에서 NULL일 수 있으므로 null이 들어올 수 있다.
 * - grade는 NULL을 표현하기 위해 int가 아니라 Integer를 사용한다.
 */
public record Member(
        int id,
        String userId,
        String password,
        String name,
        String email,
        String phone,
        String department,
        Integer grade) {
}

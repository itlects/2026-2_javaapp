package week06.example;

import java.util.List;

import week06.member.Member;

/** 예제 공통 상수와 출력 메서드 */
public class ExampleUtil {

    /** 예제 2~4에서 등록·수정·삭제할 회원 번호 (5주차 샘플 데이터 1~8번과 겹치지 않게) */
    public static final int EXAMPLE_ID = 100;

    private ExampleUtil() {
    }

    public static void printMembers(List<Member> members) {
        System.out.println("번호 | 아이디 | 이름 | 이메일 | 전화 | 학과 | 학년");
        System.out.println("------------------------------------------------------------");
        for (Member m : members) {
            System.out.printf("%d | %s | %s | %s | %s | %s | %s%n",
                    m.id(), m.userId(), m.name(),
                    nvl(m.email()), nvl(m.phone()), nvl(m.department()),
                    m.grade() == null ? "-" : m.grade());
        }
    }

    private static String nvl(String value) {
        return value == null ? "-" : value;
    }
}

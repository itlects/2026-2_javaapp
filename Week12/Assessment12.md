# 12주차 평가문제


### 객관식: 각 문항에서 가장 알맞은 답 하나를 고르시오.

1. MVC에서 HTTP 입력을 읽고 화면을 선택하는 구성요소는? ① JSP ② Servlet Controller ③ DB 테이블 ④ CSS
2. DAO의 주된 책임은? ① HTML 배치 ② 버튼 색 지정 ③ SQL 실행과 데이터 반환 ④ 주소창 변경
3. request 속성을 유지하면서 JSP로 이동하는 방법은? ① forward ② redirect ③ 새 브라우저 ④ DB 재접속
4. 한 페이지 5건일 때 3페이지의 OFFSET은? ① 3 ② 5 ③ 10 ④ 15
5. 검색 결과 12건, 크기 5일 때 페이지 수는? ① 2 ② 3 ③ 5 ④ 12
6. 검색 결과의 페이지 수를 구할 COUNT 쿼리는? ① 검색 조건 없는 COUNT ② 목록과 같은 WHERE를 적용한 COUNT ③ 항상 5 ④ 현재 페이지 행 수
7. SQL 값에 사용자 입력을 전달하는 적절한 방법은? ① 문자열 덧붙이기 ② JSP에 SQL 작성 ③ PreparedStatement 바인딩 ④ CSS 인코딩
8. 등록 후 redirect의 주된 목적은? ① 비밀번호 암호화 ② 새로고침으로 인한 POST 재전송 감소 ③ SQL 정렬 ④ 페이지 수 증가
9. 사용자 제목을 안전하게 출력하는 JSTL 태그는? ① c:out ② c:redirect ③ c:import ④ c:remove
10. Tomcat 10.1용 Servlet import는? ① javax.servlet ② java.servlet ③ jakarta.servlet ④ mysql.servlet

### 단답형

11. MVC의 M, V, C를 각각 쓰고 예제의 파일 한 개씩 연결하시오.
12. total=23, pageSize=5, page=5일 때 totalPages, offset, 표시 건수를 쓰시오.
13. 페이지 링크에서 검색어를 전달하지 않으면 어떤 문제가 생기는가?
14. JSP를 WEB-INF/views에 두는 이유를 쓰시오.
15. 목록에서 page=-3, page=abc, page=999를 입력했을 때 초기 23건 기준 처리 결과를 쓰시오.

<toggle>
정답 및 해설 — 풀이 후 확인

1② 입력 검증과 화면 선택은 Controller의 역할이다. 2③ DB 접근을 모은다. 3① 동일 요청을 사용한다. 4③ (3-1)×5=10. 5② 올림하여 3. 6② 조건이 일치해야 한다. 7③ 입력을 값으로 바인딩한다. 8② PRG 패턴이다. 9① HTML 문자를 이스케이프한다. 10③ Jakarta 패키지다.

11. Model=Post/PostDAO, View=list.jsp, Controller=BoardServlet. 12. 5페이지, OFFSET 20, 3건. 13. 다음 페이지에서 전체 목록으로 바뀌거나 검색 상태가 사라진다. 14. 외부 직접 접근을 막고 Controller가 전달한 데이터로 화면을 구성하기 위해서다. 15. 각각 1페이지, 1페이지, 5페이지로 보정한다.
</toggle>

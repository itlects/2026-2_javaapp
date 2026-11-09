## 5. 평가문제(객관식 및 단답형)

각 객관식은 정답 하나를 고르세요. 먼저 답안을 작성한 후 정답·해설을 확인합니다.

1. Spring Boot가 웹 개발을 간소화하는 핵심 구성은? ① 수동 JAR 복사만 제공 ② 자동 설정·Starter·내장 서버 ③ HTML만 컴파일 ④ MySQL 자체 대체
2. MVC에서 HTTP 요청을 받아 Service를 호출하고 View 이름을 반환하는 것은? ① View ② DB ③ Controller ④ CSS
3. `@Controller` 메서드가 `"members/list"`를 반환할 때 찾는 파일은? ① static/list.jsp ② templates/members/list.html ③ controller/list.java ④ sql/list.sql
4. 이름 검색을 GET으로 처리하는 매핑은? ① @GetMapping ② @PostConstruct ③ @Repository ④ @SpringBootApplication
5. `/members?keyword=김`의 keyword를 받는 것은? ① @PathVariable ② @RequestBody만 가능 ③ @RequestParam ④ @Service
6. `/members/1`에서 1을 받는 것은? ① @PathVariable ② @Controller ③ Model ④ @Bean
7. View에 회원목록을 전달하는 코드는? ① return members.toString() ② model.addAttribute("members", members) ③ System.out.println(members) ④ new Model()
8. SQL에 사용자 입력을 전달할 때 적절한 것은? ① 문자열을 SQL 뒤에 그대로 연결 ② HTML에 SQL 삽입 ③ ?와 바인딩 인자 사용 ④ 비밀번호를 URL에 노출
9. `th:text`의 기본 동작은? ① HTML 태그를 무조건 실행 ② 문자열을 HTML 이스케이프하여 출력 ③ SQL 실행 ④ URL 매핑 생성
10. 생성자 주입의 설명으로 옳은 것은? ① 모든 객체를 View에서 new ② Spring이 관리하는 의존 객체를 생성자로 전달 ③ DB 테이블을 생성자에 삽입 ④ getter를 삭제하는 기능

단답형

11. 시작 클래스에 붙이는 Spring Boot 기본 애너테이션을 쓰세요.
12. 클래스에 `@RequestMapping("/members")`, 메서드에 `@GetMapping("/{id}")`가 있을 때 id=3의 요청 경로를 쓰세요.
13. `Model`과 데이터 클래스 `Member`의 역할 차이를 한 문장으로 쓰세요.
14. 회원 id=999가 없을 때 본 예제가 응답하는 HTTP 상태 코드를 쓰세요.
15. `return "hello";`가 문자열 본문이 아니라 HTML View로 처리되는 이유를 쓰세요.

### 정답 및 해설

| 번호 | 정답 | 해설 |
| --- | --- | --- |
| 1 | ② | 자동 설정과 Starter, 내장 서버로 초기 설정을 줄임 |
| 2 | ③ | Controller는 요청과 응답 흐름 담당 |
| 3 | ② | Thymeleaf 기본 View 위치와 접미사 적용 |
| 4 | ① | GET 요청 매핑 |
| 5 | ③ | 쿼리 문자열 파라미터 바인딩 |
| 6 | ① | URL 경로의 변수 바인딩 |
| 7 | ② | Model의 속성 이름으로 View에서 접근 |
| 8 | ③ | SQL 구조와 입력값을 분리 |
| 9 | ② | 사용자 입력이 태그로 해석되지 않도록 출력 |
| 10 | ② | 필요한 의존성을 명시적으로 전달받음 |
| 11 | @SpringBootApplication | 자동 설정과 컴포넌트 탐색 등을 활성화 |
| 12 | /members/3 | 클래스·메서드 경로 결합 |
| 13 | Model은 View 전달용 속성 저장소, Member는 회원 한 명의 데이터 | 둘을 같은 객체로 혼동하지 않기 |
| 14 | 404 | 존재하지 않는 회원 |
| 15 | @Controller 메서드에 @ResponseBody가 없어서 반환값을 View 이름으로 해석 | @RestController는 기본적으로 응답 본문 처리 |

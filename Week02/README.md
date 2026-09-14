# JAVA 응용 2주차 — Swing GUI 실습

강의계획표의 2주차 주제인 `Swing 기본 컴포넌트를 이용한 GUI 화면 구현`에 맞춘 Eclipse 프로젝트입니다.

## 실행 환경

- JDK 17 이상
- Eclipse IDE for Java Developers
- 프로젝트명: `JAVA_Application_Week02_Swing`
- 패키지: `week02`

## Eclipse에서 열기

1. ZIP 파일을 원하는 폴더에 압축 해제합니다.
2. Eclipse에서 **File → Import → General → Existing Projects into Workspace**를 선택합니다.
3. 압축을 푼 폴더의 `JAVA_Application_Week02_Swing`를 선택합니다.
4. `src/week02`에서 예제 클래스를 엽니다.
5. **Run As → Java Application**으로 실행합니다.

## 예제 순서

1. `Ex01JFrameBasic` — JFrame 생성과 종료 설정
2. `Ex02JPanel` — JPanel 컨테이너와 배경색
3. `Ex03LabelTextField` — JLabel, JTextField 입력
4. `Ex04ButtonEvent` — JButton 클릭 이벤트
5. `Ex05FlowLayout` — FlowLayout
6. `Ex06BorderLayout` — BorderLayout
7. `Ex07GridLayout` — GridLayout
8. `Ex08LoginForm` — 로그인 화면 배치
9. `Ex09LoginValidation` — 로그인 입력 검증
10. `Ex10SignupForm` — 회원가입 화면 종합 실습

## 최종 예제 확인값

- 로그인 성공 계정: 아이디 `java`, 비밀번호 `1234`
- 회원가입 화면: 필수 입력과 개인정보 동의 여부를 검사합니다.

## 주의사항

- Swing 컴포넌트 생성은 `SwingUtilities.invokeLater()` 안에서 시작합니다.
- 비밀번호는 `JTextField` 대신 `JPasswordField`를 사용합니다.
- 화면 크기와 위치를 지정한 뒤 마지막에 `setVisible(true)`를 호출합니다.
- 배치관리자를 사용하고 픽셀 좌표를 직접 지정하는 `null layout`은 피합니다.

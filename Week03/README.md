# Week03 - Swing 이벤트 처리와 사용자 상호작용

## 수업 방향
2주차에서 완성한 로그인(Login)·회원가입(SignUp) 화면을 다시 설계하지 않습니다.
3주차는 기존 화면을 출발점으로 삼아 **Event Source → Event Object → Listener → Handler** 구조를 이해하고,
ActionEvent, ItemEvent, KeyEvent, FocusEvent, MouseEvent를 단계적으로 연결하는 데 집중합니다.

## 3시간 수업 구성
- 1교시: 이벤트 처리 구조 + ActionEvent + ItemEvent
- 2교시: KeyEvent + FocusEvent + MouseEvent
- 3교시: 2주차 Login/SignUp 고도화 + Event Monitor 종합실습

## 프로젝트
### 예제
1. Week03_Example01_ActionEvent
2. Week03_Example02_ItemEvent
3. Week03_Example03_KeyFocusEvent
4. Week03_Example04_MouseEvent

### 실습
5. Week03_Practice01_LoginEventUpgrade
6. Week03_Practice02_SignUpEventUpgrade
7. Week03_Practice03_EventMonitor

## 학습 연결
- Week02: GUI 화면 구성, 컴포넌트 배치, Login/SignUp 결과 화면 완성
- Week03: 이미 만든 컴포넌트에 Listener를 등록하고 사용자 동작에 반응하도록 구현

## Eclipse Import
1. File > Import
2. General > Existing Projects into Workspace
3. Select archive file
4. Week03_EventHandling_EclipseProjects.zip 선택
5. 예제 4개와 실습 3개를 확인하고 Finish

## 개발 환경
- Eclipse
- JDK 23
- WindowBuilder

## 로그인 실습 테스트 계정
- ID: java
- Password: 1234

## 핵심 관찰
학생은 각 예제에서 UI 배치보다 **어떤 컴포넌트가 이벤트 소스인지, 어떤 이벤트가 발생하는지,
어떤 Listener가 등록되며 Handler에서 무엇이 바뀌는지**를 설명할 수 있어야 합니다.


## 이전 초안 프로젝트 안내
Week03 폴더에는 초기 작성 단계의 프로젝트가 일부 남아 있을 수 있습니다.
현재 강의 및 Google Drive 배포의 기준은 위에 명시한 **예제 4개 + 실습 3개**입니다.

현재 배포에서 제외되는 초기 초안:
- Week03_Example01_ButtonEvent
- Week03_Example02_TextFieldEvent
- Week03_Example03_LayoutCompare
- Week03_Example04_LoginEvent
- Week03_Practice01_Login
- Week03_Practice02_SignUp
- Week03_Practice03_Layout

학생 배포 및 수업에서는 위 초기 초안 대신 Event Handling 프로젝트 7개를 사용합니다.

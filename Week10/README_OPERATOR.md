# 교수자·운영자 확인 순서

1. docs/lecture.md 상단 환경을 설정하고 servlet-login을 먼저 실행합니다.
2. 교육계정 student / Java1234!로 로그인·실패·로그아웃을 시연합니다.
3. /echo 및 /flow 두 모드의 Network·주소창·표시값 차이를 설명합니다.
4. Workbench에서 data/setup.sql을 실행하고 learning-portal용 환경변수를 설정합니다.
5. learning-portal을 실행하고 동일 계정 DB 인증을 확인합니다.
6. data/test-cases.csv 14개 시나리오를 실행합니다. 만료 검사는 소스 60초 설정 후 재로그인하여 수행하고 1200초로 복원합니다.
7. 총 100점 채점표는 docs/lecture.md의 실습 및 과제 부분을 사용합니다.

초기 SQL은 DB를 삭제하지 않으며 INSERT IGNORE로 기존 회원을 보존합니다. 기존 week10_app 계정은 비밀번호가 자동 변경되지 않습니다. 같은 이름의 이전 DB를 사용할 때에는 실제 값을 확인합니다.

검증 범위와 미검증 환경은 docs/verification.md를 확인합니다.

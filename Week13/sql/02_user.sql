-- Workbench에서 root로 실행. 아래 자리표시자를 본인 실습 비밀번호로 바꾸세요.
CREATE USER IF NOT EXISTS 'javaapp13'@'localhost'
    IDENTIFIED BY 'CHANGE_ME_LOCAL_PASSWORD';
-- 계정이 이미 있으면 CREATE USER는 기존 비밀번호를 변경하지 않습니다.
-- 변경이 필요하면 아래 주석을 해제하고 본인 비밀번호로 수정하세요.
-- ALTER USER 'javaapp13'@'localhost' IDENTIFIED BY 'CHANGE_ME_LOCAL_PASSWORD';
GRANT SELECT ON javaapp13.* TO 'javaapp13'@'localhost';

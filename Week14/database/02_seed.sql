USE javaapp14;
INSERT INTO members(name, email, phone)
SELECT '홍길동', 'hong@example.com', '010-1111-2222'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='hong@example.com');
INSERT INTO members(name, email, phone)
SELECT '김자바', 'java@example.com', '010-3333-4444'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='java@example.com');
SELECT id, name, email, phone, created_at FROM members ORDER BY id DESC;

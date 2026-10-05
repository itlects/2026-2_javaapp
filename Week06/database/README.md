# 6주차 DB 자료

- `member.sql` : 5주차 member 테이블과 같은 구조 + 5주차 CSV와 같은 8명의 데이터 (INSERT IGNORE, 반복 실행 가능)
- `product.sql` : 5주차 product 테이블과 같은 구조 + 5주차 CSV와 같은 10개 상품
- `member_sample.csv`, `product_sample.csv` : 5주차 CSV 원본 (Table Data Import Wizard로 불러와도 같은 결과)

이전에 다른 구조의 member/product 테이블을 만든 적이 있어 오류가 나면,
SQL 파일 안의 `-- DROP TABLE IF EXISTS ...;` 주석을 지우고 다시 실행합니다. (해당 테이블 데이터 삭제)

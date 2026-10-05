# Week06 — Swing GUI + JDBC 회원관리 CRUD

5주차에서 만든 `javaapp.member` / `javaapp.product` 테이블(같은 구조, 같은 데이터)을 그대로 사용하여
SELECT 조회에 INSERT / UPDATE / DELETE를 추가하고 Swing 회원관리 프로그램을 완성합니다.

## 구성

| 폴더 | Eclipse 프로젝트 이름 | 내용 | 실행 클래스 |
|---|---|---|---|
| `database` | — | member.sql, product.sql, 5주차 CSV | MySQL Workbench에서 실행 |
| `01_examples/CRUDStepExamples` | Week06_CRUDStepExamples | 예제 1~4 (콘솔, DAO 완성본) | `Example1_SelectAll` ~ `Example4_Delete` |
| `02_practice/MemberCRUD_Practice` | Week06_MemberCRUD_Practice | 실습 시작 프로젝트 (TODO 실습 2~5) | `MemberManagementFrame` |
| `03_workbooks/Workbook01_MemberCRUD` | Week06_Workbook01_MemberCRUD | DAO 빈칸 TODO 1~5 + 자가 점검 | `MemberDaoChecker` → `MemberManagementFrame` |
| `03_workbooks/Workbook02_ProductCRUD` | Week06_Workbook02_ProductCRUD | 상품관리 DAO·GUI 빈칸 | `ProductDaoChecker` → `ProductManagementFrame` |
| `MemberManagement` | Week06_MemberManagement | 기본 통합 완성본 | `MemberManagementFrame` |

## 준비 (공통)

1. MySQL Workbench에서 `database/member.sql` 실행 (워크북 2는 `product.sql`도 실행)
   - 5주차 데이터가 있으면 그대로 두고, 없으면 5주차 CSV와 같은 데이터를 넣습니다. 여러 번 실행해도 됩니다.
2. Eclipse: File > Import > General > Existing Projects into Workspace > 프로젝트 폴더 선택
3. 프로젝트 우클릭 > Build Path > Configure Build Path > Libraries > Classpath > Add External JARs
   > 5주차에 사용한 `mysql-connector-j-x.x.x.jar` 추가
4. `src/week06/common/DBConnection.java`의 `PASSWORD`를 자신의 MySQL 비밀번호로 수정

## 테이블 구조 (5주차와 동일)

```
member(id INT PK, user_id VARCHAR(30) UNIQUE NOT NULL, password VARCHAR(100) NOT NULL,
       name VARCHAR(30) NOT NULL, email, phone, department, grade INT)        -- email~grade는 NULL 가능
product(product_id VARCHAR(10) PK, product_name NOT NULL, category NOT NULL,
        price INT NOT NULL, stock INT NOT NULL, maker)                        -- maker는 NULL 가능
```

## 자주 나는 오류

| 증상 | 원인 / 해결 |
|---|---|
| `No suitable driver found` | Connector/J JAR를 Build Path에 추가하지 않음 |
| `Access denied for user 'root'` | DBConnection의 비밀번호 수정 |
| `Unknown column ...` 또는 `Column count doesn't match` (SQL 실행 시) | 다른 구조의 member 테이블이 있음 → member.sql의 `DROP TABLE` 주석 해제 후 다시 실행 |
| `TODO ...: 구현` 메시지 | 아직 완성하지 않은 TODO 메서드 |

> DB 비밀번호 `1234`는 실습용 자리값입니다. 실제 서비스에서는 비밀번호를 소스코드에 저장하지 않고,
> 회원 비밀번호도 평문이 아니라 해시값으로 저장합니다.

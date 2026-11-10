# Week06_Workbook02_ProductCRUD_Complete

6주차 워크북 2의 TODO 완성본입니다.

## 학습용 프로젝트와의 관계

- TODO 실습본: `Workbook02_ProductCRUD`
- 완성본: `Workbook02_ProductCRUD_Complete`

학생은 먼저 TODO 실습본을 직접 완성한 뒤, 막히는 부분이나 최종 정답 확인이 필요할 때 이 완성본과 비교합니다.

## 포함된 완성 항목

### ProductDAO
- findAll()
- findById()
- insert()
- update()
- delete()
- toProduct()
- nullable maker 처리

### ProductManagementFrame
- loadProducts()
- insertProduct()
- updateProduct()
- deleteProduct()
- showSelectedProduct()
- 입력 검증 및 JTable 자동 갱신

## 실행

1. MySQL Workbench에서 `database/product.sql` 실행
2. Eclipse에서 Existing Projects into Workspace로 Import
3. MySQL Connector/J를 Build Path에 추가
4. `DBConnection.java`의 비밀번호 수정
5. `ProductManagementFrame.java` 실행

> 기준 환경: Windows 10 / Java 21 / Eclipse 2025-09 / MySQL 8.0.46 / Workbench 8

# ReservationManager_Workbook

Week 7 layered database application workbook for Java 21 + Eclipse + MySQL 8.0.

## Architecture
UI -> Service -> DAO -> MySQL  
DTO transfers reservation data between layers.

## Environment
- Windows 10
- Java 21
- Eclipse 2025.9 + WindowBuilder
- MySQL Community Server 8.0.46
- MySQL Workbench 8
- MySQL Connector/J (add to Eclipse Build Path)

## Run
1. Run `database/reservation.sql` in MySQL Workbench.
2. Eclipse: File > Import > Existing Projects into Workspace.
3. Add MySQL Connector/J to Build Path.
4. If needed, edit `DBConnection.java` (default root / 1234).
5. Run `week07.reservation.ui.ReservationManagerFrame`.
6. Test Load -> Add -> Select -> Update -> Delete.

## WindowBuilder component names
- tfId, tfCustomer, tfDate, tfTime
- cbStatus
- table
- btnSearch, btnInsert, btnUpdate, btnDelete, btnClear

## Note
This project is a completed runnable reference for the workbook.

@echo off
setlocal
cd /d "%~dp0"
call mvn -f "..\w10\pom.xml" clean package
if errorlevel 1 exit /b 1
call mvn clean package
if errorlevel 1 exit /b 1
copy /y "target\w10.jar" "w10.jar"
echo Ready: java -jar w10.jar

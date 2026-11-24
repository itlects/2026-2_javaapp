@echo off
setlocal
cd /d "%~dp0"
where java >nul 2>&1
if errorlevel 1 (
    echo Java 21 is required. Check java -version and PATH.
    pause
    exit /b 1
)
if not exist "w10.jar" (
    echo w10.jar was not found next to this batch file.
    pause
    exit /b 1
)
java -jar "w10.jar" %*
if errorlevel 1 pause

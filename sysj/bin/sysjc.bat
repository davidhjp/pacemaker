@echo off

if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_HOME=%DIRNAME%..

set NEED_JDK=true
call "%APP_HOME%"\bin\init.bat %*

if "%ERRORLEVEL%" neq "0" goto end

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% -classpath "%CLASSPATH%" JavaPrettyPrinter %CMD_LINE_ARGS%

:end
if "%OS%"=="Windows_NT" endlocal


@echo off
set CLASS_BASE=c:\GF\gfguide\deploy\WEB-INF
echo %CLASS_BASE%
pushd ..\
set CLASSPATH=.;%CLASS_BASE%\classes;%CLASS_BASE%\lib\poi-3.0.1-FINAL-20070705.jar;%CLASS_BASE%\lib\mysql-connector-java-5.1.5-bin.jar;%CLASS_BASE%\lib\quartz-1.8.4.jar;%CLASS_BASE%\lib\commons-pool-1.5.4.jar;%CLASS_BASE%\lib\slf4j-log4j12-1.6.0.jar;%CLASS_BASE%\lib\commons-dbcp-1.3.jar;%CLASS_BASE%\lib\log4j-1.2.14.jar;%CLASS_BASE%\lib\slf4j-api-1.6.0.jar;%CLASS_BASE%\lib\jta-1.1.jar
@echo on
@java org.celiac.util.RequestResponseEmulator "http://gfsms.info/sms?message=αξαδ&cellPhone=12345"

pause

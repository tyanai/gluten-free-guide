@echo off
set CLASS_BASE=d:\work\dev\gfguide
echo %CLASS_BASE%
pushd ..\
set CLASSPATH=.;%CLASS_BASE%\classes;%CLASS_BASE%\WebContent\WEB-INF\lib\poi-3.0.1-FINAL-20070705.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\mysql-connector-java-5.1.5-bin.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\quartz-1.8.4.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\commons-pool-1.5.4.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\slf4j-log4j12-1.6.0.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\commons-dbcp-1.3.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\log4j-1.2.14.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\slf4j-api-1.6.0.jar;%CLASS_BASE%\WebContent\WEB-INF\lib\jta-1.1.jar
@echo on
@java org.celiac.XLS2MySQL images
pause
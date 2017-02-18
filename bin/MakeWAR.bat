@echo off
@echo off
echo Making jar file....
set PATH="C:\Program Files\Java\jdk1.6.0_17\bin";%PATH%
set CLASSPATH=.\lib\gfguide.jar;.\lib\mysql-connector-java-5.1.5-bin.jar;.\lib\commons-fileupload-1.2.1.jar
cd ..\classes
mkdir properties
copy ..\properties\*.map properties
copy..\properties\*.properties properties
copy ..\properties\*.template properties
copy ..\properties\*.job properties
jar -cvf gfguide.jar org be properties
move gfguide.jar ..\lib
del /Q properties
rmdir /Q properties


echo Making war file....
cd ..\lib
copy mysql-connector-java-5.1.5-bin.jar ..\web\WEB-INF\lib
copy gfguide.jar ..\web\WEB-INF\lib
copy commons-fileupload-1.2.1.jar ..\web\WEB-INF\lib
copy commons-io-1.4.jar ..\web\WEB-INF\lib
copy poi-3.0.1-FINAL-20070705.jar ..\web\WEB-INF\lib
copy activation.jar ..\web\WEB-INF\lib
copy mail.jar ..\web\WEB-INF\lib
copy commons-logging.jar ..\web\WEB-INF\lib
copy dwr.jar ..\web\WEB-INF\lib
copy jai_codec.jar ..\web\WEB-INF\lib
copy jai_core.jar ..\web\WEB-INF\lib
copy mlibwrapper_jai.jar ..\web\WEB-INF\lib
copy quartz-1.8.4.jar ..\web\WEB-INF\lib
copy commons-pool-1.5.4.jar ..\web\WEB-INF\lib
copy slf4j-log4j12-1.6.0.jar ..\web\WEB-INF\lib
copy commons-dbcp-1.3.jar ..\web\WEB-INF\lib
copy log4j-1.2.14.jar ..\web\WEB-INF\lib
copy slf4j-api-1.6.0.jar ..\web\WEB-INF\lib
copy jta-1.1.jar ..\web\WEB-INF\lib


copy commons-logging-1.1.1.jar ..\web\WEB-INF\lib
copy cxf-2.4.1.jar ..\web\WEB-INF\lib
copy jettison-1.3.jar ..\web\WEB-INF\lib
copy jsr311-api-1.1.1.jar ..\web\WEB-INF\lib
copy neethi-3.0.0.jar ..\web\WEB-INF\lib
copy spring-aop-3.0.5.RELEASE.jar ..\web\WEB-INF\lib
copy spring-asm-3.0.5.RELEASE.jar ..\web\WEB-INF\lib
copy spring-beans-3.0.5.RELEASE.jar ..\web\WEB-INF\lib
copy spring-context-3.0.5.RELEASE.jar ..\web\WEB-INF\lib
copy spring-core-3.0.5.RELEASE.jar ..\web\WEB-INF\lib
copy spring-expression-3.0.5.RELEASE.jar ..\web\WEB-INF\lib
copy spring-web-3.0.5.RELEASE.jar ..\web\WEB-INF\lib
copy wsdl4j-1.6.2.jar ..\web\WEB-INF\lib
copy json_simple-1.1.jar ..\web\WEB-INF\lib





copy gfguide.jar "C:\Apache\Tomcat 6.0\webapps\gfguide\WEB-INF\lib"

copy gfguide.jar ..\bin\
copy gfguide.jar "C:\Apache\Tomcat 6.0\webapps\gfguide\WEB-INF\lib"
del /Q gfguide.jar

pushd ..\web

jar cvf ..\bin\gfguide.war *

del /Q WEB-INF\lib\*

@echo on
pause


@echo off
set CLASSPATH=.;.\classes;.\lib\poi-3.0.1-FINAL-20070705.jar;.\lib\mysql-connector-java-5.1.5-bin.jar;.\lib\comm.jar
set PATH=%PATH%;.\lib
pushd ..\
@echo on
@java org.celiac.server.Server
pause
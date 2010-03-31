echo off
REM ***************************************
REM  File:$file$
REM  Revision: $rev$
REM
REM  Author: Nguyen Quang Huy
REM
REM  Version: 1.0
REM
REM  Description: Simple script for compiling cpptest plugin and start hudson with it
REM ***************************************
rmdir /S /Q "%HOME%\.hudson\plugins\cpptest" 
d:
cd D:\checkout\hudson\trunk\plugins\cpptest
call mvn -o package
copy ".\target\cpptest.hpi" "%HOME%\.hudson\plugins\"
java -jar "%HOME%\.hudson\hudson.war"
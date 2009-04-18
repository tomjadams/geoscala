@echo off

REM ---------------------------------------------------
REM Modify the following to change the default settings
REM The defaults should be OK for all configurations
REM ---------------------------------------------------

REM -- root directory for the project
set _PROJECTDIR=%PROJECTDIR%
set PROJECTDIR=.

REM -- Directory containing jars required
set _LIBDIR=%LIBDIR%
set LIBDIR=%PROJECTDIR%\lib

REM -- Name of the build file to use
set _BUILDFILE=%BUILDFILE%
set BUILDFILE=build.xml

REM -- Default compiler to use
set _JAVAC=%JAVAC%
set JAVAC=classic

REM -- Set JVM options
set _JVM_OPTS=%JVM_OPTS%
set JVM_OPTS=-Xms64m -Xmx128m

REM --------------------------------------------
REM No need to edit anything past here
REM --------------------------------------------

:init
set _CLASSPATH=%CLASSPATH%
set LOCALPATH=

:testant
if "%ANT_HOME%" == "" goto setant
goto buildpath

:setant
set ANT_HOME=%PROJECTDIR%
goto buildpath

:buildpath
set LOCALPATH=%LOCALPATH%;%LIBDIR%\ant\ant-1.7.0.jar
set LOCALPATH=%LOCALPATH%;%LIBDIR%\ant\ant-launcher-1.7.0.jar

:testjavahome
if "%JAVA_HOME%" == "" goto setjavahome
goto testjikes

:setjavahome
if not "%OS%" == "Windows_NT" goto javahomeerror

:setjavahoment
for %%j IN (java.exe) DO set JAVABIN=%%~dp$PATH:j
if "%JAVABIN%" == "" goto javahomeerror
for %%j IN (%JAVABIN%..\) DO set JAVA_HOME=%%~dpj

:testjikes
if not "%OS%" == "Windows_NT" goto setjikes

:testjikesnt
for %%k IN (jikes.exe) DO set JIKES=%%~f$PATH:k

:setjikes
if not "%JIKES%" == "" set JAVAC=jikes

:build
if exist "%JAVA_HOME%\lib\tools.jar" set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\tools.jar

"%JAVA_HOME%\bin\java.exe" %JVM_OPTS% -classpath "%CLASSPATH%;%LOCALPATH%" -Dant.home="%ANT_HOME%" -DJAVAC=%JAVAC% org.apache.tools.ant.Main -buildfile %BUILDFILE% %1 %2 %3 %4 %5 %6 %7 %8 %9

goto end

:javahomeerror
echo ERROR: JAVA_HOME not found in your environment.
echo Please, set the JAVA_HOME variable in your environment to match the
echo location of the Java Virtual Machine you want to use.

:end

set JVM_OPTS=%_JVM_OPTS%
set CLASSPATH=%_CLASSPATH%
set PROJECTDIR=%_PROJECTDIR%
set LIBDIR=%_LIBDIR%
set LIBDIR=%_LIBDIR%
set BUILDFILE=%_BUILDFILE%
set JAVAC=%_JAVAC%

endlocal

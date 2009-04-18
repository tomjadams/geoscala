#!/bin/sh

# ---------------------------------------------------
# Modify the following to change the default settings
# The defaults should be OK for all configurations
# ---------------------------------------------------

# Root directory for the project
PROJECTDIR=.

# Directory contain jars required for runtime
LIBDIR=${PROJECTDIR}/lib

# Directory contain jars required for compilation
LIBDIR=${PROJECTDIR}/lib

# The name of the build file to use
BUILDFILE=build.xml

# The default compiler to use
JAVAC=classic

# JVM options
JVM_OPTS="-Xms64m -Xmx128m"

#--------------------------------------------
# No need to edit anything past here
#--------------------------------------------

# Define ANT_HOME if necessary
if [ "$ANT_HOME" = "" ]; then
    ANT_HOME=${PROJECTDIR}
fi

if test -z "${JAVA_HOME}" ; then
    # JAVA_HOME is not set, try to set it if java is in PATH
    echo "ERROR: JAVA_HOME not found in your environment."
    echo "Please, set the JAVA_HOME variable in your environment to match the"
    echo "location of the Java Virtual Machine you want to use."
    exit
fi
#PATH=${JAVA_HOME}/bin:$PATH

# Try to find Java Home directory, from JAVA_HOME environment
# or java executable found in PATH
JAVA_BIN=${JAVA_HOME}/bin/java

# Add the ant libraries to the classpath
CLASSPATH=${CLASSPATH}:${LIBDIR}/ant/ant-1.7.0.jar
CLASSPATH=${CLASSPATH}:${LIBDIR}/ant/ant-launcher-1.7.0.jar

# Try to include tools.jar for compilation
if test -f ${JAVA_HOME}/lib/tools.jar ; then
    CLASSPATH=${CLASSPATH}:${JAVA_HOME}/lib/tools.jar
fi

# Define the java executable path
if [ "$JAVA_BIN" = "" ] ; then
    JAVA_BIN=${JAVA_HOME}/bin/java
fi

# Define the javac executable path
JAVAC=${JAVA_HOME}/bin/javac

# convert the paths to unix for cygwin
if [ "$OSTYPE" = "cygwin32" ] || [ "$OSTYPE" = "cygwin" ] ; then
    CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
    JAVA_HOME=`cygpath --path --unix "$JAVA_HOME"`
    JAVA_BIN=`cygpath --path --unix "$JAVA_BIN"`
    JAVAC=`cygpath --path --unix "$JAVAC"`
fi

# echo environment variables to stdout for debugging
#echo "Java Home: ${JAVA_HOME}"
#echo "Java compiler ${JAVAC}"
#echo "Java libpath ${LIBCLASSPATH}"
#echo Java Classpath="${CLASSPATH}"

# convert the unix path to windows
if [ "$OSTYPE" = "cygwin32" ] || [ "$OSTYPE" = "cygwin" ] ; then
    CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
    JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
fi

# Call Ant
${JAVA_BIN} ${JVM_OPTS} -Dant.home=${ANT_HOME} -DJAVAC=${JAVAC} \
        -classpath "${CLASSPATH}" org.apache.tools.ant.Main \
        -buildfile "${BUILDFILE}" "$@"


JAVA_HOME=/usr/wx/jdk
LIB_HOME=./lib

JAVA_LIB=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/jre/lib/rt.jar
COMMONS_LIB=$JAVA_LIB:$LIB_HOME/autopublish.jar:$LIB_HOME/commons-codec-1.3.jar:$LIB_HOME/commons-httpclient-3.1-alpha1.jar:$LIB_HOME/commons-logging.jar:$LIB_HOME/dom4j-1.6.1.jar:jdom.jar:.


$JAVA_HOME/bin/java -hotspot -ms64m -mx64m -classpath $COMMONS_LIB com.founder.enp.autopublish.Start

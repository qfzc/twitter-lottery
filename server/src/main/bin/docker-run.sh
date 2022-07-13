#!/bin/sh

LINA_MAINCLASS=com.smurfs.twitter.App

LINA_CLASSPATH=$CLASSPATH:../config/:`find ../lib -name *.jar|xargs|sed "s/ /:/g"`

JVM_ARGS="-Xms512M -Xmx1024M -XX:+UseParallelGC -XX:+AggressiveOpts -XX:+UseFastAccessorMethods -Djava.library.path=. -Dfile.encoding=UTF-8"

$JAVA_HOME/bin/java $JVM_ARGS -classpath $LINA_CLASSPATH $LINA_MAINCLASS
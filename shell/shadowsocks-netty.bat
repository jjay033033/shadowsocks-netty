@echo off
java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8011 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -jar shadowsocks-netty-1.0.0.jar
pause
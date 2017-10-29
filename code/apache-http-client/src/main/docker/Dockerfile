FROM tomcat:8.5

RUN mkdir "$CATALINA_HOME/keystore"

RUN keytool  -genkey -noprompt -trustcacerts -keyalg RSA -alias tomcat -dname  "CN=Palash Ray, OU=Demo, O=Swayam, L=Bangalore, ST=Karnataka, C=IN" -keypass changeme -keystore "$CATALINA_HOME/keystore/my_keystore" -storepass changeme

COPY server.xml "$CATALINA_HOME/conf/server.xml"

EXPOSE 8443
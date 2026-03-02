FROM tomcat:10.1-jdk17
COPY StudentRegistration.war /usr/local/tomcat/webapps/StudentRegistration.war
EXPOSE 8080
CMD ["catalina.sh", "run"]

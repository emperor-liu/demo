FROM jdk-8u91:20180907

VOLUME /tmp

ADD target/dubbo-provider-1.0.0.jar /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
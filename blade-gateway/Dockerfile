FROM bladex/alpine-java:openjdk17_cn_slim

MAINTAINER bladejava@qq.com

RUN mkdir -p /blade/gateway

WORKDIR /blade/gateway

EXPOSE 80

ADD ./target/blade-gateway.jar ./app.jar

ENTRYPOINT ["java", "--add-opens java.base/java.lang.reflect=ALL-UNNAMED", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]

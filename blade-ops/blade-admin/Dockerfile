FROM bladex/alpine-java:openjdk17_cn_slim

MAINTAINER bladejava@qq.com

RUN mkdir -p /blade/admin

WORKDIR /blade/admin

EXPOSE 7002

ADD ./target/blade-admin.jar ./app.jar

ENTRYPOINT ["java", "--add-opens java.base/java.lang.reflect=ALL-UNNAMED", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]

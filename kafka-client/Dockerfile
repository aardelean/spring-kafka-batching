FROM openjdk:9
MAINTAINER aardelean <ardelean.i.alexandru@gmail.com>

ENV APP_NAME=kafka-client

COPY run.sh /
COPY ${APP_NAME}-*.jar /opt/
RUN chmod +x run.sh
RUN find /opt -regextype egrep -regex ".*/${APP_NAME}-[0-9]+(\.[0-9]+)*(-PR[0-9]+)?(-SNAPSHOT)?\.jar" -exec echo {} \; -exec ln -s {} /opt/${APP_NAME}.jar \;

EXPOSE 7002

ENTRYPOINT [ "/run.sh" ]

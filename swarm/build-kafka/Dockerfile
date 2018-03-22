FROM openjdk

ARG brokerId=1
ENV KAFKA=kafka_2.11-1.0.0
ADD $KAFKA".tgz" /

RUN mv /$KAFKA /kafka && \
    sed -i s/localhost:2181/zookeeper:2181/g /kafka/config/server.properties && \
    sed -i s/broker.id=0/broker.id=${brokerId}/g /kafka/config/server.properties

EXPOSE 2181 9092
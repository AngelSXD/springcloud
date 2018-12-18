FROM java:8
ADD tx-manager.jar tx-manager.jar
RUN bash -c 'touch /tx-manager.jar'

ENV SPRING-CLOUD-EUREKA-ZONE http://192.168.92.130:8000/eureka/
ENV SPRING-CLOUD-REDIS-HOST 192.168.92.130
ENV SPRING-CLOUD-REDIS-PORT 6379
ENV SPRING-CLOUD-REDIS-PASSWORD 398023


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/tx-manager.jar"]
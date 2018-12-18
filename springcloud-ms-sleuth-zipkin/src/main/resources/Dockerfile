FROM java:8
ADD ms-sleuth-zipkin.jar ms-sleuth-zipkin.jar
RUN bash -c 'touch /ms-sleuth-zipkin.jar'

ENV SPRING-CLOUD-MS-NAME ms-sleuth-zipkin
ENV SPRING-CLOUD-EUREKA-ZONE http://192.168.92.130:8000/eureka/
ENV SPRING-CLOUD-MYSQL-HOST 192.168.92.130
ENV SPRING-CLOUD-DB-NAME springcloudtest
ENV SPRING-CLOUD-ZIPKIN-URL http://192.168.92.130:8002
ENV TX-MANAGER-URL http://192.168.92.130:7000/tx/manager/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/ms-sleuth-zipkin.jar"]
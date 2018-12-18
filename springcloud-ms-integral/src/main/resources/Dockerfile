FROM java:8
ADD ms-integral.jar ms-integral.jar
RUN bash -c 'touch /ms-integral.jar'

ENV SPRING-CLOUD-MS-NAME ms-integral
ENV SPRING-CLOUD-EUREKA-ZONE http://192.168.92.130:8000/eureka/
ENV SPRING-CLOUD-MYSQL-HOST 192.168.92.130
ENV SPRING-CLOUD-DB-NAME springcloudintegral
ENV SPRING-CLOUD-ZIPKIN-URL http://192.168.92.130:8002
ENV TX-MANAGER-URL http://$SPRING-CLOUD-HOSTNAME:7000/tx/manager/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/ms-integral.jar"]
FROM java:8
ADD ms-hystrix-turbine.jar ms-hystrix-turbine.jar
RUN bash -c 'touch /ms-hystrix-turbine.jar'

ENV SPRING-CLOUD-MS-NAME ms-hystrix-turbine
ENV SPRING-CLOUD-EUREKA-ZONE http://192.168.92.130:8000/eureka/
ENV SPRING-CLOUD-MYSQL-HOST 192.168.92.130
ENV SPRING-CLOUD-DB-NAME springcloudtest
ENV TX-MANAGER-URL http://$SPRING-CLOUD-HOSTNAME:7000/tx/manager/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/ms-hystrix-turbine.jar"]
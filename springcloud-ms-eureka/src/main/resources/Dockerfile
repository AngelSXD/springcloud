FROM java:8
ADD ms-eureka.jar ms-eureka.jar
RUN bash -c 'touch /ms-eureka.jar'

ENV SPRING-CLOUD-MS-NAME ms-eureka
ENV SPRING-CLOUD-HOSTNAME centos7
ENV TX-MANAGER-URL http://centos7:7000/tx/manager/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx1024m","-Xms1024m","/ms-eureka.jar"]
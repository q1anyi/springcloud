# SpringCloud后端项目

### 启动方式
一、先打包并启动服务端
* java -jar eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
* java -jar eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2

二、打包并且启动客户端
* java -jar eureka-client-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
* java -jar eureka-client-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2



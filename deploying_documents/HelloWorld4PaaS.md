# Hello World!  
-   编写Spring boot项目  
    1.  DockerApplication.java如下  
        ```java
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        @SpringBootApplication
        public class DockerApplication {
            public static void main(String[] args) {
                SpringApplication.run(DockerApplication.class, args);
            }
        }
        ```  
    2.  DockerController.java如下  
        ```java
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        @RestController
        public class DockerController {
            @RequestMapping("/")
            public String index() {
                return "Hello World";
            }
        }
        ```  
    3.  pom.xml文件如下  
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>
            <groupId>com.zte</groupId>
            <artifactId>testPaas</artifactId>
            <version>1.0-SNAPSHOT</version>
            <parent>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-parent</artifactId>
                <version>2.0.0.RELEASE</version>
            </parent>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-web</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-test</artifactId>
                    <scope>test</scope>
                </dependency>
            </dependencies>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-maven-plugin</artifactId>
                    </plugin>
                </plugins>
            </build>
        </project>
        ```  
    4.  执行```mvn package```  
-   将Spring boot 项目通过docker打包成tar镜像  
    1.  编写dockerfile文件用于描述要打包的镜像  
        ```
        FROM openjdk:8-jdk-alpine
        MAINTAINER testPaasAuthor
        ENV http_proxy=http://proxy/zte.com.cn:80/
        ADD testPaas-1.0-SNAPSHOT.jar app.jar
        EXPOSE 8080
        ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
        ```  
    2.  深入命令构建镜像并导出  
        ```
        sudo docker build -t testpaasauthor/testpaas .
        sudo docker save -o testpaas.tar testpaasauthor/testpaas
        ```  
-   将tar镜像上传  
-   构建蓝图，部署，提供访问地址  
    1.  在http://[ip]/portal登录，此处使用(admintest/111111为新建的账户),填写项目名称，其他保持默认。  
    2.  点击进入项目，Software Repository-Image-Import，填写镜像名称和版本号，选择打包好的tar文件，"Released as a public image"为是否将这个镜像发布为公共镜像，公共镜像是其他的项目也可以使用的。  
    3.  点击Upload即可完成。  
    4.  发布镜像后，在Software Repository-Blueprint-New Blueprint  
    5.  进行配置Type选择Microservice，输入名称和版本号，Frame type选择Pod  
        >注意此步如果出现被踢出项目，或其他奇怪的问题，可以尝试使用Chrome浏览器，并清除缓存。  
    6.  进入蓝图配置后，点击Pod/Container，找到刚刚发布的镜像，将其拖动至蓝图名称的框内。  
    7.  添加EndPoint，点击Basic Type-EndPoint，将其拖动到镜像框的边缘。  
        >注意此步骤要将其拖动到框的边缘  
    8.  单击镜像进行配置，选择Advanced settings-Port，在Protocol选择TCP，并填入docker镜像暴露的接口。  
    9.  单击EndPoint，在Port中选择刚刚配置的端口。protocol选择HTTP，Visual range选择External。  
    10. 再次拖动一个Nic到蓝图最外框，点击进行配置，Attach to network 输入lan  
    11. 保存并部署
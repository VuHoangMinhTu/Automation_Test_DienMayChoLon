# Hướng dẫn chạy test
## Bước 1: Tạo project Java Maven (Sử dụng Java 17)
## Bước 2: Thêm các dependency và plugin
- Dependencies:
+ Selenium (v4.16.1)
+ Web Driver Manager (v5.6.3)
+ JUnit 5 Jupiter (v5.9.2)
+ JUnit 4 (v4.13.2)
+ JUnit Vintage Engine (để JUnit 4 chạy trong JUnit 5) (v5.9.2)
- Plugin:
+ Maven Surefire (v3.2.5)
- Code cấu hình tổng hợp:
```
    <dependencies>
        <!-- Selenium -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.16.1</version>
        </dependency>
        <!-- WebDriverManager -->
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>5.6.3</version>
        </dependency>
        <!-- JUnit 5 (Jupiter) -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.2</version>
            <scope>test</scope>
        </dependency>
        <!-- JUnit 4 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
        <!-- JUnit Vintage Engine (để JUnit 4 chạy trong JUnit 5) -->
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <version>5.9.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
        <!-- Maven Surefire -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
                <configuration>
                    <useModulePath>false</useModulePath>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
## Bước 3: Chuyển file vào project
- Theo đường dẫn ```src/test/java``` tạo một package 
- Copy/cut 7 file đưa vào package
## Bước 4: Sử dụng maven để chạy lệnh test
- Mở terminal ở project
- Chạy lệnh 
```mvn -q test```



# 阶段 1：构建 JAR 包（使用 Maven）
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# 缓存 Maven 依赖（优化构建速度）
RUN mvn dependency:go-offline

# 打包应用（跳过测试）
RUN mvn clean package -Dmaven.test.skip=true

# 阶段 2：运行环境（使用精简的 JDK 镜像）
FROM openjdk:17-jdk-slim
WORKDIR /app

# 从构建阶段复制 JAR 包
COPY --from=build /app/target/*.jar app.jar

# Render 会通过 PORT 环境变量分配端口，必须使用该变量
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]
    
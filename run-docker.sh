#!/bin/bash
# 构建 Spring Boot 项目
mvn clean package

# 构建 Docker 镜像
docker build -t springboot-hello .

# 运行 Docker 容器
docker run -d -p 8080:8080 springboot-hello

echo "Spring Boot 应用已在 Docker 容器中运行，访问 http://localhost:8080"
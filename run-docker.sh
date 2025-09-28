#!/bin/bash

# 检查是否存在.env文件并加载
if [ -f .env ]; then
    echo "Loading environment variables from .env file..."
    export $(cat .env | grep -v '#' | xargs)
fi

# 停止并删除旧容器（如果存在）
CONTAINER_NAME=springboot-hello
echo "Cleaning up old container..."
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

# 构建项目
echo "Building Spring Boot project..."
mvn clean package -DskipTests

# 构建Docker镜像
echo "Building Docker image..."
docker build -t $CONTAINER_NAME .

# 运行新容器
echo "Starting container..."
docker run -d \
    --name $CONTAINER_NAME \
    -p 8080:8080 \
    -e SUPABASE_URL="${SUPABASE_URL:-https://your-project-url.supabase.co}" \
    -e SUPABASE_KEY="${SUPABASE_KEY:-your-supabase-anon-key}" \
    $CONTAINER_NAME

# 等待容器启动
echo "Waiting for container to start..."
sleep 3

# 检查容器状态
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Container is running successfully!"
    echo "You can access the application at http://localhost:8080"
    echo "View logs using: docker logs -f $CONTAINER_NAME"
else
    echo "Container failed to start. Checking logs..."
    docker logs $CONTAINER_NAME
    exit 1
fi
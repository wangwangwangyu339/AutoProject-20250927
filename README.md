# AutoProject-20250927

这是一个使用 Spring Boot 和 Supabase 构建的 RESTful API 项目。

![Docker Build and Push](https://github.com/wangwangwangyu339/AutoProject-20250927/actions/workflows/docker-publish.yml/badge.svg)

## Docker 镜像

项目的 Docker 镜像已发布到 Docker Hub：

```bash
docker pull wangwangwangyu339/autoproject-20250927:latest
```

## 项目要求

- JDK 17 或更高版本
- Maven 3.8.x 或更高版本
- Supabase 账号和项目

## 本地运行步骤

1. 克隆项目
```bash
git clone https://github.com/wangwangwangyu339/AutoProject-20250927.git
cd AutoProject-20250927
```

2. 配置 Supabase
   - 登录 [Supabase](https://supabase.com)
   - 创建新项目
   - 在项目的 SQL 编辑器中执行 `src/main/resources/db/users.sql` 文件中的 SQL 脚本
   - 从项目设置中获取 URL 和 anon key

3. 配置环境变量
```bash
export SUPABASE_URL=your-project-url
export SUPABASE_KEY=your-anon-key
```

或者直接在 `src/main/resources/application.yml` 中配置：
```yaml
supabase:
  url: your-project-url
  key: your-anon-key
```

4. 构建项目
```bash
mvn clean package
```

5. 运行项目
```bash
mvn spring-boot:run
```

服务将在 http://localhost:8080 启动

## API 接口

### 用户管理
- `GET /user`: 获取所有用户
- `GET /user/{id}`: 获取指定用户
- `POST /user`: 创建新用户
- `PUT /user/{id}`: 更新用户
- `DELETE /user/{id}`: 删除用户

请求示例：
```bash
# 获取所有用户
curl http://localhost:8080/user

# 创建新用户
curl -X POST http://localhost:8080/user \
  -H "Content-Type: application/json" \
  -d '{"name":"张三","email":"zhangsan@example.com"}'
```

## Docker 运行

1. 构建镜像
```bash
docker build -t autoproject .
```

2. 运行容器
```bash
docker run -p 8080:8080 \
  -e SUPABASE_URL=your-project-url \
  -e SUPABASE_KEY=your-anon-key \
  autoproject
```

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── springboothello/
│   │               ├── SpringBootHelloApplication.java
│   │               ├── config/
│   │               │   └── SupabaseConfig.java
│   │               ├── controller/
│   │               │   ├── HelloController.java
│   │               │   └── UserController.java
│   │               └── model/
│   │                   └── User.java
│   └── resources/
│       ├── application.yml
│       └── db/
│           └── users.sql
```
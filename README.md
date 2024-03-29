[![Codacy grade](https://img.shields.io/codacy/grade/a3a61ff2d17541c493b2fa8b69e1948b?label=%E4%BB%A3%E7%A0%81%E8%B4%A8%E9%87%8F&style=for-the-badge)](https://www.codacy.com/gh/cn-src/jany/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cn-src/jany&amp;utm_campaign=Badge_Grade)
[![Codacy coverage](https://img.shields.io/codacy/coverage/a3a61ff2d17541c493b2fa8b69e1948b?label=%E8%A6%86%E7%9B%96%E7%8E%87&style=for-the-badge)](https://www.codacy.com/gh/cn-src/jany/dashboard?utm_source=github.com&utm_medium=referral&utm_content=cn-src/jany&utm_campaign=Badge_Coverage)
[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/cn-src/jany/build.yml?style=for-the-badge&label=%E6%9E%84%E5%BB%BA)](https://github.com/cn-src/jany/actions)
![GitHub](https://img.shields.io/github/license/cn-src/jany?style=for-the-badge&label=%E5%BC%80%E6%BA%90%E5%8D%8F%E8%AE%AE)
[![Maven Central](https://img.shields.io/maven-central/v/cn.javaer.jany/jany-core?label=%E6%9C%80%E6%96%B0%E7%89%88%E6%9C%AC&style=for-the-badge)](https://central.sonatype.com/namespace/cn.javaer.jany)
![GitHub last commit](https://img.shields.io/github/last-commit/cn-src/jany?style=for-the-badge&label=%E6%9C%80%E6%96%B0%E6%8F%90%E4%BA%A4)
[![Java Version](https://img.shields.io/badge/Java-%2017%20-blue?style=for-the-badge)](https://adoptopenjdk.net/)
# jany

Java 常用代码

## 模块

| 模块                                  | 描述                     |
|-------------------------------------|------------------------|
| jany-core                           | 工具类、通用模型               |
| jany-ebean                          | Ebean 数据库 ORM 框架扩展     |
| jany-jackson                        | Jackson 扩展             |
| jany-jooq                           | jOOQ 扩展                |
| jany-p6spy                          | p6spy SQL 打印框架扩展       |
| jany-platform                       | pom 版本管理               |
| jany-spring                         | Spring 工具、扩展、全局异常处理    |
| jany-spring-boot-starter            | Spring Boot 扩展         |
| jany-spring-boot-starter-p6spy      | P6spy 集成               |
| jany-spring-boot-starter-springdoc  | SpringDoc 集成           |
| jany-test                           | 测试工具                   |
| jany-validation                     | Hibernate Validator 扩展 |

## 使用

Maven

```xml

<dependency>
    <groupId>cn.javaer.jany</groupId>
    <artifactId>jany-core</artifactId>
    <version>dev-SNAPSHOT</version>
</dependency>
```

Gradle

```groovy
dependencies {
    implementation 'cn.javaer.jany:jany-core:next-SNAPSHOT'
    //kts: implementation("cn.javaer.jany:jany-core:next-SNAPSHOT")
}
```
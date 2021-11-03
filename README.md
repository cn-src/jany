[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![CI](https://github.com/cn-src/jany/actions/workflows/gradle.yml/badge.svg)](https://github.com/cn-src/jany/actions/workflows/gradle.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/a3a61ff2d17541c493b2fa8b69e1948b)](https://www.codacy.com/gh/cn-src/jany/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cn-src/jany&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/cn-src/jany/branch/dev/graph/badge.svg)](https://codecov.io/gh/cn-src/jany)
[![](https://jitpack.io/v/cn.javaer/jany.svg)](https://jitpack.io/#cn.javaer/jany)

# jany
Java 常用代码

## 使用
Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

```
```xml
<dependency>
    <groupId>cn.javaer.jany</groupId>
    <artifactId>jany-core</artifactId>
    <version>dev-SNAPSHOT</version>
</dependency>
```

Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
    //kts: maven { url = uri("https://jitpack.io") }
}
```
```groovy
dependencies {
    implementation 'cn.javaer.jany:jany-core:dev-SNAPSHOT'
    //kts: implementation("cn.javaer.jany:jany-core:dev-SNAPSHOT")
}
```
plugins {
    id("jany-library")
}
val hutoolVersion: String by project
val springdocVersion: String by project
dependencies {
    api("cn.hutool:hutool-core:$hutoolVersion")

    optionalApi("cn.hutool:hutool-crypto:$hutoolVersion")
    optionalApi("org.springdoc:springdoc-openapi-webmvc-core:$springdocVersion")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")

    testImplementation(project(":jany-test"))
}

file("src/main/java/cn/javaer/jany/JanyVersion.java").writeText(
    """
    package cn.javaer.jany;

    /**
     * 此文件自动生成。
     *
     * @author cn-src
     */
    public interface JanyVersion {

        /**
         * 返回应用程序的版本
         *
         * @return 程序的版本。
         */
        static String getVersion() {
            return "${project.version}";
        }
    }
""".trimIndent()
)
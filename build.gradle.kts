subprojects {
    apply(plugin = "maven-publish")
    group = "cn.javaer.jany"
    version = "latest-SNAPSHOT"

    repositories {
        mavenCentral()
//        maven { url = uri("https://maven.aliyun.com/repository/public/") }
    }
    configure<PublishingExtension> {
        repositories {
            maven {
                if (!project.hasProperty("signing.keyId")) {
                    version = "latest-SNAPSHOT"
                }
                url = uri(System.getenv("publicSnapshotsRepoUrl"))
                credentials {
                    username = System.getenv("publicRepoUsername")
                    password = System.getenv("publicRepoPassword")
                }
            }
        }
    }
}
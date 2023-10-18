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
                val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                val snapshotsRepoUrl = uri("https://packages.aliyun.com/maven/repository/2163442-snapshot-zWHJ5H/")
                if (version.toString().endsWith("SNAPSHOT")) {
                    url = snapshotsRepoUrl
                    credentials {
                        username = System.getenv("aliyunRepoUsername")
                        password = System.getenv("aliyunRepoPassword")
                    }
                }
                else {
                    url = releasesRepoUrl
                    credentials {
                        username = providers.gradleProperty("ossrhRepoUsername").getOrElse("")
                        password = providers.gradleProperty("ossrhRepoPassword").getOrElse("")
                    }
                }
            }
        }
    }
}
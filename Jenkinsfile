pipeline {
    agent any
    stages {
        stage('build-Java8') {
            tools {
                    jdk "Java8"
            }
            steps {
                sh "./gradlew clean build"
            }
        }
        stage('build-Java11') {
            tools {
                    jdk "Java11"
            }
            steps {
                sh "./gradlew clean build"
            }
        }
    }
}
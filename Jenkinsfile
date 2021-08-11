pipeline {
  agent any
  stages {
  stage('Gradle Build') {
      steps {
        dir('backend'){sh 'chmod +x ./gradlew'
                          sh './gradlew clean build'}
      }
    }
  stage('JAR Copy') {
      steps {
        sh 'cp ./backend/build/libs/ssafy-fifth-web-common-project-1.0-SNAPSHOT.jar /home/ubuntu/jenkins/app.jar'
      }
    }
  stage('Deploy Server') {
      steps {
        sh 'java -jar /home/ubuntu/jenkins/app.jar'
      }
    }
  }
}
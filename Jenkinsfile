pipeline {
  agent none
  stages {
  stage('Gradle Build') {
      steps {
        sh 'chmod +x ./backend/gradlew'
        sh './backend/gradlew clean build'
      }
    }
  stage('JAR Copy') {
      steps {
        sh 'cp ./backend/build/libs/ssafy-fifth-web-common-project-1.0-SNAPSHOT.jar /home/ubuntu/app.jar'
      }
    }
  stage('Deploy Server') {
      steps {
        sh 'nohup java -jar /home/ubuntu/app.jar &'
      }
    }
  }
}
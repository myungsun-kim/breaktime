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
        sh 'sudo cp ./backend/build/libs/ssafy-fifth-web-common-project-1.0-SNAPSHOT.jar /home/ubuntu/app.jar'
      }
    }
  stage('Deploy Server') {
      steps {
        sh 'sudo nohup java -jar /home/ubuntu/app.jar &'
      }
    }
  }
}
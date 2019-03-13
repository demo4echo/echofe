pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        bat(script: 'gradlew.bat tasks', returnStdout: true)
      }
    }
  }
}
pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'hello'
        bat(script: 'gradlew --version', returnStdout: true)
      }
    }
  }
}
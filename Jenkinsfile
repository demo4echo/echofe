pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'hello'
        bat(script: 'gradlew --stacktrace k8sBuild', returnStdout: true, returnStatus: true)
      }
    }
  }
}
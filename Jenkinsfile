pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'hello'
        bat(script: 'docker --version', returnStdout: true, returnStatus: true)
      }
    }
  }
}
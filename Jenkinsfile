pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        bat 'gradlew k8sBuild'
      }
    }
    stage('deploy') {
      steps {
        bat(script: 'gradlew k8sDeploy', returnStdout: true)
      }
    }
    stage('test') {
      steps {
        bat(script: 'gradlew test', returnStdout: true)
      }
    }
    stage('undeploy') {
      steps {
        bat(script: 'gradlew k8sUndeploy', returnStdout: true)
      }
    }
  }
}
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
        bat 'gradlew k8sDeploy'
      }
    }
    stage('test') {
      steps {
        bat './gradlew test'
        sh './gradlew test'
      }
    }
    stage('undeploy') {
      steps {
        bat 'gradlew k8sUndeploy'
      }
    }
  }
}
pipeline {
  agent none
  stages {
    stage('build') {
      agent {
        docker {
          image 'maven:3-alpine'
        }
      }
      steps {
        sh 'mvn --version'
      }
    }
    stage('deploy') {
      agent any
      steps {
        bat 'gradlew k8sDeploy'
      }
    }
    stage('test') {
      agent any
      steps {
        bat './gradlew test'
      }
    }
    stage('undeploy') {
      agent any
      steps {
        bat 'gradlew k8sUndeploy'
      }
    }
  }
}
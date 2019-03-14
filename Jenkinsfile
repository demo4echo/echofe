pipeline {
  agent none
  stages {
    stage('build') {
      agent {
        docker {
          image 'openjdk:8-jre-alpine'
          args '-name dockerctl -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker'
        }

      }
      steps {
        bat 'gradlew k8sBuild'
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
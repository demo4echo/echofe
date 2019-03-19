pipeline {
  agent {
    image 'openjdk:8-jdk-alpine'
    args '--name jenkinsSlave -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker -v /usr/local/sbin:/usr/local/sbin -v $HOME/.docker:/root/.docker -v $HOME/.kube:/root/.kube -v $HOME/.gradle:/root/.gradle'
  }
  stages {
    stage('build') {
      agent any
      steps {
        sh './gradlew k8sBuild --no-daemon'
      }
    }
    stage('deploy') {
      agent any 
      steps {
        sh './gradlew k8sDeploy --no-daemon'
      }
    }
  }
}

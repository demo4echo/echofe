pipeline {
  agent none
  stages {
    stage('build') {
      agent {
        docker {
          image 'openjdk:8-jre-alpine'
          args '--rm -d --name dockerctl -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker'
        }

      }
      steps {
        sh 'docker ps'
      }
    }
  }
}
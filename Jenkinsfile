pipeline {
  agent none
  stages {
    stage('build') {
      agent {
        docker {
          image 'openjdk:8-jdk-alpine'
          args '--name dockerctl -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker -v $HOME/.gradle:/root/.gradle -v $HOME/.docker:/root/.docker'
        }

      }
      steps {
        sh './gradlew k8sBuild --no-daemon'
      }
    }
    stage('deploy') {
      agent {
        docker {
          image 'lachlanevenson/k8s-kubectl'
          args '--name kubectl -v /etc/kubernetes:/etc/kubernetes --entrypoint=\'\''
        }

      }
      steps {
        sh './gradlew k8sDeploy --no-daemon -Pkubeconfig="/etc/kubernetes/kubelet.conf" -Pserver="https://192.168.99.100:8443"'
      }
    }
  }
}
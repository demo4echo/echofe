pipeline {
  agent {
    docker {
      image 'openjdk:8-jdk-alpine'
      args '--name jenkins-slave -v /var/run/docker.sock:/var/run/docker.sock -v /usr/bin/docker:/usr/bin/docker -v /usr/local/sbin:/usr/local/sbin -v $HOME/.docker:/root/.docker -v $HOME/.kube:/root/.kube -v $HOME/.gradle:/root/.gradle'
    }

  }
  stages {
    stage('build') {
      steps {
        sh './gradlew k8sBuild --no-daemon'
      }
    }
    stage('deploy') {
      steps {
        sh './gradlew k8sDeploy --no-daemon'
      }
    }
    stage('test') {
      steps {
        sh './gradlew test --no-daemon'
      }
    }
    stage('undeploy') {
      steps {
        sh './gradlew k8sUndeploy --no-daemon'
      }
    }
  }
  post {
    always {
        echo 'One way or another, I have finished'
    }
    success {
        echo 'I succeeeded!'
    }
    unstable {
        echo 'I am unstable :/'
    }
    failure {
        echo 'I failed :('
    }
    changed {
        echo 'Things were different before...'
    }
  }
}

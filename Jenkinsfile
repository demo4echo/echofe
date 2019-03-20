pipeline {
  agent {
    kubernetes {
//      defaultContainer 'jdk-gradle-docker-k8s'
      yamlFile 'k8s-jenkins-slave.yaml'
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
      junit 'build/test-results/**/*.xml'
    }
    unstable {
      echo 'I am unstable :/'
    }
    failure {
      echo 'I failed :('
 //     archiveArtifacts artifacts: 'Jenkinsfile', fingerprint: true
    }
    changed {
      echo 'Things were different before...'
    }
  }
}
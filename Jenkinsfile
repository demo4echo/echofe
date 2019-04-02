pipeline {
  agent {
    kubernetes {
//		cloud 'kubernetes'
		label 'jenkins-slave-pod-agent'
      defaultContainer 'jdk-gradle-docker-k8s'
      yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
    }
  }
  stages {
    stage('build') {
      steps {
     		sh './gradlew helmBuild --no-daemon'
      }
    }
    stage('package') {
      steps {
     		sh './gradlew helmPackage --no-daemon'
      }
    }
    stage('install') {
      steps {
     		sh './gradlew helmInstall --no-daemon'
      }
    }
    stage('test') {
      steps {
     		sh './gradlew test --no-daemon'
      }
    }
    stage('uninstall') {
      steps {
     		sh './gradlew helmUninstall --no-daemon'
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
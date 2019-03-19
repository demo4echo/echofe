pipeline {
  agent {
    kubernetes {
//      defaultContainer 'jdk-gradle-docker-k8s'
//      yamlFile '-k8s-jenkins-slave.yaml'
		yaml """
apiVersion: v1
kind: Pod
metadata:
  name: jenkins-slave
  labels:
    slave-agent: jenkins
spec:
  containers:
  - name: jdk-gradle-docker-k8s
    image: openjdk:8-jdk-alpine
    command:
    - cat
    tty: true
    volumeMounts:
    - name: docker-socket
      mountPath: /var/run/docker.sock
    - name: docker-bin
      mountPath: /usr/bin/docker
    - name: kubectl-bin-in-path
      mountPath: /usr/local/sbin
    - name: docker-cache-vol
      mountPath: /root/.docker
    - name: kubectl-cache-vol
      mountPath: /root/.kube
    - name: gradle-cache-vol
      mountPath: /root/.gradle
  volumes:
  - name: docker-socket
    hostPath:
      path: /var/run/docker.sock
#      type: Socket
  - name: docker-bin
    hostPath:
      path: /usr/bin/docker
#      type: File
  - name: kubectl-bin-in-path
    hostPath:
      path: /usr/local/sbin
#      type: Directory
  - name: docker-cache-vol
    hostPath:
      path: /root/.docker
#      type: Directory
  - name: kubectl-cache-vol
    hostPath:
      path: /root/.kube
#      type: Directory
  - name: gradle-cache-vol
    hostPath:
      path: /root/.gradle
#      type: Directory
"""		
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
      junit 'build/test-results/**/*.xml'

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
pipeline {
	agent none
/*
	agent {
    kubernetes {
		cloud 'development'
//		cloud getCloudName()
//		cloud env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
		label 'jenkins-slave-pod-agent'
      defaultContainer 'jdk-gradle-docker-k8s'
      yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
    }*/
  	environment {
	   ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME = getCloudName()
	}
  stages {
    stage('build') {
		agent {
	    kubernetes {
			cloud env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
			label 'jenkins-slave-pod-agent'
	      defaultContainer 'jdk-gradle-docker-k8s'
	      yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
	    }
	   }
      steps {
			script {
				def cloudName = cloud getCloudName()
			   println "Cloud name is: ${cloudName}"
			}
     		sh './gradlew dockerBuildAndPublish --no-daemon'
      }
    }
    stage('package') {
		agent {
	    kubernetes {
			cloud env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
			label 'jenkins-slave-pod-agent'
	      defaultContainer 'jdk-gradle-docker-k8s'
	      yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
	    }
	   }
      steps {
     		sh './gradlew helmPackage --no-daemon'
      }
    }
	 stage('install') {
		agent {
	    kubernetes {
			cloud env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
			label 'jenkins-slave-pod-agent'
	      defaultContainer 'jdk-gradle-docker-k8s'
	      yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
	    }
	   }
      steps {
     		sh './gradlew helmInstall --no-daemon'
      }
    }
    stage('test') {
		agent {
	    kubernetes {
			cloud env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
			label 'jenkins-slave-pod-agent'
	      defaultContainer 'jdk-gradle-docker-k8s'
	      yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
	    }
	   }
      steps {
     		sh './gradlew test --no-daemon'
      }
    }
    stage('uninstall') {
		agent {
	    kubernetes {
			cloud env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
			label 'jenkins-slave-pod-agent'
	      defaultContainer 'jdk-gradle-docker-k8s'
	      yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
	    }
	   }
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

def getCloudName() {
//	node('master') {
//	node('jenkins-slave-pod-agent') {
	node {
		println "Going to sleep"
		sleep(60000)
		def props = readProperties interpolate: true, file: 'EnvFile.properties'

		println "We got: [" + props.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME + "]"
		return props.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
	}
}

pipeline {
	agent {
		kubernetes {
			cloud resolveCloudName()
			label 'jenkins-slave-pod-agent'
			defaultContainer 'jdk-gradle-docker-k8s'
			yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
		}
	}
	options { 
		timestamps() 
	}
	environment {
		DUMMY_ENV_VAR = assimilateEnvironmentVariables()
	}
	stages {
		stage('\u2776 build') {
			steps {
				echo "We have the following for cloud name: [${env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME}]"
				sh './gradlew dockerBuildAndPublish --no-daemon'
			}
		}
		stage('\u2777 package') {
			steps {
				sh './gradlew helmPackage --no-daemon'
			}
		}
		stage('\u2778 install') {
			steps {
				sh './gradlew helmInstall --no-daemon'
			}
		}
		stage('\u2779 test') {
			steps {
				sh './gradlew test --no-daemon'
			}
		}
		stage('\u2780 uninstall') {
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
 //     	archiveArtifacts artifacts: 'Jenkinsfile', fingerprint: true
    	}
    	changed {
      	echo 'Things were different before...'
    	}
  	}
}

def resolveCloudName() {
	node {

		return resolveCloudNameByBranchName()
//		return assimilateEnvironmentVariables()
//		return null
	}
}

def resolveCloudNameByBranchName() {
	node {
		println "Branch name is: [${env.BRANCH_NAME}]"

		if (env.BRANCH_NAME == 'master') {
			return 'production'
		} else if (env.BRANCH_NAME == 'integration') {                 
			return 'staging'
		}
		else {
			return 'development'		    
		}
	}
}

def assimilateEnvironmentVariables() {
	node {
//		checkout(scm)

		def props = readProperties interpolate: true, file: 'EnvFile.properties'
		props.each {
			key,value -> env."${key}" = "${value}" 
		}
		
		println "Cloud name is: [${env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME}]"

		return env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
	}
}

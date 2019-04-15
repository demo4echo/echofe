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
	stages {
		stage('\u2776 build') {
			steps {
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

//		println "This is the SCM: [${scm.GIT_COMMIT}]" => not permitted
//		def scmVars = checkout(scm)
//		println "Git commit is:[${scmVars.GIT_COMMIT}]"
//		def commitId = sh(returnStdout: true, script: 'git rev-parse HEAD')
//		def shortCommit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
	
		// We are missing the commit ID from Jenkins!!!	
//		sh(returnStdout: true, script: 'git show e812e5a0546c325a9ecdf0ce4b247657050c01af:EnvFile.properties > EnvFile.properties')

//		println "Git commit 1 is:[${GIT_COMMIT}]"
//		println "Git commit 2 is:[${env.GIT_COMMIT}]"
		
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
		// Fetch the complete repository
		checkout(scm)

		// Fetch only the target file by locating the commit ID
//		def commitId = sh(returnStdout: true, script: 'git rev-parse HEAD')
//		println "Git commit ID is:[${commitId}]"
//		sh(returnStdout: true, script: 'git show ${commitId}:EnvFile.properties > EnvFile.properties')

		def props = readProperties interpolate: true, file: 'EnvFile.properties'
		props.each {
			key,value -> env."${key}" = "${value}" 
		}
		
		println "Cloud name is: [${env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME}]"

		return env.ECHOFE_JENKINS_K8S_DEPLOYMENT_CLOUD_NAME
	}
}

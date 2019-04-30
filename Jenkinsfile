pipeline {
	agent {
		kubernetes {
			cloud resolveCloudNameByBranchName()
			label 'jenkins-slave-pod-agent'
			defaultContainer 'jdk-gradle-docker-k8s-helm'
//			yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
			yaml constructJenkinsSlaveManifest()
		}
	}
	options { 
		timestamps() 
	}
	environment {
		// We use this dummy environment variable to load all the properties from the designated file into environment variable (per brach)
		// This is indeed a pseudo comment for real
		X_EFRAT_ECHO_ECHOFE_DUMMY_ENV_VAR = assimilateEnvironmentVariables()
	}
	stages {
		stage('\u2776 build \u2728') {//\u1F6E0
			steps {
				sh './gradlew dockerBuildAndPublish --no-daemon'
			}
		}
		stage('\u2777 package \u2728') {//\u1F4E6
			steps {
				sh './gradlew helmPackage --no-daemon'
			}
		}
		stage('\u2778 install \u2728') {//\u1F3F4
			steps {
				sh './gradlew helmInstall --no-daemon'
			}
		}
		stage('\u2779 test \u2728') {//\u1F321
			steps {
				sh './gradlew test --no-daemon'
			}
		}
		stage('\u277A uninstall \u2728') {//\u1F3F3
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

//
// Determine the applicable k8s cloud (towards Jenkins' configuration of the K8S plugin)
//
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

//
// Load all the properties in the per brnach designated file as environment variables
//
def assimilateEnvironmentVariables() {
	node {
//		checkout(scm) => don't need it as we'll call the function after the repository has been fetched (checkout(scm) is called in the 'agent' phase)

		def props = readProperties interpolate: true, file: 'EnvFile.properties'
		props.each {
			key,value -> env."${key}" = "${value}" 
		}
		
		println "Cloud name is: [${env.JENKINS_SLAVE_K8S_DEPLOYMENT_CLOUD_NAME}]"

		return env.JENKINS_SLAVE_K8S_DEPLOYMENT_CLOUD_NAME
	}
}

//
// Load, resolve and return the manifest of the designated jenkins slave to be used
//
def constructJenkinsSlaveManifest() {
	node {
		// Read the environement variables file	
		def props = readProperties interpolate: true, file: 'EnvFile.properties'
	
		// Load the target manifest file
//		String fileContents = new File('/path/to/file').getText('UTF-8')		
//		String manifestFileContent = new File(props."JENKINS_SLAVE_MANIFEST_FILE_NAME").text
		def manifestFileContent = readYaml file: props."JENKINS_SLAVE_MANIFEST_FILE_NAME"		

		// Define bindings		
		def binding = [:]
		props.each {
			key,value -> binding."${key}" = "${value}" 
		}
		
		// Resolve environement variables
		def engine = new groovy.text.SimpleTemplateEngine()
		def template = engine.createTemplate(manifestFileContent).make(binding)
		
		// Return the result
		String resolvedJenkinsSlaveManifest = "'''\n" 
		resolvedJenkinsSlaveManifest.concat(template.toString())
		resolvedJenkinsSlaveManifest.concat("\n'''")
		return resolvedJenkinsSlaveManifest
	}
}
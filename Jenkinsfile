pipeline {
	agent {
		kubernetes {
			cloud resolveCloudNameByBranchName()
			label 'jenkins-slave-pod-agent'
			defaultContainer 'jdk-gradle-docker-k8s-helm'
			yamlFile 'Jenkinsfile.JenkinsSlaveManifest.yaml'
			namespace assembleNamespace()
		}
	}
	options { 
		timestamps() 
	}
	environment {
		// We use this dummy environment variable to load all the properties from the designated file into environment variable (per brach)
		// This is indeed a pseudo comment 4 Tiran
		X_EFRAT_ECHO_ECHOFE_DUMMY_ENV_VAR = assimilateEnvironmentVariables()
	}
	stages {
		stage('\u2776 setup \u2728') {//\u1F4A1
			steps {
				sh 'cp -ar ./.docker /root/.docker'
				sh 'cp -ar ./.kube /root/.kube'
			}
		}
		stage('\u2777 build \u2728') {//\u1F6E0
			steps {
				sh './gradlew dockerBuildAndPublish --no-daemon'
			}
		}
		stage('\u2778 package \u2728') {//\u1F4E6
			steps {
				sh './gradlew helmPackage --no-daemon'
			}
		}
		stage('\u2779 install \u2728') {//\u1F3F4
			when {
				environment name: 'CLOUD_NAME', value: 'development'
			}
			steps {
				sh './gradlew helmUpdate --no-daemon'
			}
		}
		stage('\u277A upgrade \u2728') {//\u1F3F4
			when {
				not {
					environment name: 'CLOUD_NAME', value: 'development'
				}
			}
			steps {
				sh './gradlew helmUninstall --no-daemon'
				sh './gradlew helmUpdate --no-daemon'
			}
		}
		stage('\u277B verify \u2728') {
			steps {
				sh './gradlew helmTestAndClean --no-daemon'
			}
		}
		stage('\u277C test \u2728') {//\u1F321
			steps {
				sh './gradlew test --no-daemon'
			}
		}
		stage('\u277D uninstall \u2728') {//\u1F3F3
			when {
				environment name: 'CLOUD_NAME', value: 'development'
			}
			steps {
				sh './gradlew helmUninstall --no-daemon'
			}
		}
	}
	post {
		always {
			echo 'One way or another, I have finished'
			
			script {
				// Delete the target namespace only if in development area
				if (env.CLOUD_NAME == 'development') {
					def targetNamespace = resolveNamespaceByBranchName()
					echo 'Going to delete target namespace: [${targetNamespace}]'
					disperseNamespace()		    
				}
			}
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
//			archiveArtifacts artifacts: 'Jenkinsfile', fingerprint: true
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
//	node(env.NODE_NAME) {
//	node('master') {
		println "Within resolveCloudNameByBranchName() => Node name is: [${env.NODE_NAME}]"

		println "Branch name is: [${env.BRANCH_NAME}]"

		if (env.BRANCH_NAME == 'master') {
			env.CLOUD_NAME = 'production'
		} else if (env.BRANCH_NAME == 'integration') {                 
			env.CLOUD_NAME = 'staging'
		}
		else {
			env.CLOUD_NAME = 'development'		    
		}
		
		println "Resolved cloud name is: [${env.CLOUD_NAME}]"
		
		// Return the resolved cloud name
		return env.CLOUD_NAME
	}
}

//
// Determine the applicable k8s namespace (for running the Jenkins Slave Pod (mentioned above))
//
def resolveNamespaceByBranchName() {
	node {
		println "Within resolveNamespaceByBranchName() => Node name is: [${env.NODE_NAME}]"

		println "Branch name is: [${env.BRANCH_NAME}]"

		if (env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'integration') {                 
			env.RESOLVED_NAMESPACE = 'demo4echo'
		}
		else {
			env.RESOLVED_NAMESPACE = env.BRANCH_NAME
		}
		
		println "Resolved namespace is: [${env.RESOLVED_NAMESPACE}]"
		
		// Return the resolved namespsace
		return env.RESOLVED_NAMESPACE
	}
}

//
// Check if the target namespace exists, returns true if it exists, false otherwise
//
def assessNamespace() {
	def targetNamespace = resolveNamespaceByBranchName()
	def stdout = new StringBuilder(), stderr = new StringBuilder()
	def process = 'kubectl get namespace ${targetNamespace}'.execute()
	
	// Check if the namespace is defined
	process.consumeProcessOutput(stdout, stderr)
	process.waitForOrKill(1000)
	println "out> $stdout\nerr> $stderr"
	
	def exitCode = process.exitValue()
	println "exitCode> $exitCode"

	// Return the result
	if (exitCode == 0) {
		return true
	}
	else {
		return false
	}
}

//
// Ensure the target namespace is defined
//
def assembleNamespace() {
	def doesTargetNamespaceExist = assessNamespace()
	def stdout = new StringBuilder(), stderr = new StringBuilder()
	
	// If namespace doesn't exist, create it
	if (doesTargetNamespaceExist == false) {
		def process = 'kubectl create namespace ${targetNamespace}'.execute()
		process.consumeProcessOutput(stdout, stderr)
		process.waitForOrKill(1000)
		println "out> $stdout\nerr> $stderr"
		
		def exitCode = process.exitValue()
		println "exitCode> $exitCode"
	}

	// Return the desired namespace
	return resolveNamespaceByBranchName()
}

//
// Discard the target namespace
//
def disperseNamespace() {
	def doesTargetNamespaceExist = assessNamespace()
	def stdout = new StringBuilder(), stderr = new StringBuilder()

	// If namespace exists, delete it
	if (doesTargetNamespaceExist == true) {
		def process = 'kubectl delete namespace ${targetNamespace}'.execute()
		process.consumeProcessOutput(stdout, stderr)
		process.waitForOrKill(1000)
		println "out> $stdout\nerr> $stderr"
		
		def exitCode = process.exitValue()
		println "exitCode> $exitCode"
	}

	// Return the deleted namespace
	return resolveNamespaceByBranchName()
}

//
// Load all the properties in the per brnach designated file as environment variables
//
def assimilateEnvironmentVariables() {
//	node(env.NODE_NAME) {
//		checkout(scm) => don't need it as we'll call the function after the repository has been fetched (checkout(scm) is called in the 'agent' phase)

		println "Within assimilateEnvironmentVariables() => Node name is: [${env.NODE_NAME}]"

		def props = readProperties interpolate: true, file: 'EnvFile.properties'
		props.each {
			key,value -> env."${key}" = "${value}" 
		}
		
		println "JENKINS_SLAVE_K8S_DEPLOYMENT_CLOUD_NAME value is: [${env.JENKINS_SLAVE_K8S_DEPLOYMENT_CLOUD_NAME}]"

		return env.JENKINS_SLAVE_K8S_DEPLOYMENT_CLOUD_NAME
//	}
}

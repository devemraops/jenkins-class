node {
    properties(
	[buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
    parameters(
		[string(defaultValue: 'apply', description: 'Should I build or destroy? ', name: 'TF_ACTION', trim: true),
        choice(choices: 
			[
                'dev', 
            ], 
		description: 'Which Environment should we deploy?', 
		name: 'ENVIR')])])
	stage("Clone a Repo"){
        checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/devemraops/jenkins-class.git']]])
    }
	stage("Build RDS"){
		ws("${workspace}/AWS/RDS"){
            sh "ENVIRONMENT=${ENVIR}   make tf-fmt  tf-init  tf-plan  tf-${TF_ACTION}"
        }
    }
	stage("Send Notification"){
		echo "hello"
    }
}

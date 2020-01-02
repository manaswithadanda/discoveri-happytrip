pipeline {
	agent any
	stages {
		stage('Source') { 
			steps {
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/manaswithadanda/discoveri-happytrip.git']]])
			}
		}
		stage('Build') { 
			tools {
				jdk 'jdk8'
				maven 'MavenConfiguration'
			}
			steps {
				//powershell 'java -version'
				//powershell 'mvn -version'
				//powershell 'mvn clean package'
				powershell label: '', script: 'mvn clean package'
			}
		}
		
		stage('Code Analysis') { 
		
			environment {
				def scannerHome = tool 'GTSonarQubeScanner'
			}
			
			when {
				expression { return params.SonarQube }
			}
			
			steps {
				
				withSonarQubeEnv('SonarQubeServer') {
				    bat "\"${scannerHome}\\bin\\sonar-scanner.bat\" -Dsonar.host.url=http:\"\"localhost:9000 -Dsonar.projectName=HappyTrip -Dsonar.projectVersion=1.0 -Dsonar.projectKey=HappyTrip:app -Dsonar.sources=. -Dsonar.java.binaries=."
				}
			}
		}
		
		stage('Deploy approval') {
			steps {
				script {
					def result = input(id: 'Proceed1', message: 'Deployment Approval', parameters: [[$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']])
				}
			}
		}
		
		stage('Deploy') {
			steps{
				echo "Deploying"
				deploy adapters: [tomcat7(credentialsId: 'a8ed63d0-4d18-4e37-bf24-258074b960e6', path: '', url: 'http://localhost:8085')], contextPath: 'HappyTrip', war: '**/*.war'
			}
		}
	}
	
	post {
        	always {
			
			//Publich TestNG Result
			step([$class: 'Publisher'])
            
			//Sending Email
     			step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'manaswitha.danda@pratian.com', sendToIndividuals: false])
		}
    	}
}

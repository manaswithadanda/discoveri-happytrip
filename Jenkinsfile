pipeline {
	agent any
	//triggers {
    //    pollSCM('H/5 * * * *')
    //}
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
				script {
					if(params.RunTests == false) {
						bat "mvn clean package -DskipTests"
					}
					else {
						bat "mvn clean package"
					}
					archiveArtifacts '/**/*.war'
				}
			}
		}
		
		stage('Code Analysis') { 
		
			when {
				expression { return params.SonarQube }
			}
			
			steps {
				script {
					def scannerHome = tool 'GTSonarQubeScanner'
					withSonarQubeEnv('SonarQubeServer') {
					    bat "\"${scannerHome}\\bin\\sonar-scanner.bat\" -Dsonar.host.url=http:\"\"localhost:9000 -Dsonar.projectName=HappyTrip -Dsonar.projectVersion=${currentBuild.number} -Dsonar.projectKey=HappyTrip:app -Dsonar.sources=. -Dsonar.java.binaries=."
					}
				}
			}
		}
		
		stage('Deploy approval') {
			steps {
				script {
					//def result = input(id: 'Proceed1', message: 'Deployment Approval', parameters: [[$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']])
					input id: 'Custid', message: 'Need Approval for Deployment', ok: 'Approve', submitter: 'projectmanager, pratian', submitterParameter: 'ShouldBeSubmitterAction'
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
			
			//Publich TestNG Result & Threshold to fail build
			step([$class: 'Publisher', failedFails: 60, unstableFails: 100])
            
			//Sending Email
     		emailext body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:
Check console output at $BUILD_URL to view the results.
${FILE, path="target/surefire-reports/emailable-report.html"}''', subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'manaswitha.danda@pratian.com'
			
			}
    }
}



pipeline {
    agent any 
    
    tools{
           maven 'maven'
        }
        
    stages {
        stage('Initialize') { 
            steps {
                sh'''
                      echo "PATH=${PATH}"
                      echo "M2_HOME=${M2_HOME}"
                      pwd
                      ls -la
                '''
                
            }
        }
        
        stage('build'){
            steps{
                sh  'mvn -v'
                sh  '      '
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/eddineimad0/transcription_platform']])
                sh ' mvn clean install '
                
            }
        }
    }
}


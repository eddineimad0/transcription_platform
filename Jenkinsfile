

pipeline {
    agent any 
    
    tools{
           maven 'Maven'
        }
        
    stages {
        stage('Initialize') { 
            steps {
                sh'''
                      
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


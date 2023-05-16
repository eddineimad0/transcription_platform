pipeline{
  agent any
  tools{
    maven 'maven'
  }
 
  stages{
    stage('Initialize'){
      steps{
        sh'''
              echo "PATH=${PATH}"
              echo "M2_HOME=${M2_HOME}"
              '''
            }
    }
    
    stage('build'){
      steps{
        sh ''' 
        ls -a
        mvn -f /var/lib/jenkins/workspace/TranscriptionApp_CI_CD/pom.xml install
        '''
      }
    }
  }
}


pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/eddineimad0/transcription_platform.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'sudo mvn clean install'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            
            post {
                always {
                    // Générer le rapport de test avec JaCoCo
                    jacoco(execPattern: '**/target/*.exec')
                }
            }
        }
        
        stage('Deploy') {
            steps {
                // Déployer sur Apache Tomcat
                // Assurez-vous que votre serveur Apache Tomcat est configuré dans Jenkins
                // et que les informations d'accès sont fournies
                sh 'mvn tomcat7:redeploy'
            }
        }
        
        stage('Security Scan') {
            steps {
                // Analyse de sécurité avec OWASP ZAP
                // Assurez-vous d'installer le plugin "OWASP ZAP" dans votre instance Jenkins
                
                // Exemple de commande pour lancer une analyse ZAP
                sh 'zap-baseline.py -t http://localhost:8080/transcription-platform'
            }
        }
        
        stage('SonarQube Scan') {
            steps {
                // Analyse de code avec SonarQube
                // Assurez-vous d'installer le plugin "SonarQube Scanner" dans votre instance Jenkins
                
                // Exemple de commande pour lancer une analyse SonarQube
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
    
    post {
        always {
            // Générer le rapport de couverture de code avec JaCoCo
            jacoco(execPattern: '**/target/*.exec')
            
            // Publier les rapports de test et de couverture de code à SonarQube
            sonarQubePublisher()
        }
        
        success {
            // Ajoutez ici les étapes pour les notifications ou actions spécifiques en cas de succès du pipeline
            sh "pwd"
        }
        
        failure {
            // Ajoutez ici les étapes pour les notifications ou actions spécifiques en cas d'échec du pipeline
            sh "pwd"
        }
    }
}

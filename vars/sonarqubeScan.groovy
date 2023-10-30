def call(String projectKey, String projectName, String sonarQubeServer) {
    pipeline {
        agent any
        stages {
            stage('SonarQube Analysis') {
                steps {
                    script {
                        def scannerHome = tool 'SonarScanner'; // Assumes you set up SonarScanner as a tool in Jenkins
                        def scannerCmd = "${scannerHome}/bin/sonar-scanner" +
                                         " -Dsonar.projectKey=${projectKey}" +
                                         " -Dsonar.projectName='${projectName}'" +
                                         " -Dsonar.host.url=${sonarQubeServer}"
                        sh scannerCmd
                    }
                }
            }
        }
    }
}

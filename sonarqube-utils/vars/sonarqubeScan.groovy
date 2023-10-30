def call(String projectKey, String projectName, String sonarHost, String sonarToken) {
    pipeline {
        agent any
        stages {
            stage('SonarQube Analysis') {
                steps {
                    script {
                        withSonarQubeEnv('SonarScanner') {
                            sh "mvn -Dmaven.test.failure.ignore=true -Dcheckstyle.skip sonar:sonar \
                                -Dsonar.host.url=${sonarHost} \
                                -Dsonar.projectKey=${projectKey} \
                                -Dsonar.token=${sonarToken} \
                                -Dsonar.projectName='${projectName}'"
                        }
                    }
                }
            }
        }
    }
}

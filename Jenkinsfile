pipeline {
    agent any
    
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials') // Create Docker Hub credentials in Jenkins
        SLACK_CHANNEL = '#devsecops-build-status'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true -Dcheckstyle.skip clean package'
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def customImageName = "meki69/simple-java-app:${env.BUILD_NUMBER}"
                    sh "docker build -t $customImageName ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASSWORD']]) {
                        def customImageName = "meki69/simple-java-app:${env.BUILD_NUMBER}"
                        sh "docker login -u $DOCKERHUB_USER -p $DOCKERHUB_PASSWORD"
                        sh "docker push $customImageName"
                    }
                }
            }
        }
        stage("TRIVY"){
            steps{
                sh "trivy image cloudsheger/simple-java-app:${env.BUILD_NUMBER} > trivy.txt" 
            }
        }

        stage("Deploy Using Docker"){
            steps{
                sh " docker run -d --name petclinic -p 8082:8082 cloudsheger/simple-java-app:${env.BUILD_NUMBER} "
            }
        }

        stage('Send Slack Alert') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('FAILURE') }
            }
            steps {
                script {
                    slackSend(
                        color: 'danger',
                        channel: env.SLACK_CHANNEL,
                        message: "Build failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}\n${env.BUILD_URL}",
                        tokenCredentialId: 'slack-token'
                    )
                }
            }
        }

        stage('Clean Up Docker Images') {
            steps {
                script {
                    sh 'docker system prune -f'
                }
            }
        }
    }

    post {
        success {
            script {
                slackSend(
                    color: 'good',
                    channel: env.SLACK_CHANNEL,
                    message: "Build successful: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}\n${env.BUILD_URL}",
                    tokenCredentialId: 'slack-token'
                )
            }
        }
    }
}

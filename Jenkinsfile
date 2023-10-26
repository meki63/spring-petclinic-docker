pipeline {
    agent any
    
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials') // Create Docker Hub credentials in Jenkins
        SLACK_CHANNEL = '#build-status'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def customImageName = "cloudsheger/simple-java-app:${env.BUILD_NUMBER}"
                    
                    sh "docker build -t $customImageName ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASSWORD']]) {
                        def customImageName = "your-docker-hub-username/your-app:${env.BUILD_NUMBER}"
                        
                        sh "docker login -u $DOCKERHUB_USER -p $DOCKERHUB_PASSWORD"
                        sh "docker push $customImageName"
                    }
                }
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

@Library('my-shared-library') _
import com.example.CustomUtils

pipeline {
    agent any

    environment {
        CUSTOM_IMAGE_NAME = "cloudsheger/simple-java-app:${env.BUILD_NUMBER}"
        DOCKER_PORT = "8082"
    }

    stages {
        stage('docker build') {
            steps {
                script {
                    // Call the buildDocker() function from your shared library
                    def buildResult = buildDocker()

                    if (buildResult == 'SUCCESS') {
                        echo 'Docker build successful'
                    } else {
                        error 'Docker build failed'
                    }
                }
            }
        }

        stage('scan Docker image') {
            steps {
                script {
                    // Call the scanDockerImage() function from your shared library
                    def scanResult = scanDockerImage(env.CUSTOM_IMAGE_NAME)

                    if (scanResult == 'SUCCESS') {
                        echo 'Docker image scanned successfully'
                    } else {
                        error 'Docker image scan failed'
                    }
                }
            }
        }

        stage('deploy Docker image') {
            steps {
                script {
                    // Call the deployDockerImage() function from your shared library
                    def deployResult = deployDockerImage(env.CUSTOM_IMAGE_NAME, env.DOCKER_PORT.toInteger())

                    if (deployResult == 'SUCCESS') {
                        echo 'Docker image deployed successfully'
                    } else {
                        error 'Docker image deployment failed'
                    }
                }
            }
        }

        stage('cleanup Docker images') {
            steps {
                script {
                    // Call the cleanupDockerImages() function from your shared library
                    CustomUtils.cleanupDockerImages()
                }
            }
        }
    }
}

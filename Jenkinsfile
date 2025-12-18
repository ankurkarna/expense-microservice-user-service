pipeline {
    agent any

    environment {
        DOCKER_HUB_REPO = 'ankurkarna/expense-microservice-user-service'
        DOCKER_HUB_CREDENTIALS = 'docker-hub-creds'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'chmod +x mvnw || true'
                sh './mvnw clean test || mvn clean test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_HUB_REPO}:${BUILD_NUMBER}")
                    docker.build("${DOCKER_HUB_REPO}:latest")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_HUB_CREDENTIALS) {
                        docker.image("${DOCKER_HUB_REPO}:${BUILD_NUMBER}").push()
                        docker.image("${DOCKER_HUB_REPO}:latest").push()
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
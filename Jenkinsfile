pipeline {
    agent any

    environment {
        // The image name we want to create
        IMAGE_NAME = "user-service:local"
        // Your Kind cluster name (from 'kind get clusters')
        CLUSTER_NAME = "microservices-lab"
        // The name of the Kind node container
        KIND_NODE = "microservices-lab-control-plane"
    }

    stages {
        stage('Checkout') {
            steps {
                // This clears the old workspace to prevent path issues
                cleanWs()
                // Pulls the latest code from your repo
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                echo "Building Spring Boot JAR..."
                sh "chmod +x mvnw"
                sh "./mvnw clean package -DskipTests"
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker Image for M2/ARM64..."
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Load Image into Kind') {
            steps {
                echo "Piping image into Kind node: ${KIND_NODE}..."
                // The 'Manual Pipe' fix for the containerd snapshotter error
                sh "docker save ${IMAGE_NAME} | docker exec -i ${KIND_NODE} ctr -n k8s.io images import -"
                
                // Verification: List images inside the node to confirm it arrived
                sh "docker exec ${KIND_NODE} crictl images | grep user-service"
            }
        }

        stage('Deploy to K8s') {
            steps {
                echo "Applying Kubernetes Manifest..."
                // Uses the 'k8s-config' credential we created in Jenkins
                withKubeConfig([credentialsId: 'k8s-config']) {
                    // This assumes user-service.yaml is in the ROOT of your repo
                    sh "kubectl apply -f user-service.yaml"
                    
                    // Force a restart so the pod picks up the NEW image immediately
                    sh "kubectl rollout restart deployment user-service"
                    
                    // Check status
                    sh "kubectl get pods"
                }
            }
        }
    }

    post {
        success {
            echo "Successfully deployed User Service to Kind!"
        }
        failure {
            echo "Pipeline failed. Check the logs above for specific errors."
        }
    }
}

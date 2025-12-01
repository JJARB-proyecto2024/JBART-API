pipeline {
    agent any

    tools {
        // Aquí usamos el nombre exacto que pusiste en el Paso 4
        jdk 'jdk-21'
        gradle 'gradle-latest'
    }

    stages {
        stage('Checkout') {
            steps {
                // Descarga el código automáticamente gracias a la config del Paso 5
                checkout scm
            }
        }

        stage('Dar Permisos') {
            steps {
                // En Linux/Docker a veces el gradlew pierde permisos de ejecución
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Test Unitarios') {
            steps {
                // Ejecutamos tu tarea personalizada o el filtro
                sh './gradlew test --tests "com.project.demo.unit_test.*" --no-daemon'
            }
        }

        // Opcional: Integración con SonarQube si quisieras
        /*
        stage('SonarQube') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh './gradlew sonar --no-daemon'
                }
            }
        }
        */
    }

    post {
        always {
            // Recoger los reportes de JUnit para que Jenkins los muestre bonitos
            junit 'build/test-results/**/*.xml'
        }
    }
}
node {
    stage ('clone') {
        checkout scm
    }
    stage('build') {
        sh 'cd back && ./gradlew api:clean api:build && ./gradlew chat:clean chat:build'
    }
}

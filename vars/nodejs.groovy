def call() {

pipeline { 
    agent {
        node {
            label 'workstation'
        }
    }

  options { 
    ansiColor('xterm')
  }




stages {
    
    stage ('Code Quality'){
        steps {
            sh 'sonar-scanner -Dsonar.projectKey=${component}'
        }
    }

    stage ('Unit Test Cases'){
        steps {
            sh "echo Unit tests"
        }
    }

    stage ('CheckMarx SAST Scan'){
        steps {
            sh "echo CheckMarx Scan"
        }
    }

    stage ('CheckMarx SCA Scan'){
        steps {
            sh "echo CheckMarx SCA Scan"
        }
    }


}

post { 
     always {
        cleanWs()
     }
}
}
}
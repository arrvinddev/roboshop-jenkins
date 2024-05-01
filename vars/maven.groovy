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
    

    stage ('Code Compile'){
        steps {
            // sh "mvn compile"
            sh 'echo compile'
        }
    }
    
    stage ('Code Quality'){
        steps {
            // sh 'ls -l'
            // sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.29.145:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
            sh 'echo Code Quality'
        }
    }

    stage ('Unit Test Cases'){
        steps {
            sh 'echo Unit tests'
            // sh "mvn test"
        }
    }

    stage ('CheckMarx SAST Scan'){
        steps {
            sh 'echo CheckMarx Scan'
        }
    }

    stage ('CheckMarx SCA Scan'){
        steps {
            sh 'echo CheckMarx SCA Scan'
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
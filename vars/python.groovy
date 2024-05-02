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

environment {
  NEXUS = credentials('NEXUS')
 }


stages {


    stage ('Code Quality'){
        steps {
            // sh 'ls -l'
            // sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.29.145:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
            sh 'echo Code Quality'
        }
    }

    stage ('Unit Test Cases'){
        steps {
            sh 'echo Unit tests'
            // sh "python3.6 -m unittest"
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

    stage ('Release Application'){
        steps {
            sh 'echo $TAG_NAME >VERSION'
            sh 'zip -r ${component}-${TAG_NAME}.zip *.ini *.py *.txt VERSION ${schema_dir}'
            sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.30.201:8081/repository/${component}/${component}-${TAG_NAME}.zip'
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
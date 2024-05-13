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
            sh 'echo unit testing'
            // sh "npm test"
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
            sh 'zip -r ${component}-${TAG_NAME}.zip *'
            //Deleting this file as it is not needed.
            // sh 'zip -d ${component}-${TAG_NAME}.zip Jenkinsfile'
            // sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.30.201:8081/repository/${component}/${component}-${TAG_NAME}.zip'
            sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 119876270834.dkr.ecr.us-east-1.amazonaws.com'
            sh 'docker build -t 119876270834.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} .'
            sh 'docker push 119876270834.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}'
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
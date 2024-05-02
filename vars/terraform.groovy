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


parameters { 
choice(name: 'env', choices: ['dev', 'prod'], description: 'choose environment to apply')
 }

stages {

    stage('Terraform INIT') {
        steps {
            sh 'terraform init -backend-config=env-${env}/state.tfvars'
        }
    }
    stage('Terraform Apply') {
        steps {
            sh 'terraform apply -auto-approve -var-file=env-${env}/main.tfvars'
        }
    }

    
}

parameters { 
choice(name: 'env', choices: ['dev', 'prod'], description: 'choose environment to destroy')
 }

 stages {

    stage('Terraform INIT') {
        steps {
            sh 'terraform init -backend-config=env-${env}/state.tfvars'
        }
    }
    stage('Terraform Apply') {
        steps {
            sh 'terraform destroy -auto-approve -var-file=env-${env}/main.tfvars'
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
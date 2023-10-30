import subprocess

def sonar_scan(project_key, project_name, sonar_host, sonar_token):
    command = f"mvn -Dmaven.test.failure.ignore=true -Dcheckstyle.skip sonar:sonar \
               -Dsonar.projectKey={project_key} \
               -Dsonar.projectName='{project_name}' \
               -Dsonar.host.url={sonar_host} \
               -Dsonar.token={sonar_token}"
    
    try:
        result = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        return result.stdout.decode('utf-8')
    except subprocess.CalledProcessError as e:
        return e.stderr.decode('utf-8')

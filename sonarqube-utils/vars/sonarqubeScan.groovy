def call(String projectKey, String projectName, String sonarHost, String sonarToken) {
    def sonarScanOutput = new GroovyShell().parse(new File("${JENKINS_HOME}/workspace/your-job-name/src/sonarscan.py")).sonar_scan(projectKey, projectName, sonarHost, sonarToken)
    
    echo "SonarQube Scan Result:\n${sonarScanOutput}"
}

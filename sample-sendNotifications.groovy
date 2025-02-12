#!/usr/bin/env groovy

/**
* Send notifications based on build status string
*/
def call(String buildStatus = 'STARTED') {
  // build status of null means successful
  buildStatus = buildStatus ?: 'SUCCESS'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def attachLog = true
  def summary = "${subject} (${env.BUILD_URL})"
  def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""
    
  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESS') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else if (buildStatus == 'FAILURE') {
    color = 'RED'
    colorCode = '#FF0000'
} else {
              color = 'BLUE'
    colorCode = '#0000FF'
  } 
  // Send notifications

  emailext (
      to: 'in.jenkins@homecredit.co.in',
      subject: subject,
      body: details,
      attachLog: attachLog,
      recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    ) 
}

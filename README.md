# listmaker #

A sample Web application using Google App Engine for Java, Objectify, and Jersey on the server along
with GWT Activities & Places and Resty-GWT on the client.

### LICENSE ###
The sample project is NOT open source. In exchange for payment, Turbomanage Corp. grants you a non-transferable license
 to use and modify the source code in your own Web applications; however, you are not permitted to redistribute any
 part of the source code to others outside your company. Access to this repository is monitored.
 
### Community ###

Use the [issue tracker](https://github.com/turbomanage/listmaker/issues) to report bugs or request enhancements.

### Download the sample ###
 1. ```git clone https://drfibonacci@bitbucket.org/drfibonacci/listmaker.git```
 1. If you don't already have it, [install Maven 3](http://maven.apache.org/download.cgi).

### Run the sample ###
 1. ```mvn clean package appengine:devserver```
 2. Browse to http://localhost:8080/

### Run the sample in GWT dev mode ###
 1. ```mvn gwt:debug```
 1. Wait for the message "Listening for transport dt_socket at address: 8000" 
 1. Configure a Java remote debugger in your IDE on port 8000 and launch it to attach.
 1. The standalone GWT console will appear and you can launch the browser from there.
 
 Alternatively, you can run GWT dev mode directly with Google Plugin
 for Eclipse or IntelliJ full edition (see further instructions).
 
### Setup with IntelliJ ###
 1. Import the project into IntelliJ as a Maven project.
 1. If you have IntelliJ full edition, you can run GWT dev mode in the IDE as usual.
 
### Setup with Eclipse ###
 1. Install the [Google Plugin for Eclipse](https://developers.google.com/eclipse/docs/download).
 1. Import the project into Eclipse as an existing Maven project.

### Optional ###
The sample includes G+ and Facebook login integration. The buttons are found in src/main/webapp/index.html. Simply
look for the TODOs where you need to replace the CLIENT_IDs and secrets with those found in the respective developer
consoles.
   
### Upload to App Engine ###
Before uploading to App Engine, replace the application ID and version in src/main/webapp/WEB-INF/appengine-web.xml.
To obfuscate the GWT code, making it smaller, build with the maven "release" profile.

Example:
```mvn -P release clean package appengine:update```

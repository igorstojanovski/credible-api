# credible-api
Credible API

jdbc:h2:mem:testdb

In the release folder you can find the deliverable for the current branch.
In order to run it you need Java 11. and you run it by executing:

java -jar api-0.0.1-SNAPSHOT.jar

This will start the app on port 8384. Started in this way the app will use an internal, in memory database.
After the app is stopped all the data are deleted. 

A single test user is create when starting the app. More info about it can be found in the StartupConfiguration class.



After starting the app all API endpoints can be then accessed ata http://loclahost:8384
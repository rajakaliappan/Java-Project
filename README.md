# WebCrawler-v2
This project contains Web crawler which will search for word for given URL(s) and stores the result in Output File
Configuration needs to be updated in properties file for below

List of URL(s)
Text to search
Stores the search result in Output File

Dependency need to be setup as per below. Ensure below artifacts are available in same location

jsoup-1.13.1.jar needs to be in lib folder
WebCrawler-0.0.1-SNAPSHOT.jar has to be in same location as lib folder
Copy config.properties and update properties as per local environment. Sample of property file can be referred from the project.
Have output.txt file created in the same location as per config.properties

Execution:
java -jar WebCrawler-0.0.1-SNAPSHOT.jar
The result of execution will be stored in output.txt file.

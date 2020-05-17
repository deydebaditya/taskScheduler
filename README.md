SETUP GUIDE:
1. Clone the project.
2. This project is a gradle project, open it using any IDE of your choice and import the project using the default Gradle Wrapper setting.
3. As this project is a Spring Boot microservice, setting up of a server is not required.


1. The project is developed using JAVA(mininum JDK 8), Spring Boot and the build tool used is Gradle. Please extract the zip to get the whole project source code.
2. The project use API's for inputs, which are described in the attached API usage guide.
3. The project architecture is built to persist data too, but due to lack of time, and urgent medical issues at home, it has not been implemented, but would be a quick work, with a little time in hand.
4. All the queries are made working.
5. The scheduling APIs are currently made to schedule tasks without a descriptive response, but the architecture is in place. This would again be a quick work with a little time.
6. All the use cases have been solved.
7. The JAR can be run directly using java -jar <filename> to start ip the server.
8. The default server port is 8085, which can be changes in src/main/resources/application.properties by changing the server.port property.
9. The default log location is C:\\TaskScheduler\\Logs\\ which can be changed in  src/main/resources/application.properties by changing the logging.path property. 


API Usage 
1. To schedule tasks: a. API: <URL>:<PORT>/task/schedule b. HTTP Verb: POST c. Payload type: application/json d. Sample Payload: 
{ 
 "taskRequests":[ 
  { 
   "taskName": "testRecAB", 
   "taskDuration": 10, 
   "taskSchedule": "51 21 SUN", 
   "taskType": "B", 
   "taskPriority": "LOW" 
  }, 
  { 
   "taskName": "testRecD", 
   "taskDuration": 10, 
   "taskTriggerTime": "15 Mar 2020 21:49:15", 
   "taskType": "A", 
   "taskPriority": "HIGH" 
  }, 
  { 
   "taskName": "testRecB", 
   "taskDuration": 10, 
   "taskSchedule": "* * *", 
   "taskType": "B", 
   "taskPriority": "LOW" 
  }, 
  { 
   "taskName": "testRecC", 
   "taskDuration": 10, 
   "taskSchedule": "* * *", 
   "taskType": "B", 
   "taskPriority": "HIGH" 
  } 
 ] 
} 
2. To set status of a task: a. API: <URL>:<PORT>/task/setStatus b. HTTP Verb: POST c. Payload type: application/json d. Sample Payload: 
{ 
 "taskRequests":[ 
  { 
   "taskName": "testRecB", 
   "taskType": "B", 
   "taskStatus": "INACTIVE" 
  } 
 ] 
} 
3. Get active task info: a. API: <URL>:<PORT>/task/info/activeTasks b. HTTP Verb: GET c. Optional query parameter: taskName d. Sample Payloads: i. localhost:8085/task/info/activeTasks ii. localhost:8085/task/info/activeTasks?taskName=testA e. If taskName is not specified, all the current active tasks would be returned. f. If taskName is specified, the task with that particular name, if active, will be returned with its details. 
 
 
4. Get inactive task info: a. API: <URL>:<PORT>/task/info/inactiveTasks b. HTTP Verb: GET c. Query parameters and behavior same as activeTask API. 
 
5. Get completed task info: a. API: <URL>:<PORT>task/info/completedTasks b. HTTP Verb: GET c. Query parameters and behavior same as activeTask API. 
 
6. Get task execution status between timestamps: a. API: <URL>:<PORT>/task/info/executionStatus b. HTTP Verb: GET c. Query Parameters i. t1 -> start time ii. t2 -> end time d. Sample payloads: i. localhost:8085/task/info/executionStatus?t1=15 Mar 2020 21:02:25&t2=15 Mar 2020 21:32:45

# Grabber-jobs

 1. This application for parsing web: mySql.ru
 
 It has responsobilty for parsing forum of job by java vacation
 Application makes parsing of web by java vacation and loades it to DataBase.
 It will update from the last day when we received new Item for job.

 
 2. System requrements
 
    JDK 8.0
    
    PostgresSQl 9.5.12
    
    Linux Ubuntu 10/Windiwos 10
    
 3.
 
This Application use file properties:

By default:

jdbc.url = jdbc:postgresql://localhost:5432
jdbc.nameTable = mytask
jdbc.username = postgres
jdbc.password = admin
jdbc.weblink = http://www.sql.ru/forum/job
jdbc.time = 3350
jdbc.lastDate =0015-00-00 00:00:00

To start application: java -jar <way folder>/grabber.jar
 
Result you may see on web:
https://localhost:9000/?msg=allPosts - all data which was include to DB
https://localhost:9000/?msg=lastDate - max date update which was include to DB
  
 # stack technology
 Java 8 SE, JDBC, PostrgeSQL 9.1,JSOUP ,Quartz,Properties,REST API, Lambok
 
  # to do list
  - add HH parser
  - add job parser
  - add args (depends of parser)
  - add possobility to work with parallel stream of parser
 
 #Vladimir Makarov
 
    


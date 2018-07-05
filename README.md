# mySQLRuParsing

 1. This application for parsing web: mySql.ru
 
 It has responsobilty for parsing forum of job by java vacation
 Application makes parsing of web by java vacation and loades it to DataBase.
 It will update from the last day when we received new Item for job.
 
 Class ParsingHTML responsible for parsing by word "Java"
 Class QuartzJob responsible for Time work
 
 2. System requrements
 
    JDK 8.0
    
    PostgresSQl 9.5.12
    
    Linux Ubuntu 10
    
 3. This Application can be start by two way:
 Before start work first step to updae app.properties.It locates in folder resurces.
 Sample:
 
jdbc.url = jdbc:postgresql://localhost:5432 - indicate your data

jdbc.nameTable = postgres - indicate your value

jdbc.username = postgres - indicate your value

jdbc.password = admin - indicate your value

jdbc.weblink = http://www.sql.ru/forum/job - your shouldn't update it

jdbc.corExp = 0 0 12 * * ? - here you can choose time lips

jdbc.lastDate = 0015-00-00 00:00:00  -  start point date for parsing
 
 - if you work in IDEA it will be enough to run StartApplication.java
 - if you want to create jar file. For this point your should choose class StartApplication as main during compilation.
 
 To start with jar file. Jar file and app.properties should be in one folder.
 app.properties include propertise for application .
 Sample of command in terminal:
 
 java -jar SQLRuParser app.properties
 
 4. THank you
 
 #Vladimir Makarov
 
    


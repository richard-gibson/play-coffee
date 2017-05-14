# play-coffee
# Session 1
## SBT Installation instructions

SBT is the "Scala Build Tool".
We will use it to compile & run our code.

- You need a working installation of Java 8 (OpenJDK or Oracle JDK).

### Windows

Go to the [SBT download page](http://www.scala-sbt.org/download.html) and install it from there.

### Linux/macOS

Run the following commands:

```
$ ./setup
$ ./sbt
```

After downloading the entire Internet, this should load up the SBT shell.


## Runnign Play Scala Seed

To create a new Play project use:

```./sbt new playframework/play-scala-seed.g8```

Enter the following when prompted
```
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Set current project to play-coffee (in build file:/.../play-coffee/)

This template generates a Play Scala project

name [play-scala-seed]: coffee-app
organization [com.example]: com.coffeeapp
scala_version [2.11.11]:
play_version [2.5.14]:
scalatestplusplay_version [2.0.0]:

Template applied in ./coffee-app

```


Move Play project files to current directory
```
$ ( mv coffee-app/* . && mv coffee-app/.gitignore . &&  rm -rf coffee-app && rm -rf *gradle*)
```

After that,  
```./sbt``` or ```sbt``` then  ```run```

and go to http://localhost:9000 to see the running server.

use CTRL/CMD+D to return to SBT console

other useful sbt tasks

| task      	| Usage                                 	                |
|------------	|-------------------------------------------------------	|
| clean      	| clean generated files                                 	|
| compile    	| compile application                                   	|
| test       	| run specs/scalatest/junit tests                       	|
| run <port> 	| run play application in development mode              	|
| console    	| launch Scala REPL with access to application packages 	|
| reload     	| reload sbt settings                                   	|
| dist       	| package application                                   	|


# Session 2
## DB setup

In sbt run
```
flyway/flywayMigrate
compile
```

##Exercise: Coffee App REST API
Use the following classes to expose HTTP Endpoints to enable users to query Coffee Apps data
modules/api/src/main/scala/com/coffeeapp/repo/BusinessRepository.scala
modules/api/src/main/scala/com/coffeeapp/repo/OutletRepository.scala

Endpoints to expose
```
GET     /api/outlets                        List all outlets
GET     /api/outlets/location/:location     List all outlets matching location (town)
GET     /api/outlets/postcode/:postcode     List all outlets matching postcode

GET     /api/business                   List all businesses with their related outlets
GET     /api/business/:id               List a business with related outlets by a given ID
```


# Session 3
##Exercise: Admin panel
Add actions to the CoffeeBusinessController that will enable admin users to view and delete businesses

Routes to implement
GET     /coffee-admin                   controllers.CoffeeBusinessController.listBusinesses
GET     /business/delete/:id            controllers.CoffeeBusinessController.deleteBusinessById(id:Int)

The coffee-admin route should only display info from the Coffee_business table
Add a link beside each coffee business enabling the user to delete the business
(note a business can only be deleted if it doesn't have any related Outlets)

If you are feeling adventurous:
Implement another route to list a business with all related outlets using the route below
GET     /business/read/:id              controllers.CoffeeBusinessController.listBusinessById(id:Int)

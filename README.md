# play-coffee

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

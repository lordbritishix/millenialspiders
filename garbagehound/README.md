# Instructions

## Setup
* `gcloud init`

## Maven
### Running locally
    $ mvn clean jetty:run-exploded
  
### Deploying new instance (requires gcloud setup)
    $ mvn appengine:deploy
    
## Debugging
export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"

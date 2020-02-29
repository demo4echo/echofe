# echofe
_A module representing a frontend µs (e.g. one that is exposed to the world) :octocat:_

## TL;DR:
The module is a simple echo server (written in **Java**) that communicates with the [echobe](https://github.com/demo4echo/echofe) via a gRPC protocol. It is built via **Gradle**, as all the modules in the project. It has the [EchoCommon](https://github.com/demo4echo/EchoCommon) configured as its sub-module. It includes source files to build an applicable **Helm** chart.

### Principal Items:
**EnvFile.properties** - branch specific configuration file. All its properties are converted to environment variables within the Jenkins Pipeline execution. This file will not be merged during merge between branches (following a proper setup in the **.gitattributes** file)

@echo off

REM Minikube formatr (needs to add some manipulation on the path:
REM If the path to mounted (local) is => C:\Users\tmeltse.COMVERSE\OneDrive - Mavenir Ltd\GitClones\GitHub\eclipse
REM Then the manipulated path would be => /c/Users/tmeltse.COMVERSE/OneDrive - Mavenir Ltd/GitClones/GitHub/eclipse (add "/" in the beginning ; replace "C" with "c" ; remove ":" ; "replace "\" with "/")
REM So the updated command would be => docker run -it --rm -v "/c/Users/tmeltse.COMVERSE/OneDrive - Mavenir Ltd/GitClones/GitHub/eclipse":$HOME/repo alpine ash

REM Docker Desktop format (no manipulation is needed), but the C drive needs to be shared once through the Docker Desktop GUI!
docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock -v %userprofile%\.gradle:/$HOME/.gradle -v %userprofile%\.helm:/$HOME/.helm -v "%cd%":$HOME/repo -w $HOME/repo demo4echo/alpine_openjdk8_k8scdk:latest ash

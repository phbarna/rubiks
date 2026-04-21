#!/bin/bash

# Run from the project root directory

# Rebuild JAR and publish a uniquely named copy to the Samba share (removes previous versions)
dir="${PWD##*/}"

rm $HOME/share/$dir*jar 2>/dev/null;mvn clean package -Dmaven.test.skip=true && cp target/$dir-*.jar $HOME/share/$dir-$(uuid).jar;

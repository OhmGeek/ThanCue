#!/bin/bash

vname=$1
release=$2

file=src/ThanCue/Main.java
sed -i 's/\(VERSION_NAME = "\).*/VERSION_NAME = "'$1'";/g' $file

file=src/ThanCue/app-info.properties
sed -i 's/version = .*/version = '$vname'/g' $file
sed -i 's/release = .*/release = '$release'/g' $file

file=src/ThanCue/updates.xml
sed -i 's/id=".*/id="'$release'"/g' $file
sed -i 's/version=".*/version="'$vname'"/g' $file
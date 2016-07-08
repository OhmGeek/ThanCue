#!/bin/bash

vname=$1
release=$2

file=src/ThanCue/Variables/Constants.java
sed -i 's/\(VERSION_NAME = "\).*/VERSION_NAME = "'$vname'";/g' $file
sed -i 's/\(RELEASE_ID = \).*/RELEASE_ID = '$release';/g' $file

file=src/ThanCue/app-info.properties
sed -i 's/version = .*/version = '$vname'/g' $file
sed -i 's/release = .*/release = '$release'/g' $file

file=src/ThanCue/updates.xml
sed -i 's/id=".*/id="'$release'"/g' $file
sed -i 's/version=".*/version="'$vname'"/g' $file

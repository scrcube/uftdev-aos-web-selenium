#!/bin/bash

if [ -z "$1" ]
then
    echo "Usage:"
    echo "    getDriver.sh driverVersionNumber"
    exit 1
fi

VER=$1

echo "Getting chromedriver for:"
echo "#########################"
echo "     "$VER
echo "#########################"
sudo mkdir $VER
cd $VER
sudo wget -N http://chromedriver.storage.googleapis.com/$VER/chromedriver_linux64.zip -P .

sudo unzip chromedriver_linux64.zip

sudo chmod 755 chromedriver

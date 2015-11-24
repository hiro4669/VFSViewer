#!/bin/sh
# vfs.MainView diskpath
# e.g., vfs.MainView rp06.disk 

echo $1
java -cp ./classes/:./extractor.jar vfs.MainView $1 

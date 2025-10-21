#!/bin/bash

MOCKDATA_DIR="mockdata"

for file in "$MOCKDATA_DIR"/*
do
  if [ -f "$file" ]; then
    echo "Uploading $file"
    curl -X POST -F "file=@$file" http://localhost:8081/resources/upload
    echo "\n"
  fi
done


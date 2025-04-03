#!/bin/bash

openssl genrsa -out ./src/main/resources/keys/accessKeypair.pem 2048
openssl rsa -in ./src/main/resources/keys/accessKeypair.pem -pubout -out ./src/main/resources/keys/accessPublic.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in ./src/main/resources/keys/accessKeypair.pem -out ./src/main/resources/keys/accessPrivate.pem

openssl genrsa -out ./src/main/resources/keys/refreshKeypair.pem 2048
openssl rsa -in ./src/main/resources/keys/refreshKeypair.pem -pubout -out ./src/main/resources/keys/refreshPublic.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in ./src/main/resources/keys/refreshKeypair.pem -out ./src/main/resources/keys/refreshPrivate.pem
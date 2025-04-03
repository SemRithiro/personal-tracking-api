#!/bin/bash

mkdir keys
openssl genrsa -out ./keys/accessKeypair.pem 2048
openssl rsa -in ./keys/accessKeypair.pem -pubout -out ./keys/accessPublic.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in ./keys/accessKeypair.pem -out ./keys/accessPrivate.pem

openssl genrsa -out ./keys/refreshKeypair.pem 2048
openssl rsa -in ./keys/refreshKeypair.pem -pubout -out ./keys/refreshPublic.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in ./keys/refreshKeypair.pem -out ./keys/refreshPrivate.pem
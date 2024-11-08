#!/bin/bash

# Create a truststore from the PEM certificate
keytool -import \
  -alias aivenroot \
  -file /app/certs/ca.pem \
  -keystore /app/certs/truststore.jks \
  -storepass truststore \
  -noprompt
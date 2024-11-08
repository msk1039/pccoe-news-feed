#!/bin/bash

# Download the CA certificate from Aiven
#curl -o /app/certs/ca.pem "${CA_CERT_URL}"

# Start the application with the CA certificate
java -Djavax.net.ssl.trustStore=/app/certs/ca.pem \
     -Djavax.net.ssl.trustStoreType=PEM \
     -jar app.jar
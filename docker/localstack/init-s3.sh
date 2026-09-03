#!/bin/sh
# Se ejecuta automaticamente al arrancar LocalStack (ver /etc/localstack/init/ready.d).
# Crea el bucket que usa el backend para almacenar imagenes de productos/categorias.
set -e

BUCKET_NAME="${EASY_STORE_AWS_S3_EASY_STORE:-easy-store-bucket}"

echo "Creando bucket '${BUCKET_NAME}' en LocalStack..."
awslocal s3api create-bucket --bucket "${BUCKET_NAME}" || true
awslocal s3api put-bucket-cors --bucket "${BUCKET_NAME}" --cors-configuration '{
  "CORSRules": [
    {
      "AllowedOrigins": ["*"],
      "AllowedMethods": ["GET", "PUT", "POST", "DELETE", "HEAD"],
      "AllowedHeaders": ["*"]
    }
  ]
}' || true

echo "Bucket '${BUCKET_NAME}' listo."

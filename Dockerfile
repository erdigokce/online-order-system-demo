FROM openjdk:17.0.1-slim-buster

ARG BULD_DIR=target
COPY ${BULD_DIR}/classes /app

EXPOSE 443

ENTRYPOINT ["java","-cp","app:app/lib/*","OosSellerApplication"]
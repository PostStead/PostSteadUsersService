# PostStead - Cloud Computing project
Sandu Alexandru-Cristian
Alexiu Adrian Stefan

## Docker compose

Create jar file to be used by the Dockerfile
```shell
mvn clean install package
```

Docker compose file for rabbitmq
```shell
docker compose -f docker-compose-rabbitmq.yml up
```
# cashmanager_2022_5

## Docker

To get Docker: https://docs.docker.com/get-docker/

In order to PostgreSQL container Docker to work, stop your local PostgreSQL service.

Containers name:
- cashmanager_db (*PostgreSQL*)
- cashmanager_api (*backend*)
- cashmanager_apk (*frontend*)

To build containers Docker:
```shell
$ docker-compose build
```

To run containers Docker (use *-d* flag to run them in background):
```shell
$ docker-compose up
```

To run a command in a running container:
```shell
$ docker exec -ti [container_name] bash
```

To exit a container:
```shell
root@[container_id]:~# exit
```

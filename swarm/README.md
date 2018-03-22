useful commands:
docker swarm init
docker node ls
docker node inspect <NODEID> --pretty

htpasswd -nb -B admin <password> | cut -d ":" -f 2

echo "postgres" | docker secret create pg_password -
echo "postgres" | docker secret create pg_user -
echo "batch" | docker secret create pg_db -



docker stack deploy -c test-compose.yml batch

docker service create --name portainer     --publish 9000:9000     --constraint 'node.role == manager'     --mount type=bind,src=//var/run/docker.sock,dst=/var/run/docker.sock     portainer/portainer     -H unix:///var/run/docker.sock --admin-password '$2y$05$RtXe/m2XhZKjXpyCATXY7eUlVsuSsqarCDJd1eI41AT0GG6wJzAWi'
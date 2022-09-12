echo "Iniciando despliegue"
#git pull

CID=$(docker ps -aqf "name=report-server")
echo $CID

echo "Detendiendo contenedor"
docker stop $CID

echo "Borrando contenedor"
docker rm $CID

echo "Borrando imagen"
docker rmi report-server:v1

echo "Creando jar de spring"
mvn clean package -DskipTests -Dmaven.test.skip=true

docker build -t report-server:v1 .
docker run -p 8082:8082 --name report-server -d report-server:v1

echo "Terminando despliegue"
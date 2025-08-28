echo"Starting deploy"

echo"Download changes"
#git pull origin dev

echo"Stop containers"
docker-compose down

echo"Delete images"
docker rmi persist-reports-app

echo"Create new images"
docker-compose build

echo"Start containers"
docker-compose up -d
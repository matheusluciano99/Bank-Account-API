DOCKERHUB_USERNAME="matheusluciano99"
IMAGE_NAME="conta-bancaria-api"
TAG="latest"

echo "Building Docker image..."
docker build -t $DOCKERHUB_USERNAME/$IMAGE_NAME:$TAG .

echo "Pushing to Docker Hub..."
docker push $DOCKERHUB_USERNAME/$IMAGE_NAME:$TAG

echo "Done! Image available at: $DOCKERHUB_USERNAME/$IMAGE_NAME:$TAG"

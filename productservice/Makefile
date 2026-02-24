build:
	mvnw clean package -DskipTests

build-container:
	docker build -t product-service:1.0 .

run-container:
	docker run -p 8082:8081 product-service:1.0

kub:
	kubectl apply -f k8s/configmap.yaml
	kubectl apply -f k8s/deployment.yaml
	kubectl apply -f k8s/service.yaml
	kubectl apply -f k8s/secret.yaml
	kubectl apply -f k8s/namespace.yaml

clean:
	kubectl delete -f k8s/service.yaml
	kubectl delete -f k8s/deployment.yaml
	kubectl delete -f k8s/configmap.yaml
	kubectl delete -f k8s/secret.yaml

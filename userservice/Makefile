build:
	mvnw clean package -DskipTests

build-container:
	docker build -t app-k8s-local:1.0 .

run-container:
	docker run -p 8080:8080 app-k8s-local:1.0

kub1:
	kubectl apply -f k8s/configmap.yaml
	kubectl apply -f k8s/deployment.yaml
	kubectl apply -f k8s/service.yaml
	kubectl apply -f k8s/secret.yaml

clean:
	kubectl delete -f k8s/service.yaml
	kubectl delete -f k8s/deployment.yaml
	kubectl delete -f k8s/configmap.yaml
	kubectl delete -f k8s/secret.yaml

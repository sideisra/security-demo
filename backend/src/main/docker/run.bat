docker stop security-demo
docker rm security-demo
docker run --name security-demo --network security-demo -p 8443:8443 securitydemo:latest

# message_routing_app

Prerequisites: Docker and Docker Compose

Step 1: Clone the Repository
bash
git clone https://github.com/hnnfr/message_routing_app.git
cd cd .\message_routing_app\

Step 2: Launch Services with Docker Compose
bash
docker-compose up
(or 'docker-compose up -d' for detached mode)

This will start the MQ Series, PostgreSQL database, the Spring Boot backend application, and Angular frontend application in 4 different containers, in the same network.
You will have to wait for all containers to be up and running. Check with: 
docker ps

You should see something like: 
CONTAINER ID   IMAGE                          COMMAND                  CREATED              STATUS                        PORTS                                                                NAMES
c84a7aab3265   message_routing_app-frontend   "docker-entrypoint.s…"   About a minute ago   Up About a minute             0.0.0.0:4200->4200/tcp                                               frontend
300b830be61b   message_routing_app-backend    "java -jar app.jar"      About a minute ago   Up About a minute             0.0.0.0:8080->8080/tcp                                               backend
d1163c379442   icr.io/ibm-messaging/mq        "runmqdevserver"         About a minute ago   Up About a minute (healthy)   9157/tcp, 0.0.0.0:1414->1414/tcp, 0.0.0.0:9443->9443/tcp, 9415/tcp   ibm-mq
ccd0b9e5113e   postgres:15-alpine             "docker-entrypoint.s…"   About a minute ago   Up About a minute (healthy)   0.0.0.0:5432->5432/tcp                                               postgres

Step 3: Interacting with the application
You have several ways to interact with the application. 
First, you can connect to the MQ Console so that you can produce test messages
https://localhost:9443/

 + use the credentials: admin / passw0rd
 + Note: it is HTTPS, not HTTP. The browser will show the warning about security issue => accepting the risk and advance

Then, you can test the backend application using Postman (or Insomnia or the like)
 + getAllMessages: GET http://localhost:8080/api/messages
 + getMessageById: GET http://localhost:8080/api/messages/1
 + count: GET http://localhost:8080/api/messages/count
 + searchByContent: GET http://localhost:8080/api/messages/search?content=test
 + getAllPartners: GET http://localhost:8080/api/partners
 + searchPartnersByAlias: GET http://localhost:8080/api/partners/search?alias=1
 + createPartner: POST http://localhost:8080/api/partners,
with body JSON:
{
"alias": "P201",
"type": "confidential partner",
"direction": "INBOUND",
"application": "new_app_200",
"processedFlowType": "ALERTING",
"description": "This is a test partner too"
}
 + getPartnerById: GET http://localhost:8080/api/partners/2

Finally, to test the application end-to-end, you connect to the frontend interface using a browser: http://localhost:4200/

Step 4: Test Key Functions on IHM
Messages:
 + Access the message list from the main menu.
 + Issue a new message from MQ Console, and reload the IHM => you should see the new message in the list
 + Click on a message to see its details in a pop-up.

Partners:
 + Access the partner list from the menu.
 + Add a new partner via the form.
 + Edit a partner
 + Delete an existing partner.

Step 5: Close the application 
bash
docker-compose down

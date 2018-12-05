# Distributed Algorithms & Systems (DAS4)
## SIT4024-1
### Group 14 - Testing Instructions

1. Extract all files from GROUP14.tar
2. Get the local I.P. address of the designated server by looking the results (en0: inet) of:

	```bash
	ifconfig
	```
3. For all client machines: **(The designated server may omit this step)**
   
   Modify **Client.java** on Line Number **159**:
	
	```java
	String url = "DESIGNATED_SERVER_IP_ADDRESS";
	```
	Replace DESIGNATED__SERVER\__IP_ADDRESS with the I.P. address of the designated server as retrieved from Step 2


4. Navigate to the das4 directory with your favourite shell/terminal/console

5. Compile all the Java files

	```bash
	javac *.java
	```

6. Compile using the RMI Compiler (RMIC) to generate the necessary stub and skeleton class files

	```bash
	rmic Client
	```

	```bash
	rmic Server
	```

7. Start a remote object registry on the specified port (defaults on port 1099) on the current host

	```bash
	rmiregistry
	```

8. Start the Server on localhost

	```bash
	java Server
	```


9. Start the client **(The designated server may omit this step)**

	```bash
	java Client
	```

10. Follow the instructions on client machines to enter a username to download the file

11. To simulate a server downtime, stop the server using ```Ctrl-C``` on the designated server's terminal once a client has finished downloading the file 

	In order to achieve the desired outcome, allow the first client to download at least 50% of the file before running subsequent clients

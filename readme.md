# Distributed Algorithms and Systems 4
## SIT4024-1 Coursework

### For the designated server,
> Modify **Client.java**, Line Number **164**:
> 
> ```java
> String url = "DESIGNATED_SERVER_IP_ADDRESS";
> ```
> 
> Replace DESIGNATED_SERVER_IP_ADDRESS with the exact I.P. address of the designated server.

### Instructions to start all clients
0. Navigate to the das4 directory with your favourite shell/terminal/console.

1. Compile all the Java files

	```bash
	javac *.java
	```

2. Compile using the RMI Compiler (RMIC) to generate the necessary stub and skeleton class files

	```bash
	rmic Client
	```

	```bash
	rmic Server
	```

3. Start a remote object registry on the specified port (defaults on port 1099) on the current host.

	```bash
	rmiregistry
	```

4. Start the Server on localhost

	```bash
	java Server
	```

5. Start the client

	```bash
	java Client
	```

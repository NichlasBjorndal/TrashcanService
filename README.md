# TrashcanService
The repo for our Web Services class

## Development
### Prerequisites
The tokyo keys in a directory from where the [Connecting](#Connecting)


### Connecting
#### 4gb Server
```shell
ssh -i tokyo root@tokyo4.compute.dtu.dk
```

#### 2gb Server
```shell
ssh -i tokyo root@tokyo2.compute.dtu.dk
```

### Running
Jenkins will run on the 2gb Server and Wildfly on the 4gb

#### Jenkins


#### Wildfly
Connect to the 4 gb server as described in [4gb Server](#4gb-Server)

Change account to the wildfly account with password wildfly:
```shell
su - wildfly
```

Change directory to the bin folder of the Wildfly installation and run standalone.sh:
```shell
cd wildfly-11.0.0.Final/bin && ./standalone.sh
```

There will now be a running wildfly connection at [http://159.89.18.108:9990](http://159.89.18.108:9990)

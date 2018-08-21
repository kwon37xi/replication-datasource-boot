# Replication DataSource for Spring Boot 2

You can see the basic idea of replication routing datasource in [Java (Spring & Non Spring) replication-datasource project](https://github.com/kwon37xi/replication-datasource).

This is an example of Spring Boot 2 with `LazyConnectionDataSourceProxy` + `AbstractRoutingDataSource`.


## Configuration for Spring boot 2
Refer to the following 3 source codes.

* [ReplicationRoutingDataSource.java](https://github.com/kwon37xi/replication-datasource-boot/blob/master/src/main/java/kr/pe/kwonnam/boot/bootreplicationdatasource/routingdatasource/ReplicationRoutingDataSource.java) : routing implementation of `AbstractRoutingDataSource`
* [ReplicationDataSourceConfig](https://github.com/kwon37xi/replication-datasource-boot/blob/master/src/main/java/kr/pe/kwonnam/boot/bootreplicationdatasource/ReplicationDataSourceConfig.java) : Core configuration example.
  * `@Primary` and `@DependsOn` are required for Spring Boot.
* [BoorReplicationDatasourceApplication](https://github.com/kwon37xi/replication-datasource-boot/blob/master/src/main/java/kr/pe/kwonnam/boot/bootreplicationdatasource/BootReplicationDatasourceApplication.java) : Run this boot application, you will see the replicated query results.

## How to run
Run the following shell command.

```
./gradlew bootRun
```

then, you will see the results like the following
```
# with @Transactional(readOnly = true) method
▶ INFO ### findByIdRead : read_1

# with @Transactional(readOnly = false) method
▶ INFO ### findByIdWrite : write_1
```

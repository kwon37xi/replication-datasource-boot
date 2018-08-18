package kr.pe.kwonnam.boot.bootreplicationdatasource;

import kr.pe.kwonnam.boot.bootreplicationdatasource.routingdatasource.ReplicationRoutingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Replication DataSources Configuration
 */
@Configuration
public class ReplicationDataSourceConfig {
    /**
     * Main DataSource
     * <p>
     * Application must use this dataSource.
     */
    @Primary
    @Bean
    // @DependsOn required!! thanks to Michel Decima
    @DependsOn({"writeDataSource", "readDataSource", "routingDataSource"})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    /**
     * {@link org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource}는
     * {@link org.springframework.beans.factory.InitializingBean}을 구현하므로,
     * 명시적으로 afterPropertiesSet()메소드를 호출하거나
     * 별도 @Bean으로 만들어 Spring Life Cycle을 타도록 해야 한다.
     */
    @Bean
    public DataSource routingDataSource() {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writeDataSource());
        dataSourceMap.put("read", readDataSource());
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());

        return routingDataSource;
    }

    @Bean(destroyMethod = "shutdown")
    public DataSource writeDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
                .setName("lazyWriteDb")
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:/writedb.sql");
        return builder.build();
    }

    @Bean(destroyMethod = "shutdown")
    public DataSource readDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
                .setName("lazyReadDb")
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:/readdb.sql");
        return builder.build();
    }
}

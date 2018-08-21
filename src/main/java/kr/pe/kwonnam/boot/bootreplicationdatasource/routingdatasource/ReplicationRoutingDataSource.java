package kr.pe.kwonnam.boot.bootreplicationdatasource.routingdatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * ReplicationRoutingDataSource routes connections by <code>@Transaction(readOnly=true|false)</code>
 */
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
    private Logger log = LoggerFactory.getLogger(ReplicationRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "read" : "write";
        log.info("current dataSourceType : {}", dataSourceType);
        return dataSourceType;
    }
}

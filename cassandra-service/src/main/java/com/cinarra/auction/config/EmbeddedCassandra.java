package com.cinarra.auction.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.CQLDataSet;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

import static org.cassandraunit.utils.EmbeddedCassandraServerHelper.DEFAULT_STARTUP_TIMEOUT;

public class EmbeddedCassandra {
    private final String host;
    private final int port;
    private CQLDataSet dataSet;
    private Session session;

    public EmbeddedCassandra(String host, int port, String dataSetLocation, String keyspaceName) {
        this.host = host;
        this.port = port;
        dataSet = new ClassPathCQLDataSet(dataSetLocation, keyspaceName);
    }

    public void start() throws Exception {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(DEFAULT_STARTUP_TIMEOUT);
        Cluster cluster = new Cluster.Builder().addContactPoints(host).withPort(port).build();
        session = cluster.connect();
        CQLDataLoader dataLoader = new CQLDataLoader(session);
        dataLoader.load(dataSet);
        session = dataLoader.getSession();
    }

    public Session getSession() {
        return session;
    }
}

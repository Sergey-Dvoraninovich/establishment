package com.dvoraninovich.establishment.model.pool;

import com.dvoraninovich.establishment.exception.DatabaseException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseConnectionPool {
    private static final Logger logger = LogManager.getLogger(DatabaseConnectionPool.class);
    private static AtomicReference<DatabaseConnectionPool> instance = new AtomicReference<>(null);
    private static final AtomicBoolean instanceInitialized = new AtomicBoolean(false);

    private static final String DB_PROPERTIES_NAME = "pool.properties";
    private static final String MIN_POOL_SIZE_PROPERTY = "poolMinSize";
    private static final String MAX_POOL_SIZE_PROPERTY = "poolMaxSize";

    private int poolMinSize;
    private int poolMaxSize;

    private BlockingDeque<Connection> availableConnections;
    private Deque<Pair<Connection, Instant>> busyConnections;

    private DatabaseConnectionPool() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(DB_PROPERTIES_NAME)) {
            Properties poolProperties = new Properties();
            poolProperties.load(inputStream);

            String poolMinSizePropertyValue = poolProperties.getProperty(MIN_POOL_SIZE_PROPERTY);
            String poolMaxSizePropertyValue = poolProperties.getProperty(MAX_POOL_SIZE_PROPERTY);
            poolMinSize = Integer.parseInt(poolMinSizePropertyValue);
            poolMaxSize = Integer.parseInt(poolMaxSizePropertyValue);

            if (poolMinSize > poolMaxSize) {
                logger.fatal("Min size of pool is greater than max size");
                throw new RuntimeException("Min size of pool is greater than max size ");
            }

            availableConnections = new LinkedBlockingDeque<>(poolMaxSize);
            busyConnections = new LinkedBlockingDeque<>(poolMaxSize);

            for (int i = 0; i < poolMinSize; i++) {
                Connection connection = ConnectionFactory.createConnection();
                availableConnections.put(connection);
            }
        } catch (IOException e) {
            logger.fatal("Unable to read database properties", e);
            throw new RuntimeException("Unable to read database properties", e);
        } catch (NumberFormatException e) {
            logger.fatal("Unable to configure vital parameters of connection pool", e);
            throw new RuntimeException("Unable to configure size of connection pool", e);
        } catch (InterruptedException e) {
            logger.error("Caught an exception", e);
            Thread.currentThread().interrupt();
        } catch (DatabaseException e) {
            logger.error("Caught an error trying to establish connection", e);
        }

        if (availableConnections.size() < poolMinSize) {
            logger.fatal("Unable to create pool due to lack of connections. Required "
                    + poolMinSize + " but got "
                    + availableConnections.size());
            throw new RuntimeException("Unable to create pool due to lack of connections. Required "
                    + poolMinSize + " but got "
                    + availableConnections.size());
        }

    }

    public static DatabaseConnectionPool getInstance() {
        while (instance.get() == null) {
            if (instanceInitialized.compareAndSet(false, true)) {
                instance.set(new DatabaseConnectionPool());
            }
        }
        return instance.get();
    }


    public Connection acquireConnection() throws DatabaseException {
        Connection connection = null;

        try {
            connection = (availableConnections.size() + busyConnections.size() < poolMaxSize)
                    ? ConnectionFactory.createConnection()
                    : availableConnections.take();

            Instant usageStart = Instant.now();
            busyConnections.add(Pair.of(connection, usageStart));
        } catch (InterruptedException e) {
            logger.error("Caught an exception", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if ((connection != null)
                && (connection.getClass() == ProxyConnection.class)) {
            busyConnections.removeIf(pair -> pair.getLeft().equals(connection));

            try {
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                logger.error("Caught an exception", e);
                Thread.currentThread().interrupt();
            }
        } else {
            logger.warn("Trying to release a connection not supposed to be released!");
        }
    }

    public void destroyPool() {

        for (int i = 0; i < poolMaxSize; i++) {
            try {
                ProxyConnection connection = (ProxyConnection) availableConnections.take();
                connection.closeWrappedConnection();
            } catch (InterruptedException e) {
                logger.error("Caught an exception", e);
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error("Unable to close connection in a proper way", e);
            }
        }

        Enumeration<Driver> driversEnumeration = DriverManager.getDrivers();
        while (driversEnumeration.hasMoreElements()) {
            Driver driver = driversEnumeration.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Failed to deregister driver: ", e);
            }
        }

    }
}

package com.dvoraninovich.establishment.model.pool;

import com.dvoraninovich.establishment.exception.DatabaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);

    private static final String DB_PROPERTIES_NAME = "db.properties";
    private static final String DB_DRIVER_NAME_PROPERTY = "driver";
    private static final String DB_URL_PROPERTY = "url";


    private static final String dbUrl;


    private static final Properties dbProperties = new Properties();

    static {
        URL resource = ConnectionFactory.class.getClassLoader().getResource(DB_PROPERTIES_NAME);

        if (resource == null) {
            logger.fatal("Impossible to read database properties");
            throw new RuntimeException("Impossible to read database properties");
        }

        String propertiesPath = new File(resource.getFile()).getAbsolutePath();

        try (InputStream inputStream = new FileInputStream(propertiesPath)) {
            dbProperties.load(inputStream);

            String driver = dbProperties.getProperty(DB_DRIVER_NAME_PROPERTY);
            Class.forName(driver);

            dbUrl = dbProperties.getProperty(DB_URL_PROPERTY);
        } catch (IOException e) {
            logger.fatal("Unable to read database properties", e);
            throw new RuntimeException("Unable to read database properties", e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Cannot load specified database driver", e);
            throw new RuntimeException("Cannot load specified database driver", e);
        }
    }

    static Connection createConnection() throws DatabaseException {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbProperties);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            logger.error("Impossible to establish connection", e);
            throw new DatabaseException("Impossible to establish connection", e);
        }
    }
}

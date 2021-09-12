package com.dvoraninovich.establishment.controller.listener;

import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnectionPool.getInstance().destroyPool();
    }
}

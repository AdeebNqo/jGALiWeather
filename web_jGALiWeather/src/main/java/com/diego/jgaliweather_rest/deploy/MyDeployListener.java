package com.diego.jgaliweather_rest.deploy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyDeployListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {       
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Hacer algo al replegar la aplicación");
    }   
}

package com.flctxx.meditation.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
//import org.hibernate.cfg.Ano

public class SessionFactoryHelper {

    private static SessionFactory sessionFactory = null;

    static {
        try {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

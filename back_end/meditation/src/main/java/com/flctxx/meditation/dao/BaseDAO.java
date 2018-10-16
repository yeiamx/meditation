package com.flctxx.meditation.dao;

import com.flctxx.meditation.utils.SessionFactoryHelper;

import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class BaseDAO<T> {

    /**
     * Insert data
     */
    public void create(T object) {
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Update the database
     * @param object
     */
    public void update(T object) {
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    /**
     * Update if exists else create.
     * @param object
     */
    public Boolean saveOrUpdate(T object) {
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
            return false;
        }
		return true;
    }

    /**
     * Delete from the database
     */
    public void delete(T object) {
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public T find(Class <? extends T> clazz, Serializable id) {
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            return (T) session.get(clazz, id);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }
    
    public List<T> listFromId(String userId, String tableName) {
       Session session = SessionFactoryHelper.getSessionFactory().openSession();
       try{
	        session.beginTransaction();
	            
	        String hql = "from " + tableName+ " Where id=?";     
	        Query q = session.createQuery(hql) ;     
	        q.setString(0, userId);
	        return q.list();
       } finally {
	        session.getTransaction().commit();
	        session.close();
       }
    }
    
    public List<T> listFromDifferId(String userId, String idColumnName, String tableName){
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try{
	        session.beginTransaction();
	            
	        String hql = "from " + tableName+ " Where "+idColumnName+"=?";     
	        Query q = session.createQuery(hql) ;     
	        q.setString(0, userId);
	        return q.list();
	   } finally {
	        session.getTransaction().commit();
	        session.close();
	   }
   }
    
    public List<T> listAll(String tableName){
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try{
	        session.beginTransaction();
	            
	        String hql = "from " + tableName;     
	        Query q = session.createQuery(hql) ;     
	        
	        return q.list();
	   } finally {
	        session.getTransaction().commit();
	        session.close();
	   }
   }
    
    public List<T> list(String hql) {
        Session session = SessionFactoryHelper.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            return session.createQuery(hql).list();
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }
}

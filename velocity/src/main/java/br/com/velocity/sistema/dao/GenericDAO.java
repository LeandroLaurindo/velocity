/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.util.JPAUtil;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

@SuppressWarnings("unchecked")
public class GenericDAO<PK, T> implements Serializable {

    
    //private EntityManager entityManager;

    /*  public GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }*/
    public T getById(PK pk) {
        return (T) JPAUtil.getEntityManager().find(getTypeClass(), pk);
    }

    public T getByParametro(String p) {
        System.out.println("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + p + "");
        return (T) JPAUtil.getEntityManager().createQuery("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + p + "").setMaxResults(1).getSingleResult();
    }

    public void save(T entity) {
        JPAUtil.getEntityManager().getTransaction().begin();
        JPAUtil.getEntityManager().persist(entity);
         JPAUtil.getEntityManager().getTransaction().commit();
    }

    public void update(T entity) {
        try{
        JPAUtil.getEntityManager().getTransaction().begin();
        JPAUtil.getEntityManager().merge(entity);
        JPAUtil.getEntityManager().getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            JPAUtil.getEntityManager().getTransaction().rollback();
        }
    }

    public void delete(T entity) {
        JPAUtil.getEntityManager().getTransaction().begin();
        JPAUtil.getEntityManager().remove(entity);
        JPAUtil.getEntityManager().getTransaction().commit();
    }

    public List<T> findAll() {
        
        return JPAUtil.getEntityManager().createQuery(("FROM " + getTypeClass().getName()))
                .getResultList();
    }

    public List<T> findAll(String parametros) {
        return JPAUtil.getEntityManager().createQuery("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + parametros + "")
                .getResultList();
    }

    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}

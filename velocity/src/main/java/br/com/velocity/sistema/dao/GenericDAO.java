/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;
 
import java.lang.reflect.ParameterizedType;
import java.util.List;
 
import javax.persistence.EntityManager;
 

@SuppressWarnings("unchecked")
public class GenericDAO<PK, T> {
    private EntityManager entityManager;
 
    public GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
 
    public T getById(PK pk) {
        return (T) entityManager.find(getTypeClass(), pk);
    }
 
     public T getByParametro(String p) {
         System.out.println("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + p +"");
        return (T) entityManager.createQuery("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + p +"").getSingleResult();
    }
    public void save(T entity) {
        entityManager.persist(entity);
    }
 
    public void update(T entity) {
        entityManager.merge(entity);
    }
 
    public void delete(T entity) {
        entityManager.remove(entity);
    }
 
    public List<T> findAll() {
        return entityManager.createQuery(("FROM " + getTypeClass().getName()))
                .getResultList();
    }
    
    public List<T> findAll(String parametros){
        return entityManager.createQuery("SELECT c FROM " + getTypeClass().getSimpleName() +" c " + parametros +"")
                .getResultList();
    }
 
    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}
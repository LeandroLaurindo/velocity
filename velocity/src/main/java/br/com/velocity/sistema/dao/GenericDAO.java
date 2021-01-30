/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.util.JPAUtil;
import br.com.velocity.sistema.util.MessagesView;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
public class GenericDAO<PK, T> implements Serializable {

    MessagesView mv = new MessagesView();

    //private EntityManager entityManager;

    /*  public GenericDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }*/
    public T getById(PK pk) {
        return (T) JPAUtil.getEntityManager().find(getTypeClass(), pk);
    }

    public T getByParametro(String p) {
        //System.out.println("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + p + "");
        return (T) JPAUtil.getEntityManager().createQuery("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + p + "").setMaxResults(1).getSingleResult();
    }

    public boolean save(T entity) {
        boolean retorno = false;
        try {
            JPAUtil.getEntityManager().getTransaction().begin();
            JPAUtil.getEntityManager().persist(entity);
            JPAUtil.getEntityManager().getTransaction().commit();
            retorno = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            mv.error("Não foi possivel salvar!");
            JPAUtil.getEntityManager().getTransaction().rollback();
        }
        return retorno;
    }

    public boolean update(T entity) {
        boolean retorno = false;
        try {
            JPAUtil.getEntityManager().getTransaction().begin();
            JPAUtil.getEntityManager().merge(entity);
            JPAUtil.getEntityManager().getTransaction().commit();
            retorno = true;
        } catch (Exception e) {
            e.printStackTrace();
            mv.error("Não foi possivel atualizar!");
            JPAUtil.getEntityManager().getTransaction().rollback();
        }
        return retorno;
    }

    public boolean delete(T entity) {
        boolean retorno = false;
        try {
            JPAUtil.getEntityManager().getTransaction().begin();
            JPAUtil.getEntityManager().remove(entity);
            JPAUtil.getEntityManager().getTransaction().commit();
            retorno = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            mv.error("Não foi possivel remover!");
            JPAUtil.getEntityManager().getTransaction().rollback();
        }
        return retorno;
    }

    public List<T> findAll() {

        return JPAUtil.getEntityManager().createQuery(("FROM " + getTypeClass().getName()))
                .getResultList();
    }

    public List<T> findAll(String parametros) {
        //System.err.println("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + parametros + "");
        return JPAUtil.getEntityManager().createQuery("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + parametros + "")
                .getResultList();
    }

    public List<String> findAll(String campos, String parametros) {
        //System.err.println("SELECT c FROM " + getTypeClass().getSimpleName() + " c " + parametros + "");
        return JPAUtil.getEntityManager().createQuery("SELECT " + campos + " FROM " + getTypeClass().getSimpleName() + " c " + parametros + "")
                .getResultList();
    }

    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}

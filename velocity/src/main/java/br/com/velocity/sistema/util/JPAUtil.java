/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

/**
 *
 * @author Leandro Laurindo
 */
@Transactional
public class JPAUtil {

    private static final EntityManagerFactory emf;
    private static EntityManager entityManager = null;

    static {
        emf = Persistence.createEntityManagerFactory("locadoraPU");
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }
}

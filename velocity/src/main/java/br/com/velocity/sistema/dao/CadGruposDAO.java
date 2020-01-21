/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadGrupos;
import javax.persistence.EntityManager;

/**
 *
 * @author Leandro Laurindo
 */
public class CadGruposDAO extends GenericDAO<Integer, CadGrupos>{
    
    public CadGruposDAO(EntityManager entityManager) {
        super(entityManager);
    }
    
}

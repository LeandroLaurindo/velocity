/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadFornecedor;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
import javax.persistence.EntityManager;

/**
 *
 * @author Leandro Laurindo
 */
public class CadFornecedorDAO extends GenericDAO<Integer, CadFornecedor> implements Serializable{
    
    public CadFornecedorDAO() {
        //super(Util.JpaEntityManager());
    }
    
}

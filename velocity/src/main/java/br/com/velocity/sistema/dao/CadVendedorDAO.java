/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadVendedor;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
import javax.persistence.EntityManager;

/**
 *
 * @author Leandro Laurindo
 */
public class CadVendedorDAO extends GenericDAO<Integer, CadVendedor> implements Serializable{
    
    public CadVendedorDAO() {
        //super(Util.JpaEntityManager());
    }
    
}

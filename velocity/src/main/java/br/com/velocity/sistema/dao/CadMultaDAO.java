/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadMulta;
import java.io.Serializable;

/**
 *
 * @author Leandro Laurindo
 */
public class CadMultaDAO extends GenericDAO<Integer, CadMulta> implements Serializable{
    
    public CadMultaDAO() {
        //super(Util.JpaEntityManager());
    }
    
}

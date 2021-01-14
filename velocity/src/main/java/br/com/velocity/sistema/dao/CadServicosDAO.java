/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.dao;

import br.com.velocity.sistema.entidades.CadServicos;
import java.io.Serializable;

/**
 *
 * @author Leandro Laurindo
 */
public class CadServicosDAO extends GenericDAO<Integer, CadServicos> implements Serializable{
    
    public CadServicosDAO() {
        //super(Util.JpaEntityManager());
    }
    
}

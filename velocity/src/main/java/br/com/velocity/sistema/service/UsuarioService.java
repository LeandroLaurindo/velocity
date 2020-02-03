/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.service;

import br.com.velocity.sistema.dao.UsuarioDAO;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.managers.SimpleEntityManager;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Leandro Laurindo
 */
public class UsuarioService implements Serializable {

    private UsuarioDAO dao;
    private SimpleEntityManager simpleEntityManager;

    public UsuarioService(){
        dao = new UsuarioDAO();
    }

    public void save(Usuario cadUsuario) {
            dao.save(cadUsuario);
    }

    public void update(Usuario cadUsuario) {
             dao.update(cadUsuario);
    }

    public void delete(Integer id) {
             
            Usuario u = dao.getByParametro(" WHERE c.idUsuario=" + id+"");
            dao.delete(u);
    }

    public Usuario carregar(Integer id){
        return dao.getById(id);
    }
    //aqui passa do where para frente os filtros desejados
    public Usuario carregar(String parametro){
        return dao.getByParametro(parametro);
    }
    
    public List<Usuario> findAll() {
        return dao.findAll();
    }
    
    public List<Usuario> findAll(String parametros) {
        return dao.findAll(parametros);
    }

}

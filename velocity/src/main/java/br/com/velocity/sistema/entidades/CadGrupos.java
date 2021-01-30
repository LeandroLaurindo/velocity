/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_grupos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadGrupos.findAll", query = "SELECT c FROM CadGrupos c")})
public class CadGrupos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_grupo")
    private Integer idGrupo;
    @Size(max = 50, message = "O campo Tipo deve ter entre 1 e 50 caracteres")
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 100, message = "O campo Nome deve ter entre 1 e 100 caracteres")
    @Column(name = "nome")
    private String nome;
    @Size(max = 250, message = "O campo Descrição deve ter entre 1 e 250 caracteres")
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 2147483647)
    @Column(name = "opcionais")
    private String opcionais;
    @Column(name = "categoria_fk")
    private Integer categoriaFk;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco")
    private BigDecimal preco;

    public CadGrupos() {
    }

    public CadGrupos(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(String opcionais) {
        this.opcionais = opcionais;
    }

    public Integer getCategoriaFk() {
        return categoriaFk;
    }

    public void setCategoriaFk(Integer categoriaFk) {
        this.categoriaFk = categoriaFk;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadGrupos)) {
            return false;
        }
        CadGrupos other = (CadGrupos) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.locadora.entidades.CadGrupos[ idGrupo=" + idGrupo + " ]";
    }
    
}

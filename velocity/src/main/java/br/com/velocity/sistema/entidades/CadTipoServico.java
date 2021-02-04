/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_tipo_servico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadTipoServico.findAll", query = "SELECT c FROM CadTipoServico c")})
public class CadTipoServico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_servico")
    private Integer idTipoServico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "descricao_servico")
    private String descricaoServico;

    public CadTipoServico() {
    }

    public CadTipoServico(Integer idTipoServico) {
        this.idTipoServico = idTipoServico;
    }

    public CadTipoServico(Integer idTipoServico, String descricaoServico) {
        this.idTipoServico = idTipoServico;
        this.descricaoServico = descricaoServico;
    }

    public Integer getIdTipoServico() {
        return idTipoServico;
    }

    public void setIdTipoServico(Integer idTipoServico) {
        this.idTipoServico = idTipoServico;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoServico != null ? idTipoServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadTipoServico)) {
            return false;
        }
        CadTipoServico other = (CadTipoServico) object;
        if ((this.idTipoServico == null && other.idTipoServico != null) || (this.idTipoServico != null && !this.idTipoServico.equals(other.idTipoServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.velocity.sistema.entidades.CadTipoServico[ idTipoServico=" + idTipoServico + " ]";
    }

}

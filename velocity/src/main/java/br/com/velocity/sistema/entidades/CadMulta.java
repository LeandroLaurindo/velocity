/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_multa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadMulta.findAll", query = "SELECT c FROM CadMulta c")})
public class CadMulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_multa")
    private Integer idMulta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "auto_de_infracao")
    private String autoDeInfracao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "orgao")
    private String orgao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_multa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMulta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario_fk")
    private int usuarioFk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "placa")
    private String placa;

    public CadMulta() {
    }

    public CadMulta(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public CadMulta(Integer idMulta, String autoDeInfracao, String orgao, Date dataMulta, int usuarioFk, String placa) {
        this.idMulta = idMulta;
        this.autoDeInfracao = autoDeInfracao;
        this.orgao = orgao;
        this.dataMulta = dataMulta;
        this.usuarioFk = usuarioFk;
        this.placa = placa;
    }

    public Integer getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(Integer idMulta) {
        this.idMulta = idMulta;
    }

    public String getAutoDeInfracao() {
        return autoDeInfracao;
    }

    public void setAutoDeInfracao(String autoDeInfracao) {
        this.autoDeInfracao = autoDeInfracao;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public Date getDataMulta() {
        return dataMulta;
    }

    public void setDataMulta(Date dataMulta) {
        this.dataMulta = dataMulta;
    }

    public int getUsuarioFk() {
        return usuarioFk;
    }

    public void setUsuarioFk(int usuarioFk) {
        this.usuarioFk = usuarioFk;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMulta != null ? idMulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadMulta)) {
            return false;
        }
        CadMulta other = (CadMulta) object;
        if ((this.idMulta == null && other.idMulta != null) || (this.idMulta != null && !this.idMulta.equals(other.idMulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.velocity.sistema.entidades.CadMulta[ idMulta=" + idMulta + " ]";
    }

}

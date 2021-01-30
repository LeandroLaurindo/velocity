/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_servicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadServicos.findAll", query = "SELECT c FROM CadServicos c")})
public class CadServicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_servico")
    private Integer idServico;
    @Size(max = 2147483647)
    @Column(name = "tipo_servico")
    private String tipoServico;
    @Column(name = "codigo_servico")
    private Integer codigoServico;
    @Size(max = 30, message = "O campo Nome Serviço deve ter entre 1 e 30 caracteres")
    @Column(name = "nome_servico")
    private String nomeServico;
    @Size(max = 500, message = "O campo descrição deve ter entre 1 e 500 caracteres")
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "data_servico")
    @Temporal(TemporalType.DATE)
    private Date dataServico;
    @Size(max = 10, message = "O campo Situação deve ter entre 1 e 10 caracteres")
    @Column(name = "situacao")
    private String situacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_servico")
    private BigDecimal valorServico;
    @Column(name = "prestador_servico")
    private Integer prestadorServico;
    @Column(name = "veiculo")
    private Integer veiculo;
    @Column(name = "usuario")
    private Integer usuario;

    public CadServicos() {
    }

    public CadServicos(Integer idServico) {
        this.idServico = idServico;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public Integer getCodigoServico() {
        return codigoServico;
    }

    public void setCodigoServico(Integer codigoServico) {
        this.codigoServico = codigoServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataServico() {
        return dataServico;
    }

    public void setDataServico(Date dataServico) {
        this.dataServico = dataServico;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public Integer getPrestadorServico() {
        return prestadorServico;
    }

    public void setPrestadorServico(Integer prestadorServico) {
        this.prestadorServico = prestadorServico;
    }

    public Integer getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Integer veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServico != null ? idServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadServicos)) {
            return false;
        }
        CadServicos other = (CadServicos) object;
        if ((this.idServico == null && other.idServico != null) || (this.idServico != null && !this.idServico.equals(other.idServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.velocity.sistema.entidades.CadServicos[ idServico=" + idServico + " ]";
    }

}

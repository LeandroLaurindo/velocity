/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Leandro Laurindo
 */
@Entity
@Table(name = "cad_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadCliente.findAll", query = "SELECT c FROM CadCliente c")})
public class CadCliente implements Serializable {

    @OneToMany(mappedBy = "clienteFk")
    private Collection<ControleVeiculos> controleVeiculosCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "vendedor_fk")
    private Integer vendedorFk;
    @Size(max = 30, message = "O campo Plano Pagamento Estudal deve ter entre 1 e 30 caracteres")
    @Column(name = "plano_pagto")
    private String planoPagto;
    @Column(name = "tabela_preco_fk")
    private Integer tabelaPrecoFk;
    @Column(name = "segmento_fk")
    private Integer segmentoFk;
    @Column(name = "classificacao_fk")
    private Integer classificacaoFk;
    @Size(max = 50, message = "O campo Origem Cliente deve ter entre 1 e 50 caracteres")
    @Column(name = "origem_cliente")
    private String origemCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "limite_credito")
    private BigDecimal limiteCredito;
    @Size(max = 250, message = "O campo Observação deve ter entre 1 e 250 caracteres")
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "representante_legal_fk")
    private Integer representanteLegalFk;
    @JoinColumn(name = "documento_fk", referencedColumnName = "id_documentos")
    @ManyToOne(optional = false)
    private CadDocumentos documentoFk;

    public CadCliente() {
    }

    public CadCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getVendedorFk() {
        return vendedorFk;
    }

    public void setVendedorFk(Integer vendedorFk) {
        this.vendedorFk = vendedorFk;
    }

    public String getPlanoPagto() {
        return planoPagto;
    }

    public void setPlanoPagto(String planoPagto) {
        this.planoPagto = planoPagto;
    }

    public Integer getTabelaPrecoFk() {
        return tabelaPrecoFk;
    }

    public void setTabelaPrecoFk(Integer tabelaPrecoFk) {
        this.tabelaPrecoFk = tabelaPrecoFk;
    }

    public Integer getSegmentoFk() {
        return segmentoFk;
    }

    public void setSegmentoFk(Integer segmentoFk) {
        this.segmentoFk = segmentoFk;
    }

    public Integer getClassificacaoFk() {
        return classificacaoFk;
    }

    public void setClassificacaoFk(Integer classificacaoFk) {
        this.classificacaoFk = classificacaoFk;
    }

    public String getOrigemCliente() {
        return origemCliente;
    }

    public void setOrigemCliente(String origemCliente) {
        this.origemCliente = origemCliente;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getRepresentanteLegalFk() {
        return representanteLegalFk;
    }

    public void setRepresentanteLegalFk(Integer representanteLegalFk) {
        this.representanteLegalFk = representanteLegalFk;
    }

    public CadDocumentos getDocumentoFk() {
        return documentoFk;
    }

    public void setDocumentoFk(CadDocumentos documentoFk) {
        this.documentoFk = documentoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadCliente)) {
            return false;
        }
        CadCliente other = (CadCliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.locadora.entidades.CadCliente[ idCliente=" + idCliente + " ]";
    }

    @XmlTransient
    public Collection<ControleVeiculos> getControleVeiculosCollection() {
        return controleVeiculosCollection;
    }

    public void setControleVeiculosCollection(Collection<ControleVeiculos> controleVeiculosCollection) {
        this.controleVeiculosCollection = controleVeiculosCollection;
    }

}

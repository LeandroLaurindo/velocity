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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe Rios
 */
@Entity
@Table(name = "controle_veiculos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControleVeiculos.findAll", query = "SELECT c FROM ControleVeiculos c")})
public class ControleVeiculos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_controle_veiculo")
    private Integer idControleVeiculo;
    @Column(name = "data_hora_saida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraSaida;
    @Column(name = "data_hora_prevista_retorno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPrevistaRetorno;
    @Column(name = "data_hora_retorno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraRetorno;
    @Size(max = 2147483647)
    @Column(name = "condicoes_veiculo_saida")
    private String condicoesVeiculoSaida;
    @Size(max = 2147483647)
    @Column(name = "condicoes_veiculo_retorno")
    private String condicoesVeiculoRetorno;
    @Size(max = 2147483647)
    @Column(name = "resposavel_pelo_veiculo")
    private String resposavelPeloVeiculo;
    @Column(name = "historico_reparos_fk")
    private Integer historicoReparosFk;
    @Column(name = "agendamento_proximos_reparos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date agendamentoProximosReparos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "km_saida")
    private BigDecimal kmSaida;
    @Column(name = "km_retorno")
    private BigDecimal kmRetorno;
    @Size(max = 2147483647)
    @Column(name = "outras_observacoes")
    private String outrasObservacoes;
    @JoinColumn(name = "cliente_fk", referencedColumnName = "id_cliente")
    @ManyToOne
    private CadCliente clienteFk;
    @JoinColumn(name = "veiculo_fk", referencedColumnName = "id_modelo")
    @ManyToOne(optional = false)
    private CadModeloVeiculo veiculoFk;

    public ControleVeiculos() {
    }

    public ControleVeiculos(Integer idControleVeiculo) {
        this.idControleVeiculo = idControleVeiculo;
    }

    public Integer getIdControleVeiculo() {
        return idControleVeiculo;
    }

    public void setIdControleVeiculo(Integer idControleVeiculo) {
        this.idControleVeiculo = idControleVeiculo;
    }

    public Date getDataHoraSaida() {
        return dataHoraSaida;
    }

    public void setDataHoraSaida(Date dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    public Date getDataHoraPrevistaRetorno() {
        return dataHoraPrevistaRetorno;
    }

    public void setDataHoraPrevistaRetorno(Date dataHoraPrevistaRetorno) {
        this.dataHoraPrevistaRetorno = dataHoraPrevistaRetorno;
    }

    public Date getDataHoraRetorno() {
        return dataHoraRetorno;
    }

    public void setDataHoraRetorno(Date dataHoraRetorno) {
        this.dataHoraRetorno = dataHoraRetorno;
    }

    public String getCondicoesVeiculoSaida() {
        return condicoesVeiculoSaida;
    }

    public void setCondicoesVeiculoSaida(String condicoesVeiculoSaida) {
        this.condicoesVeiculoSaida = condicoesVeiculoSaida;
    }

    public String getCondicoesVeiculoRetorno() {
        return condicoesVeiculoRetorno;
    }

    public void setCondicoesVeiculoRetorno(String condicoesVeiculoRetorno) {
        this.condicoesVeiculoRetorno = condicoesVeiculoRetorno;
    }

    public String getResposavelPeloVeiculo() {
        return resposavelPeloVeiculo;
    }

    public void setResposavelPeloVeiculo(String resposavelPeloVeiculo) {
        this.resposavelPeloVeiculo = resposavelPeloVeiculo;
    }

    public Integer getHistoricoReparosFk() {
        return historicoReparosFk;
    }

    public void setHistoricoReparosFk(Integer historicoReparosFk) {
        this.historicoReparosFk = historicoReparosFk;
    }

    public Date getAgendamentoProximosReparos() {
        return agendamentoProximosReparos;
    }

    public void setAgendamentoProximosReparos(Date agendamentoProximosReparos) {
        this.agendamentoProximosReparos = agendamentoProximosReparos;
    }

    public BigDecimal getKmSaida() {
        return kmSaida;
    }

    public void setKmSaida(BigDecimal kmSaida) {
        this.kmSaida = kmSaida;
    }

    public BigDecimal getKmRetorno() {
        return kmRetorno;
    }

    public void setKmRetorno(BigDecimal kmRetorno) {
        this.kmRetorno = kmRetorno;
    }

    public String getOutrasObservacoes() {
        return outrasObservacoes;
    }

    public void setOutrasObservacoes(String outrasObservacoes) {
        this.outrasObservacoes = outrasObservacoes;
    }

    public CadCliente getClienteFk() {
        return clienteFk;
    }

    public void setClienteFk(CadCliente clienteFk) {
        this.clienteFk = clienteFk;
    }

    public CadModeloVeiculo getVeiculoFk() {
        return veiculoFk;
    }

    public void setVeiculoFk(CadModeloVeiculo veiculoFk) {
        this.veiculoFk = veiculoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControleVeiculo != null ? idControleVeiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControleVeiculos)) {
            return false;
        }
        ControleVeiculos other = (ControleVeiculos) object;
        if ((this.idControleVeiculo == null && other.idControleVeiculo != null) || (this.idControleVeiculo != null && !this.idControleVeiculo.equals(other.idControleVeiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.velocity.sistema.entidades.ControleVeiculos[ idControleVeiculo=" + idControleVeiculo + " ]";
    }
    
}

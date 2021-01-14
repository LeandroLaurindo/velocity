/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadFornecedor;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.entidades.CadServicos;
import br.com.velocity.sistema.service.CadFornecedorService;
import br.com.velocity.sistema.service.CadModeloVeiculoService;
import br.com.velocity.sistema.service.CadServicosService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean
@ViewScoped
public class ServicosBean implements Serializable {

    private CadServicos cadServicos;

    private List<CadServicos> listaCadServicos;

    private CadServicosService cadServicosService;

    private CadFornecedorService fornecedorService;

    private CadModeloVeiculoService veiculoService;

    private CadModeloVeiculo modeloVeiculo;

    private List<CadModeloVeiculo> listaModeloVeiculos;

    private CadFornecedor cadFornecedor;

    private List<CadFornecedor> listaCadFornecedores;

    private List<String> listaNomePrestador;

    private List<String> listaNomeVeiculo;

    private Integer idV;

    private Integer idF;

    private Integer idVeiculoValido;

    private Integer idPrestadorValido;

    private String nomeV;

    private String nomePrestador;

    private MessagesView msg;

    private String valorDoServico;

    @PostConstruct
    public void init() {
        cadServicosService = new CadServicosService();
        fornecedorService = new CadFornecedorService();
        veiculoService = new CadModeloVeiculoService();
        cadServicos = new CadServicos();
        msg = new MessagesView();
        try {
            String veiculo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idVeiculo");
            if (null != veiculo) {
                idV = Integer.valueOf(veiculo);
                carregarVeiculo();
                Util.updateComponente("forServicos");
                Util.executarAcao("PF('dialogoServico').show()");
            }

            String fornecedor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idFornecedor");
            if (null != fornecedor) {
                idF = Integer.valueOf(fornecedor);
                carregarFornecedor();
                Util.updateComponente("forServicos");
                Util.executarAcao("PF('dialogoServico').show()");
            }
            listarVeiculos();
            listarFornecedores();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listarServicos();
        Util.updateComponente("forTabelaServicos");

    }

    public void carregarVeiculo() {
        this.modeloVeiculo = new CadModeloVeiculo();
        this.modeloVeiculo = veiculoService.carregar(idV);
        this.cadServicos.setVeiculo(idV);
        this.nomeV = this.modeloVeiculo.getModelo();
    }

    public void carregarFornecedor() {
        this.cadFornecedor = new CadFornecedor();
        this.cadFornecedor = fornecedorService.carregar(idF);
        this.cadServicos.setPrestadorServico(idF);
        this.nomePrestador = this.cadFornecedor.getDocumentoFk().getPessoaFk().getNome();
    }

    public void listarFornecedores() {
        this.listaCadFornecedores = new ArrayList<>();
        this.listaCadFornecedores = this.fornecedorService.findAll("where c.documentoFk.pessoaFk.situacao ='SIM' ORDER BY c.documentoFk.pessoaFk.nome, c.documentoFk.pessoaFk.razaoSocial ASC");
        this.listaNomePrestador = new ArrayList<>();
        if (this.listaCadFornecedores.size() > 0) {
            for (CadFornecedor listaCadFornecedore : listaCadFornecedores) {
                this.listaNomePrestador.add(listaCadFornecedore.getDocumentoFk().getPessoaFk().getNome());
            }
        }
    }

    public void listarServicos() {
        this.listaCadServicos = this.cadServicosService.findAll();

    }

    public void listarVeiculos() {
        this.listaModeloVeiculos = new ArrayList<>();
        this.listaModeloVeiculos = veiculoService.findAll(" WHERE (c.disponivel = true OR c.disponivel is null) ORDER BY c.modelo ASC");
        this.listaNomeVeiculo = new ArrayList<>();
        if (this.listaModeloVeiculos.size() > 0) {
            for (CadModeloVeiculo listaModeloVeiculo : listaModeloVeiculos) {
                this.listaNomeVeiculo.add(listaModeloVeiculo.getModelo());
            }
        }
    }

    public void setarIdVeiculo() {
        this.modeloVeiculo = new CadModeloVeiculo();
        this.modeloVeiculo = veiculoService.carregarPorparametro(" WHERE c.modelo = '" + nomeV + "'");
        this.cadServicos.setVeiculo(modeloVeiculo.getIdModelo());
    }

    public void setarIdFornecedor() {
        this.cadFornecedor = new CadFornecedor();
        this.cadFornecedor = fornecedorService.carregarPorParamentro(" WHERE c.documentoFk.pessoaFk.nome ='" + nomePrestador + "'");
        this.cadServicos.setPrestadorServico(this.cadFornecedor.getIdFornecedor());
    }

    public void salvarServico() {
        this.valorDoServico = valorDoServico.replaceAll("\\.", "");
        this.valorDoServico = valorDoServico.replaceAll("\\,", ".");
        this.cadServicos.setValorServico(new BigDecimal(this.valorDoServico));
        this.cadServicosService.save(this.cadServicos);
        limparServico();
        listarServicos();
        Util.updateComponente("forServicos");
        Util.updateComponente("forTabelaServicos");
        Util.executarAcao("PF('dialogoServico').hide()");
    }

    public void novoServico() {
        this.nomePrestador = "";
        this.nomeV = "";
        this.idF = 0;
        this.idV = 0;
        this.cadFornecedor = new CadFornecedor();
        this.modeloVeiculo = new CadModeloVeiculo();
        this.idPrestadorValido = 0;
        this.idVeiculoValido = 0;
        this.cadServicos = new CadServicos();
        Util.updateComponente("forServicos");
        Util.executarAcao("PF('dialogoServico').show()");
    }

    public void limparServico() {
        this.nomePrestador = "";
        this.nomeV = "";
        this.idF = 0;
        this.idV = 0;
        this.cadFornecedor = new CadFornecedor();
        this.modeloVeiculo = new CadModeloVeiculo();
        this.idPrestadorValido = 0;
        this.idVeiculoValido = 0;
        this.cadServicos = new CadServicos();
    }

    public void editarServico() {
        this.valorDoServico = valorDoServico.replaceAll("\\.", "");
        this.valorDoServico = valorDoServico.replaceAll("\\,", ".");
        this.cadServicos.setValorServico(new BigDecimal(this.valorDoServico));
        this.cadServicosService.update(this.cadServicos);
        listarServicos();
        Util.updateComponente("forServicos");
        Util.updateComponente("forTabelaServicos");
        Util.executarAcao("PF('dialogoServico').hide()");
    }

    public void deletarServico() {
        if (this.cadServicos.getIdServico() != null) {
            this.cadServicosService.delete(this.cadServicos);
            listarServicos();
            Util.updateComponente("forTabelaServicos");
        } else {
            msg.warn("O serviço não pode ser nulo!");
        }
    }

    public void setarParaDeletar(Integer id) {
        this.cadServicos = this.cadServicosService.carregar(id);
        Util.executarAcao("PF('dlgConfI').show()");
    }

    public void setarParaEditar(CadServicos cadServicos) {
        this.cadServicos = cadServicos;
        this.cadFornecedor = this.fornecedorService.carregar(cadServicos.getPrestadorServico());
        this.nomePrestador = cadFornecedor.getDocumentoFk().getPessoaFk().getNome();
        this.modeloVeiculo = this.veiculoService.carregar(cadServicos.getVeiculo());
        this.nomeV = this.modeloVeiculo.getModelo();
        this.valorDoServico = cadServicos.getValorServico().toString();
        this.valorDoServico = formataValor(this.valorDoServico);
        Util.updateComponente("forServicos");
        Util.executarAcao("PF('dialogoServico').show()");
    }

    public String formataValor(String valor) {
        if (valor != null) {
            if (!valor.isEmpty()) {
                valor = valor.replaceAll("\\.", ",");
                StringBuilder stringBuilder = new StringBuilder(valor);
                if (valor.length() > 6) {
                    stringBuilder.insert(valor.length() - 6, '.');
                }
                if (valor.length() > 9) {
                    stringBuilder.insert(valor.length() - 9, '.');
                }

                if (valor.length() > 12) {
                    stringBuilder.insert(valor.length() - 12, '.');
                }
                return stringBuilder.toString();
            } else {
                return "0,00";
            }
        } else {
            return "0,00";
        }
    }

    public String mostrarPrestador(Integer idPresta) {
        try {
            this.cadFornecedor = new CadFornecedor();
            this.cadFornecedor = fornecedorService.carregar(idPresta);
            return this.cadFornecedor.getDocumentoFk().getPessoaFk().getNome();
        } catch (Exception e) {
            return "";
        }
    }

    public String mostrarVeiculo(Integer idVeicu) {
        try {
            this.modeloVeiculo = new CadModeloVeiculo();
            this.modeloVeiculo = veiculoService.carregar(idVeicu);
            this.cadServicos.setVeiculo(idV);
            return this.modeloVeiculo.getModelo();
        } catch (Exception e) {
            return "";
        }
    }

    public CadServicos getCadServicos() {
        return cadServicos;
    }

    public void setCadServicos(CadServicos cadServicos) {
        this.cadServicos = cadServicos;
    }

    public List<CadServicos> getListaCadServicos() {
        return listaCadServicos;
    }

    public void setListaCadServicos(List<CadServicos> listaCadServicos) {
        this.listaCadServicos = listaCadServicos;
    }

    public CadModeloVeiculo getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(CadModeloVeiculo modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public List<CadModeloVeiculo> getListaModeloVeiculos() {
        return listaModeloVeiculos;
    }

    public void setListaModeloVeiculos(List<CadModeloVeiculo> listaModeloVeiculos) {
        this.listaModeloVeiculos = listaModeloVeiculos;
    }

    public CadFornecedor getCadFornecedor() {
        return cadFornecedor;
    }

    public void setCadFornecedor(CadFornecedor cadFornecedor) {
        this.cadFornecedor = cadFornecedor;
    }

    public List<CadFornecedor> getListaCadFornecedores() {
        return listaCadFornecedores;
    }

    public void setListaCadFornecedores(List<CadFornecedor> listaCadFornecedores) {
        this.listaCadFornecedores = listaCadFornecedores;
    }

    public List<String> getListaNomePrestador() {
        return listaNomePrestador;
    }

    public void setListaNomePrestador(List<String> listaNomePrestador) {
        this.listaNomePrestador = listaNomePrestador;
    }

    public List<String> getListaNomeVeiculo() {
        return listaNomeVeiculo;
    }

    public void setListaNomeVeiculo(List<String> listaNomeVeiculo) {
        this.listaNomeVeiculo = listaNomeVeiculo;
    }

    public Integer getIdV() {
        return idV;
    }

    public void setIdV(Integer idV) {
        this.idV = idV;
    }

    public Integer getIdF() {
        return idF;
    }

    public void setIdF(Integer idF) {
        this.idF = idF;
    }

    public Integer getIdVeiculoValido() {
        return idVeiculoValido;
    }

    public void setIdVeiculoValido(Integer idVeiculoValido) {
        this.idVeiculoValido = idVeiculoValido;
    }

    public Integer getIdPrestadorValido() {
        return idPrestadorValido;
    }

    public void setIdPrestadorValido(Integer idPrestadorValido) {
        this.idPrestadorValido = idPrestadorValido;
    }

    public String getNomeV() {
        return nomeV;
    }

    public void setNomeV(String nomeV) {
        this.nomeV = nomeV;
    }

    public String getNomePrestador() {
        return nomePrestador;
    }

    public void setNomePrestador(String nomePrestador) {
        this.nomePrestador = nomePrestador;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public String getValorDoServico() {
        return valorDoServico;
    }

    public void setValorDoServico(String valorDoServico) {
        this.valorDoServico = valorDoServico;
    }

}

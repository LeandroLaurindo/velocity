package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.entidades.ControleVeiculos;
import br.com.velocity.sistema.service.CadClienteService;
import br.com.velocity.sistema.service.CadModeloVeiculoService;
import br.com.velocity.sistema.service.ControleVeiculosService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
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
public class ControleVeiculosBean implements Serializable {

    private CadClienteService clienteService;
    private ControleVeiculosService service;
    private CadModeloVeiculoService veiculoService;
    private List<String> listaVeiculos;
    private List<String> listaClientes;
    private CadModeloVeiculo veiculo;
    private ControleVeiculos controleVeiculos;
    private List<ControleVeiculos> lista;
    private List<CadModeloVeiculo> listaModeloVeiculos;
    private List<CadCliente> listaCadClientes;
    private MessagesView msg;
    private String idV = "";
    private String idC = "";
    private CadCliente cadCliente;
    private Integer idCarro;
    private Integer idCliente;

    @PostConstruct
    public void init() {
        this.listaCadClientes = new ArrayList<>();
        this.clienteService = new CadClienteService();
        this.veiculoService = new CadModeloVeiculoService();
        this.service = new ControleVeiculosService();
        this.listaVeiculos = new ArrayList<>();
        this.cadCliente = new CadCliente();
        this.listaClientes = new ArrayList<>();
        idV = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idv");
        idC = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idC");
        if (valido(idC)) {
             idCliente =  Integer.valueOf(idC);     
            carregarCliente();
        } else {
            if (valido(idV)) {
                idCarro = Integer.valueOf(idV);
                carregarVeiculo();
            }
        }
        listarVeiculos();
        listarClientes();

        this.controleVeiculos = new ControleVeiculos();
        this.lista = new ArrayList<>();
        this.msg = new MessagesView();
        listar();

    }

    public boolean valido(String campo) {
        return campo != null && !campo.isEmpty();
    }

    public void listar() {
        this.lista = service.findAll();
        Util.updateComponente("formCV");
    }

    public void carregarVeiculo() {
        this.veiculo = new CadModeloVeiculo();
        this.veiculo = veiculoService.carregar(idCarro);
        this.controleVeiculos.setVeiculoFk(veiculo);
    }

    public void listarVeiculos() {
        this.listaModeloVeiculos = new ArrayList<>();
        this.listaModeloVeiculos = veiculoService.findAll(" WHERE (c.disponivel = true OR c.disponivel is null) ORDER BY c.modelo ASC");
    }

    public void carregarCliente() {
        this.cadCliente = new CadCliente();
        this.cadCliente = clienteService.carregar(idCliente);
        this.controleVeiculos.setClienteFk(cadCliente);
    }

    public void listarClientes() {
        this.listaCadClientes = new ArrayList<>();
        this.listaCadClientes = this.clienteService.findAll(" WHERE c.documentoFk.pessoaFk.situacao ='SIM' ORDER BY c.documentoFk.pessoaFk.nome, c.documentoFk.pessoaFk.razaoSocial ASC");
    }

    public void salvar() {
        if (this.controleVeiculos.getIdControleVeiculo() == null) {
            this.service.save(this.controleVeiculos);
            this.msg.info("Salvo com sucesso.");
            listar();
            this.controleVeiculos = new ControleVeiculos();
            Util.executarAcao("PF('dglVControle').hide()");
        } else {
            this.editar();
        }
    }

    public void editar() {
        this.service.update(this.controleVeiculos);
        this.msg.info("Editado com sucesso.");
        listar();
        this.controleVeiculos = new ControleVeiculos();
        Util.executarAcao("PF('dglVControle').hide()");
    }

    public void deletar() {
        this.service.delete(this.controleVeiculos);
        this.msg.info("Removido com sucesso.");
        listar();
        this.controleVeiculos = new ControleVeiculos();
    }

    public void limpar() {
        listarVeiculos();
        listarClientes();
        this.controleVeiculos = new ControleVeiculos();
        Util.updateComponente("formCadCV");
        Util.executarAcao("PF('dglVControle').show()");
       
    }

    public void novoServico() {
        this.controleVeiculos = new ControleVeiculos();
        Util.executarAcao("PF('dglVControle').show()");
        Util.updateComponente("formCadCV");
    }

    public void novoAluguel() {
        this.controleVeiculos = new ControleVeiculos();
        Util.executarAcao("PF('dglVControle').show()");
        Util.updateComponente("formCadCV");
    }

    public void cancelar() {
        this.controleVeiculos = new ControleVeiculos();
        Util.executarAcao("PF('dglVControle').hide()");
    }

    public void setarDadosEditar(ControleVeiculos cmv) {
        this.controleVeiculos = cmv;
        Util.executarAcao("PF('dglVControle').show()");
        Util.updateComponente("formCadCV");
    }

    public void setarDadosDeletar(ControleVeiculos cmv) {
        this.controleVeiculos = cmv;
        Util.updateComponente("forCmvConf");
        Util.executarAcao("PF('dlgConfCv').show()");
    }

    public String disponivelV(Boolean d) {
        if (d == null) {
            return "Sim";
        } else {
            if (d) {
                return "Sim";
            } else {
                return "Não";
            }
        }
    }

    public ControleVeiculos getControleVeiculos() {
        return controleVeiculos;
    }

    public void setControleVeiculos(ControleVeiculos controleVeiculos) {
        this.controleVeiculos = controleVeiculos;
    }

    public List<ControleVeiculos> getLista() {
        return lista;
    }

    public void setLista(List<ControleVeiculos> lista) {
        this.lista = lista;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public List<String> getListaVeiculos() {
        return listaVeiculos;
    }

    public void setListaVeiculos(List<String> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }

    public CadModeloVeiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(CadModeloVeiculo veiculo) {
        this.veiculo = veiculo;
    }

    public CadCliente getCadCliente() {
        return cadCliente;
    }

    public void setCadCliente(CadCliente cadCliente) {
        this.cadCliente = cadCliente;
    }

    public List<String> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<String> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public String getIdV() {
        return idV;
    }

    public void setIdV(String idV) {
        this.idV = idV;
    }

    public String getIdC() {
        return idC;
    }

    public void setIdC(String idC) {
        this.idC = idC;
    }

    public List<CadModeloVeiculo> getListaModeloVeiculos() {
        return listaModeloVeiculos;
    }

    public void setListaModeloVeiculos(List<CadModeloVeiculo> listaModeloVeiculos) {
        this.listaModeloVeiculos = listaModeloVeiculos;
    }

    public List<CadCliente> getListaCadClientes() {
        return listaCadClientes;
    }

    public void setListaCadClientes(List<CadCliente> listaCadClientes) {
        this.listaCadClientes = listaCadClientes;
    }

}

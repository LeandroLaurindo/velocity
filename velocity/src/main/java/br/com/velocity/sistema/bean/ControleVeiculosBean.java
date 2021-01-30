package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadFornecedor;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.entidades.CadServicos;
import br.com.velocity.sistema.entidades.ControleVeiculos;
import br.com.velocity.sistema.service.CadClienteService;
import br.com.velocity.sistema.service.CadFornecedorService;
import br.com.velocity.sistema.service.CadModeloVeiculoService;
import br.com.velocity.sistema.service.CadServicosService;
import br.com.velocity.sistema.service.ControleVeiculosService;
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
public class ControleVeiculosBean implements Serializable {

    private CadClienteService clienteService;
    private ControleVeiculosService service;
    private CadModeloVeiculoService veiculoService;
    private CadServicosService cadServicosService;
    private List<String> listaVeiculos;
    private List<String> listaClientes;
    private CadModeloVeiculo veiculo;
    private ControleVeiculos controleVeiculos;
    private List<ControleVeiculos> lista;
    private List<CadModeloVeiculo> listaModeloVeiculos;
    private List<CadCliente> listaCadClientes;
    private List<CadServicos> listaCadServicos;
    private CadServicos cadServicos;
    private MessagesView msg;
    private String idV = "";
    private String idC = "";
    private CadCliente cadCliente;
    private Integer idCarro;
    private Integer idCliente;
    private List<CadFornecedor> listaFornecedores;
    private CadFornecedorService fornecedorService = new CadFornecedorService();
    private List<String> tipoSaida;
    private String saidak;
    private String retornok;

    @PostConstruct
    public void init() {
        this.cadServicosService = new CadServicosService();
        this.listaCadServicos = new ArrayList<>();
        this.cadServicos = new CadServicos();
        this.listaCadClientes = new ArrayList<>();
        this.clienteService = new CadClienteService();
        this.veiculoService = new CadModeloVeiculoService();
        this.service = new ControleVeiculosService();
        this.listaVeiculos = new ArrayList<>();
        this.cadCliente = new CadCliente();
        this.listaClientes = new ArrayList<>();
        this.tipoSaida = new ArrayList<>();
        this.tipoSaida.add("ALUGUEL");
        this.tipoSaida.add("RESERVA");
        idV = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idv");
        idC = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idC");
        if (valido(idC)) {
            idCliente = Integer.valueOf(idC);
            carregarCliente();
            Util.updateComponente("formCadCV");
            Util.executarAcao("PF('dglVControle').show()");
        } else {
            if (valido(idV)) {
                idCarro = Integer.valueOf(idV);
                carregarVeiculo();
                Util.updateComponente("formCadCV");
                Util.executarAcao("PF('dglVControle').show()");
            }
        }
        listarVeiculos();
        listarClientes();
        //listarFornecedor();

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
        this.controleVeiculos = new ControleVeiculos();
        this.veiculo = new CadModeloVeiculo();
        this.veiculo = veiculoService.carregar(idCarro);
        this.controleVeiculos.setVeiculoFk(this.veiculo);
    }

    public void carregarVeiculoServico() {
        this.veiculo = new CadModeloVeiculo();
        this.veiculo = veiculoService.carregar(idCarro);
    }

    public void listarFornecedor() {
        this.listaFornecedores = new ArrayList<>();
        this.listaFornecedores = this.fornecedorService.findAll("where c.documentoFk.pessoaFk.situacao ='SIM' ORDER BY c.documentoFk.pessoaFk.nome, c.documentoFk.pessoaFk.razaoSocial ASC");

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
            this.controleVeiculos = new ControleVeiculos();
            this.controleVeiculos = this.service.carregar(controleVeiculos.getIdControleVeiculo());
            if (this.controleVeiculos != null) {
                if (this.controleVeiculos.getIdControleVeiculo() != null) {

                    CadModeloVeiculo cmv = this.veiculoService.carregar(this.controleVeiculos.getVeiculoFk().getIdModelo());
                    if (cmv != null) {
                        if (cmv.getIdModelo() != null) {
                            if (this.controleVeiculos.getAlugueloReserva().equalsIgnoreCase("ALUGUEL") || this.controleVeiculos.getAlugueloReserva().equalsIgnoreCase("RESERVA")) {
                                cmv.setDisponivel(false);
                                cmv.setMotivo(this.controleVeiculos.getAlugueloReserva());
                                this.veiculoService.update(cmv); 
                            }
                        }
                    }
                    this.msg.info("Salvo com sucesso.");
                }
                this.controleVeiculos = new ControleVeiculos();
            }
            listar();
            Util.executarAcao("PF('dglVControle').hide()");
        } else {
            this.editar();
        }
    }

    public boolean veiculoNaoEnull(CadModeloVeiculo cadModeloVeiculo) {
        return cadModeloVeiculo != null && cadModeloVeiculo.getIdModelo() != null;
    }

    public void salvarServico() {

        if (this.cadServicos.getIdServico() == null) {
            if (veiculoNaoEnull(this.veiculo)) {
                this.cadServicos.setVeiculo(this.veiculo.getIdModelo());
            }
            this.cadServicosService.save(this.cadServicos);
            this.msg.info("Salvo com sucesso.");
            //listar();
            this.cadServicos = new CadServicos();
        } else {
            this.cadServicosService.update(cadServicos);
            this.cadServicos = new CadServicos();
        }
    }

    public void editar() {
        this.service.update(this.controleVeiculos);
        this.controleVeiculos = new ControleVeiculos();
        this.controleVeiculos = this.service.carregar(controleVeiculos.getIdControleVeiculo());
        if (this.controleVeiculos != null) {
            if (this.controleVeiculos.getIdControleVeiculo() != null) {
                CadModeloVeiculo cmv = this.veiculoService.carregar(this.controleVeiculos.getVeiculoFk().getIdModelo());
                if (cmv != null) {
                    if (cmv.getIdModelo() != null) {
                        if (this.controleVeiculos.getAlugueloReserva().equalsIgnoreCase("ALUGUEL") || this.controleVeiculos.getAlugueloReserva().equalsIgnoreCase("RESERVA")) {
                            if (this.controleVeiculos.getDataHoraRetorno() == null) {
                                cmv.setDisponivel(false);
                                cmv.setMotivo(this.controleVeiculos.getAlugueloReserva());
                            } else {
                                cmv.setMotivo("");
                                cmv.setDisponivel(true);
                            }
                            this.veiculoService.update(cmv);
                        }
                    }
                }
            }
        }
        this.msg.info("Editado com sucesso.");
        listar();
        this.controleVeiculos = new ControleVeiculos();
        Util.executarAcao("PF('dglVControle').hide()");
    }

    public void deletar() {
        this.service.delete(this.controleVeiculos);
        this.msg.info("Removido com sucesso.");
        if (this.controleVeiculos != null) {
            if (this.controleVeiculos.getIdControleVeiculo() != null) {
                CadModeloVeiculo cmv = this.veiculoService.carregar(this.controleVeiculos.getVeiculoFk().getIdModelo());
                if (cmv != null) {
                    if (cmv.getIdModelo() != null) {
                        cmv.setDisponivel(false);
                        this.veiculoService.update(cmv);
                    }
                }
            }
        }
        listar();
        this.controleVeiculos = new ControleVeiculos();
    }

    public void deletarCadServico() {
        this.cadServicosService.delete(this.cadServicos);
        this.msg.info("Removido com sucesso.");
        //listar();
        this.cadServicos = new CadServicos();
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

    public void novoCadServico() {
        this.cadServicos = new CadServicos();
        Util.updateComponente("formServicos");
        Util.executarAcao("PF('dgServico').show()");

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

    public void setarServicos(CadServicos cmv) {
        this.cadServicos = cmv;
        if (cmv.getVeiculo() == null) {
            Util.updateComponente("formServicos2");
            Util.executarAcao("PF('dgServico2').show()");
        } else {
            this.idCarro = cmv.getVeiculo();
            carregarVeiculo();
            Util.updateComponente("formServicos");
            Util.executarAcao("PF('dgServico').show()");
        }

    }

    public void setarDadosDeletar(ControleVeiculos cmv) {
        this.controleVeiculos = cmv;
        Util.updateComponente("forCmvConf");
        Util.executarAcao("PF('dlgConfCv').show()");
    }

    public void setarServicosDeletar(CadServicos cmv) {
        this.cadServicos = cmv;
        Util.updateComponente("formDeletarServico");
        Util.executarAcao("PF('dlgConfServico').show()");
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

    public String formatarV(String x) {

        if (x != null) {
            if (x.contains(".")) {
                x = x.replaceAll("\\.", "");
                System.err.println(x);
                StringBuilder stringBuilder = new StringBuilder(x);
                stringBuilder.insert(x.length() - 3, '.');
                return stringBuilder.toString();
            }
            return "0." + x;
        }
        return "0.000";

    }

    public void formatarVTS() {

        if (saidak != null) {
            saidak = saidak.replaceAll("\\.", "");
            saidak = saidak.replaceAll("\\,", "");
            StringBuilder stringBuilder = new StringBuilder(saidak);
            if (saidak.contains(".")) {
                int t = saidak.length();
                if (t > 3) {
                    stringBuilder.insert(saidak.length() - 3, '.');
                    t = stringBuilder.toString().length();
                }
                if (t > 7) {
                    stringBuilder.insert(saidak.length() - 7, '.');
                    t = stringBuilder.toString().length();
                }
                if (t > 11) {
                    stringBuilder.insert(saidak.length() - 11, '.');
                }
                saidak = stringBuilder.toString();
                controleVeiculos.setKmSaida(new BigDecimal(stringBuilder.toString()));
            } else {
                saidak = "0." + retornok;
                controleVeiculos.setKmSaida(new BigDecimal("0." + saidak));
            }
        }
        controleVeiculos.setKmSaida(new BigDecimal("0.000"));

    }

    public void formatarVTR() {
        if (retornok != null) {
            retornok = retornok.replaceAll("\\.", "");
            retornok = retornok.replaceAll("\\,", "");
            StringBuilder stringBuilder = new StringBuilder(retornok);
            int t = retornok.length();
            System.err.println(t);
            if (t > 3) {
                if (t > 3) {
                    stringBuilder.insert(retornok.length() - 3, '.');
                    t = stringBuilder.toString().length();
                }
                if (t > 7) {
                    stringBuilder.insert(retornok.length() - 7, '.');
                    t = stringBuilder.toString().length();
                }
                if (t > 11) {
                    stringBuilder.insert(retornok.length() - 11, '.');
                }
                retornok = stringBuilder.toString();
                controleVeiculos.setKmSaida(new BigDecimal(stringBuilder.toString()));
            } else {
                 retornok = "0." + retornok;
                controleVeiculos.setKmSaida(new BigDecimal("0." + retornok));
            }
        }
        controleVeiculos.setKmSaida(new BigDecimal("0.000"));

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

    public List<CadServicos> getListaCadServicos() {
        return listaCadServicos;
    }

    public void setListaCadServicos(List<CadServicos> listaCadServicos) {
        this.listaCadServicos = listaCadServicos;
    }

    public CadServicos getCadServicos() {
        return cadServicos;
    }

    public void setCadServicos(CadServicos cadServicos) {
        this.cadServicos = cadServicos;
    }

    public List<CadFornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public void setListaFornecedores(List<CadFornecedor> listaFornecedores) {
        this.listaFornecedores = listaFornecedores;
    }

    public List<String> getTipoSaida() {
        return tipoSaida;
    }

    public void setTipoSaida(List<String> tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    public String getSaidak() {
        return saidak;
    }

    public void setSaidak(String saidak) {
        this.saidak = saidak;
    }

    public String getRetornok() {
        return retornok;
    }

    public void setRetornok(String retornok) {
        this.retornok = retornok;
    }

}

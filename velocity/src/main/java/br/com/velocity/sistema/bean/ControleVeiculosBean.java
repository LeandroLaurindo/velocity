package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.entidades.CadServicos;
import br.com.velocity.sistema.entidades.ControleVeiculos;
import br.com.velocity.sistema.service.CadClienteService;
import br.com.velocity.sistema.service.CadModeloVeiculoService;
import br.com.velocity.sistema.service.CadServicosService;
import br.com.velocity.sistema.service.ControleVeiculosService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private CadCliente cadCliente;
    private List<String> tipoSaida;
    private String saidak;
    private String retornok;
    private String tipoDeAcao = "";
    private Date dataI;
    private Date dataF;
    private String mostrarDatas = "nao";

    private String vdiaria;

    private String vdesc;

    private String vacresc;
    
    private ControleVeiculos controleEditar;

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
        this.controleVeiculos = new ControleVeiculos();
        this.lista = new ArrayList<>();
        this.msg = new MessagesView();
        listar();

        listarVeiculos();
        listarClientes();
        novoAluguelR();

    }

    public void novoAluguelR() {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("valor").equalsIgnoreCase("abrir")) {
                Util.updateComponente("formPv");
                Util.executarAcao("PF('dlgPesquisarV').show()");
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void mostraData() {
        mostrarDatas = "";

        if (tipoDeAcao.equalsIgnoreCase("RESERVA")) {
            mostrarDatas = "sim";
        } else {
            mostrarDatas = "nao";
        }
        Util.updateComponente("formPv");
        Util.executarAcao("PF('dlgPesquisarV').show()");
    }

    public boolean valido(String campo) {
        return campo != null && !campo.isEmpty();
    }

    public void listar() {
        this.lista = service.findAll();
        Util.updateComponente("formCV");
    }

    public void listarVeiculos() {
        this.controleVeiculos = new ControleVeiculos();
        this.controleVeiculos.setAlugueloReserva(this.tipoDeAcao);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.listaModeloVeiculos = new ArrayList<>();
        List<CadModeloVeiculo> listaModeloVeiculosAux = veiculoService.findAll("ORDER BY c.modelo ASC");
        List<ControleVeiculos> listaAux = new ArrayList<>();
        String hqlVAluguel = "";
        if (this.tipoDeAcao.equalsIgnoreCase("RESERVA")) {
            if (dataI == null) {
                dataI = new Date();
            }
            if (dataI != null && dataF != null) {
                hqlVAluguel = " AND c.dataHoraPrevistaRetorno >= '" + df.format(dataI) + " 00:00:00' AND c.dataHoraPrevistaRetorno <= '" + df.format(dataF) + " 23:59:00'";
            } else {
                if (dataI != null) {
                    hqlVAluguel = " AND c.dataHoraPrevistaRetorno >= '" + df.format(dataI) + " 00:00:00'";
                } else {
                    if (dataF != null) {
                        hqlVAluguel = " AND c.dataHoraPrevistaRetorno <= '" + df.format(dataF) + " 00:00:00'";
                    }
                }
            }

            for (CadModeloVeiculo veiculoAux : listaModeloVeiculosAux) {

                this.listaCadServicos = this.cadServicosService.findAll("WHERE c.veiculo=" + veiculoAux.getIdModelo() + " AND c.situacao='Aberto'");
                listaAux = this.service.findAll("WHERE c.veiculoFk.idModelo=" + veiculoAux.getIdModelo() + "" + hqlVAluguel);
                boolean podeEntrar = true;
                for (CadServicos cs : listaCadServicos) {
                    if (cs.getVeiculo().intValue() == veiculoAux.getIdModelo().intValue()) {
                        podeEntrar = false;
                        break;
                    }
                }
                if (podeEntrar) {
                    for (ControleVeiculos cv : listaAux) {
                        if (cv.getVeiculoFk().getIdModelo().intValue() == veiculoAux.getIdModelo().intValue()) {
                            podeEntrar = false;
                            break;
                        }
                    }
                }

                if (podeEntrar) {
                    this.listaModeloVeiculos.add(veiculoAux);
                }
            }
        } else {
            if (this.tipoDeAcao.equalsIgnoreCase("ALUGUEL")) {
                if (dataI == null) {
                    dataI = new Date();
                }
                if (dataI != null && dataF != null) {
                    hqlVAluguel = " AND c.dataHoraPrevistaRetorno >= '" + df.format(dataI) + " 00:00:00' AND c.dataHoraPrevistaRetorno <= '" + df.format(dataF) + " 23:59:00'";
                } else {
                    if (dataI != null) {
                        hqlVAluguel = " AND c.dataHoraPrevistaRetorno >= '" + df.format(dataI) + " 00:00:00'";
                    } else {
                        if (dataF != null) {
                            hqlVAluguel = " AND c.dataHoraPrevistaRetorno <= '" + df.format(dataF) + " 00:00:00'";
                        }
                    }
                }
                List<CadModeloVeiculo> listav = new ArrayList<>();
                List<ControleVeiculos> listac = new ArrayList<>();
                listav = veiculoService.findAll("WHERE (c.disponivel is null OR c.disponivel=true) ORDER BY c.modelo ASC");
                for (CadModeloVeiculo cadMVeiculo : listav) {
                    boolean pode = true;
                    listac = this.service.findAll("WHERE c.veiculoFk.idModelo=" + cadMVeiculo.getIdModelo() + "" + hqlVAluguel);

                    if (listac.size() > 0) {
                        for (ControleVeiculos cVeiculos : listac) {
                            if (cVeiculos.getVeiculoFk().getIdModelo().intValue() == cadMVeiculo.getIdModelo().intValue()) {
                                pode = false;
                                break;
                            }
                        }
                    }

                    if (pode) {
                        this.listaModeloVeiculos.add(cadMVeiculo);
                    }
                }
            }
        }

        if (this.listaModeloVeiculos.size() > 0) {
            Util.executarAcao("PF('dlgPesquisarV').hide()");
            Util.updateComponente("formCadCV");
            Util.executarAcao("PF('dglVControle').show()");
        } else {
            this.msg.warn("No momento não veicilo disponivel!");
        }

    }

    public void listarClientes() {
        this.listaCadClientes = new ArrayList<>();
        this.listaCadClientes = this.clienteService.findAll(" WHERE c.documentoFk.pessoaFk.situacao ='SIM' ORDER BY c.documentoFk.pessoaFk.nome, c.documentoFk.pessoaFk.razaoSocial ASC");
    }

    public void salvar() {
        if (this.controleVeiculos.getIdControleVeiculo() == null) {
            //System.out.println("chamou medodo salvar");

            this.controleVeiculos.setValorDiaria(new BigDecimal(formVpBigDecimal(vdiaria)));
            this.controleVeiculos.setValorDesc(new BigDecimal(formVpBigDecimal(vdesc)));
            this.controleVeiculos.setValorAcresc(new BigDecimal(formVpBigDecimal(vacresc)));

            if (this.service.save(this.controleVeiculos)) {
                CadModeloVeiculo cmv = this.veiculoService.carregar(this.controleVeiculos.getVeiculoFk().getIdModelo());
                if (cmv != null) {
                    if (cmv.getIdModelo() != null) {
                        if (this.controleVeiculos.getAlugueloReserva().equalsIgnoreCase("ALUGUEL")) {
                            cmv.setDisponivel(false);
                            cmv.setMotivo(this.controleVeiculos.getAlugueloReserva());
                            this.veiculoService.update(cmv);
                        }
                    }
                }
                this.msg.info("Salvo com sucesso.");

                this.controleVeiculos = new ControleVeiculos();

                listar();
                Util.executarAcao("PF('dglVControle').hide()");
            } else {
                this.msg.warn("Não possivel salvar");
            }
        } else {
            if(controleEditar.getVeiculoFk().getIdModelo().intValue() != this.controleVeiculos.getIdControleVeiculo().intValue()){
              if(!controleEditar.getAlugueloReserva().equalsIgnoreCase(this.controleVeiculos.getAlugueloReserva())){
                  if(controleEditar.getAlugueloReserva().equalsIgnoreCase("ALUGUEL")){
                      CadModeloVeiculo cadModeloVeicul = this.veiculoService.carregar(controleEditar.getVeiculoFk().getIdModelo());
                      cadModeloVeicul.setModelo("");
                      cadModeloVeicul.setDisponivel(true);
                      this.veiculoService.update(cadModeloVeicul);
                  }
              }
               if(verificarVeiculo(this.controleVeiculos)){
                   this.editar();
               }else{
                   this.msg.warn(this.controleVeiculos.getVeiculoFk().getModelo() + " está indisponivel!");
               }
            }else{
            this.editar();
            }
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
        
        this.controleVeiculos.setValorDiaria(new BigDecimal(formVpBigDecimal(vdiaria)));
        this.controleVeiculos.setValorDesc(new BigDecimal(formVpBigDecimal(vdesc)));
        this.controleVeiculos.setValorAcresc(new BigDecimal(formVpBigDecimal(vacresc)));
        if (this.service.update(this.controleVeiculos)) {
            CadModeloVeiculo cmv = this.veiculoService.carregar(this.controleVeiculos.getVeiculoFk().getIdModelo());
            if (cmv != null) {
                if (cmv.getIdModelo() != null) {
                    if (this.controleVeiculos.getAlugueloReserva().equalsIgnoreCase("ALUGUEL")) {
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
            this.msg.info("Editado com sucesso.");
            listar();
            this.controleVeiculos = new ControleVeiculos();
            Util.executarAcao("PF('dglVControle').hide()");
        } else {
            this.msg.error("Não foi possivel salvar");
        }
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
        this.listaModeloVeiculos = veiculoService.findAll("ORDER BY c.modelo ASC");
        this.controleVeiculos = cmv;
        this.controleEditar = this.controleVeiculos;
        this.vdiaria = formVpBigDecimalPReal(controleVeiculos.getValorDiaria().toString());
        this.vdesc = formVpBigDecimalPReal(controleVeiculos.getValorDesc().toString());
        this.vacresc = formVpBigDecimalPReal(controleVeiculos.getValorAcresc().toString());
        Util.executarAcao("PF('dglVControle').show()");
        Util.updateComponente("formCadCV");
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

    public String formVpBigDecimal(String valor) {
        if (valor == null) {
            return "0.00";
        } else {
            if (valor.isEmpty()) {
                return "0.00";
            } else {
                valor = valor.replaceAll("\\.", "");
                valor = valor.replaceAll("\\,", ".");
                return valor;
            }
        }
    }

    public String formVpBigDecimalPReal(String valor) {
        if (valor == null) {
            return "0,00";
        } else {
            if (valor.isEmpty()) {
                return "0,00";
            } else {
                valor = valor.replaceAll("\\.", ",");
                StringBuilder stringBuilder = new StringBuilder(valor);
                if (valor.length() > 6) {
                    stringBuilder.insert(valor.length() - 6, '.');
                    valor = stringBuilder.toString();
                }
                if (valor.length() >= 10) {
                    stringBuilder.insert(valor.length() - 10, '.');
                    valor = stringBuilder.toString();
                }

                return valor;
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

    public String getTipoDeAcao() {
        return tipoDeAcao;
    }

    public void setTipoDeAcao(String tipoDeAcao) {
        this.tipoDeAcao = tipoDeAcao;
    }

    public Date getDataI() {
        return dataI;
    }

    public void setDataI(Date dataI) {
        this.dataI = dataI;
    }

    public Date getDataF() {
        return dataF;
    }

    public void setDataF(Date dataF) {
        this.dataF = dataF;
    }

    public String getMostrarDatas() {
        return mostrarDatas;
    }

    public void setMostrarDatas(String mostrarDatas) {
        this.mostrarDatas = mostrarDatas;
    }

    public String getVdiaria() {
        return vdiaria;
    }

    public void setVdiaria(String vdiaria) {
        this.vdiaria = vdiaria;
    }

    public String getVdesc() {
        return vdesc;
    }

    public void setVdesc(String vdesc) {
        this.vdesc = vdesc;
    }

    public String getVacresc() {
        return vacresc;
    }

    public void setVacresc(String vacresc) {
        this.vacresc = vacresc;
    }

    private boolean verificarVeiculo(ControleVeiculos controleVe) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        List<ControleVeiculos> lista1 = new ArrayList<>();
        lista1 = this.service.findAll("WHERE c.veiculoFk.idmodelo=" + controleVe.getVeiculoFk().getIdModelo() + " AND c.dataHoraPrevistaRetorno >= '" + df.format(controleVe.getDataHoraPrevistaRetorno()) + " 00:00:00' AND c.dataHoraPrevistaRetorno <= '" + df.format(controleVe.getDataHoraPrevistaRetorno()) + " 23:59:00'");
        if (lista1.size() > 0) {
            return false;
        } else {
            List<CadServicos> lista2 = new ArrayList<>();
            lista2 = this.cadServicosService.findAll("WHERE c.veiculo=" + controleVe.getVeiculoFk().getIdModelo() + " AND c.situacao='Aberto'");
            if (lista2.size() > 0) {
                return false;
            }
        }
        return true;
    }

}

package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadFornecedor;
import br.com.velocity.sistema.entidades.CadFuncionarios;
import br.com.velocity.sistema.entidades.CadHabilitacao;
import br.com.velocity.sistema.entidades.CadImagens;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.*;
import br.com.velocity.sistema.util.MessagesView;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean(name = "cadImagemBean")
@ViewScoped
public class CadImagemBean implements Serializable {

    private UploadedFile file;
    private CadImagens imagem;
    private CadImagensService service = new CadImagensService();
    private CadModeloVeiculoService veiculoService = new CadModeloVeiculoService();
    private CadClienteService clienteService = new CadClienteService();
    private CadFuncionariosService funcionariosService = new CadFuncionariosService();
    private UsuarioService usuarioService = new UsuarioService();
    private CadHabilitacaoService habilitacaoService = new CadHabilitacaoService();
    private CadFornecedorService fornecedorService = new CadFornecedorService();
    private CadCliente cliente = new CadCliente();
    private Usuario usuario = new Usuario();
    private CadModeloVeiculo veiculo = new CadModeloVeiculo();
    private CadFuncionarios funcionario = new CadFuncionarios();
    private String caminhoDaImagem = "";
    private List<Usuario> listaUsuario = new ArrayList<>();
    private List<CadCliente> listaClientes = new ArrayList<>();
    private List<CadFornecedor> listaFornecedores = new ArrayList<>();
    private List<CadModeloVeiculo> listaVeiculos = new ArrayList<>();
    private List<CadHabilitacao> listaHabilitacao = new ArrayList<>();
    private Integer idCliente;
    private Integer idFornecedor;
    private Integer idUsuario;
    private Integer idVeiculo;
    private Integer idHabilitacao;
    private Integer idFuncionario;
    private String idImagem;
    private CadImagens imagensAux = new CadImagens();
    private MessagesView msg = new MessagesView();

    @PostConstruct
    public void init() {
        this.imagem = new CadImagens();
        try {

            idImagem = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            if (null != idImagem) {
                if (idImagem.contains("nova")) {
                    if (idImagem.contains("fornecedor")) {
                        this.idFornecedor = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("cliente")) {
                        this.idCliente = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("habilitacao")) {
                        this.idHabilitacao = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("usuario")) {
                        this.idUsuario = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("funcionario")) {
                        this.idFuncionario = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("veiculo")) {
                        this.idVeiculo = Integer.parseInt(idImagem.substring(0, 1));
                    }
                } else {
                    this.imagensAux = this.service.carregar("WHERE c.nomeImagem='" + idImagem + "'");

                    if (idImagem.contains("fornecedor")) {
                        this.idFornecedor = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("cliente")) {
                        this.idCliente = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("habilitacao")) {
                        this.idHabilitacao = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("usuario")) {
                        this.idUsuario = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("funcionario")) {
                        this.idFuncionario = Integer.parseInt(idImagem.substring(0, 1));
                    }
                    if (idImagem.contains("veiculo")) {
                        this.idVeiculo = Integer.parseInt(idImagem.substring(0, 1));
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        this.listar();
    }

    public void listar() {
        this.listaFornecedores = fornecedorService.findAll(" ORDER BY c.documentoFk.pessoaFk.nome");
        this.listaClientes = clienteService.findAll(" ORDER BY c.documentoFk.pessoaFk.nome");
        this.listaVeiculos = veiculoService.findAll(" ORDER BY c.modelo");
        this.listaUsuario = usuarioService.findAll(" ORDER BY c.login");
        this.listaHabilitacao = habilitacaoService.findAll(" ORDER BY c.documentoFk.pessoaFk.nome");
    }

    public void salvar() {
        this.imagem = new CadImagens();
        if (idCliente != null) {
            this.imagem.setIdFk(idCliente);
            this.imagem.setNomeImagem(String.valueOf(idCliente) + "cliente");
            salvarImagem();

        }
        if (idUsuario != null) {
            this.imagem.setIdFk(idUsuario);
            this.imagem.setNomeImagem(String.valueOf(idUsuario) + "usuario");
            salvarImagem();
        }
        if (idVeiculo != null) {
            this.imagem.setIdFk(idVeiculo);
            this.imagem.setNomeImagem(String.valueOf(idVeiculo) + "veiculo");
            salvarImagem();
        }

        if (idHabilitacao != null) {
            this.imagem.setIdFk(idHabilitacao);
            this.imagem.setNomeImagem(String.valueOf(idHabilitacao) + "habilitacao");
            salvarImagem();
        }
        
         if (idFornecedor != null) {
            this.imagem.setIdFk(idFornecedor);
            this.imagem.setNomeImagem(String.valueOf(idFornecedor) + "fornecedor");
            salvarImagem();
        }

    }

    public void salvarImagem() {
        if (file != null) {

            try {
                if (imagensAux.getIdImagem() != null) {
                    this.service.delete(imagensAux);
                }
                this.imagem.setImagem(IOUtils.toByteArray(file.getInputstream()));
                this.imagem.setTipo(file.getContentType().substring(6, 9));
//                System.out.println(imagem.getNomeImagem());
//                System.out.println(imagem.getTipo());
//                System.out.println(imagem.getIdFk());
                this.service.save(imagem);
            } catch (Exception ex) {
                ex.printStackTrace();
                this.msg.error("Não foi possivel salvar a imagem");
            }

        } else {
            this.msg.warn("Escolha uma imagem");
        }
    }

    public void atualizar() {
        if (file != null) {
            try {
                this.imagem.setNomeImagem(file.getFileName());
                this.imagem.setImagem(file.getContents());
                this.imagem.setTipo(file.getContentType());
                this.service.update(imagem);
            } catch (Exception ex) {
                ex.printStackTrace();
                this.msg.error("Não foi possivel atualizar a imagem");
            }

        } else {
            this.msg.warn("Escolha uma imagem");
        }
    }

    public String buscarImagem(String nome) {
        try {
            CadImagens img = service.carregar(" WHERE c.nomeImagem='" + nome + "'");
            if (img.getIdImagem() != null) {

                Path path = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "resources/img");

                System.err.println(path);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                path = Paths.get(path.toRealPath() + "/" + img.getNomeImagem() + "." + img.getTipo());
                if (!Files.exists(path)) {
                    FileOutputStream fos = new FileOutputStream(path.toString());
                    fos.write(img.getImagem());
                    fos.close();
                }

                //imagemDoCarroSelecionado = "../temp/carro/"+carroSelecionado.getCodigo() + ".jpg";
                caminhoDaImagem = "/resources/img/" + img.getNomeImagem() + "." + img.getTipo();
                System.out.println(caminhoDaImagem);
            } else {

                caminhoDaImagem = null;

            }
        } catch (IOException e) {
            caminhoDaImagem = null;
        }
        return caminhoDaImagem;
    }

    public void deletar() {

    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public CadImagens getImagem() {
        return imagem;
    }

    public void setImagem(CadImagens imagem) {
        this.imagem = imagem;
    }

    public CadImagensService getService() {
        return service;
    }

    public void setService(CadImagensService service) {
        this.service = service;
    }

    public CadModeloVeiculoService getVeiculoService() {
        return veiculoService;
    }

    public void setVeiculoService(CadModeloVeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    public CadClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(CadClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public CadFuncionariosService getFuncionariosService() {
        return funcionariosService;
    }

    public void setFuncionariosService(CadFuncionariosService funcionariosService) {
        this.funcionariosService = funcionariosService;
    }

    public CadCliente getCliente() {
        return cliente;
    }

    public void setCliente(CadCliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CadModeloVeiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(CadModeloVeiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getCaminhoDaImagem() {
        return caminhoDaImagem;
    }

    public void setCaminhoDaImagem(String caminhoDaImagem) {
        this.caminhoDaImagem = caminhoDaImagem;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public List<CadCliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<CadCliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<CadModeloVeiculo> getListaVeiculos() {
        return listaVeiculos;
    }

    public void setListaVeiculos(List<CadModeloVeiculo> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }

    public CadFuncionarios getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(CadFuncionarios funcionario) {
        this.funcionario = funcionario;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public List<CadHabilitacao> getListaHabilitacao() {
        return listaHabilitacao;
    }

    public void setListaHabilitacao(List<CadHabilitacao> listaHabilitacao) {
        this.listaHabilitacao = listaHabilitacao;
    }

    public Integer getIdHabilitacao() {
        return idHabilitacao;
    }

    public void setIdHabilitacao(Integer idHabilitacao) {
        this.idHabilitacao = idHabilitacao;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public CadHabilitacaoService getHabilitacaoService() {
        return habilitacaoService;
    }

    public void setHabilitacaoService(CadHabilitacaoService habilitacaoService) {
        this.habilitacaoService = habilitacaoService;
    }

    public String getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(String idImagem) {
        this.idImagem = idImagem;
    }

    public CadImagens getImagensAux() {
        return imagensAux;
    }

    public void setImagensAux(CadImagens imagensAux) {
        this.imagensAux = imagensAux;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public List<CadFornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public void setListaFornecedores(List<CadFornecedor> listaFornecedores) {
        this.listaFornecedores = listaFornecedores;
    }

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
    
    
}

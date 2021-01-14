/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadDocumentos;
import br.com.velocity.sistema.entidades.CadEmail;
import br.com.velocity.sistema.entidades.CadEndereco;
import br.com.velocity.sistema.entidades.CadFornecedor;
import br.com.velocity.sistema.entidades.CadImagens;
import br.com.velocity.sistema.entidades.CadPessoa;
import br.com.velocity.sistema.entidades.CadTelefone;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.CadDocumentosService;
import br.com.velocity.sistema.service.CadEmailService;
import br.com.velocity.sistema.service.CadEnderecoService;
import br.com.velocity.sistema.service.CadFornecedorService;
import br.com.velocity.sistema.service.CadImagensService;
import br.com.velocity.sistema.service.CadPessoaService;
import br.com.velocity.sistema.service.CadTelefoneService;
import br.com.velocity.sistema.service.UsuarioService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.Util;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CadFornecedorBean implements Serializable {

    private CadDocumentos documentos;

    private CadTelefone telefone;

    private CadEmail email;

    private CadPessoa pessoa;

    private CadFornecedor fornecedor;

    private CadEndereco endereco;

    private List<CadFornecedor> listaFornecedores;

    private CadEmailService emailService = new CadEmailService();

    private CadTelefoneService telefoneService = new CadTelefoneService();

    private CadEnderecoService enderecoService = new CadEnderecoService();

    private CadDocumentosService documentosService = new CadDocumentosService();

    private CadFornecedorService fornecedorService = new CadFornecedorService();

    private CadPessoaService pessoaService = new CadPessoaService();

    private UsuarioService usuarioService = new UsuarioService();

    private MessagesView msg = new MessagesView();

    private String tipoPessoa = "";

    String caminhoDaImagem = "";

    @PostConstruct
    public void init() {
        this.documentos = new CadDocumentos();
        this.fornecedor = new CadFornecedor();
        this.email = new CadEmail();
        this.pessoa = new CadPessoa();
        this.telefone = new CadTelefone();
        this.endereco = new CadEndereco();
        this.listaFornecedores = new ArrayList<>();
        listarDados();
    }

    public void listarDados() {
        this.listaFornecedores = this.fornecedorService.findAll("where c.documentoFk.pessoaFk.situacao ='SIM' ORDER BY c.documentoFk.pessoaFk.nome, c.documentoFk.pessoaFk.razaoSocial ASC");
    }

    public void salvar() {
        boolean validacao = false;
        List<CadDocumentos> lista = new ArrayList<>();
        if (null != documentos.getCpf()) {
            if (!documentos.getCpf().isEmpty()) {
                lista = this.documentosService.findAll("where c.cpf ='" + this.documentos.getCpf() + "'");
                if (lista.size() > 0) {
                    validacao = true;
                }
            }
        }
        if (!validacao) {
            if (null != documentos.getCnpj()) {
                if (!documentos.getCnpj().isEmpty()) {
                    lista = this.documentosService.findAll("where c.cnpj ='" + this.documentos.getCnpj() + "'");
                    if (lista.size() > 0) {
                        validacao = true;
                    }
                }
            }
        }
        if (!validacao) {
            Calendar calendar = Calendar.getInstance();
            Usuario usuario = this.usuarioService.carregar(3);
            // System.out.println(usuario.getLogin());
            try {
                this.pessoa.setDataInsercao(calendar.getTime());
                this.pessoa.setDataAlteracao(calendar.getTime());
                this.pessoa.setUsuarioFk(usuario);
                if (this.tipoPessoa.equalsIgnoreCase("Fisica")) {
                    if (pessoa.getNome() != null) {
                        this.pessoa.setRazaoSocial(pessoa.getNome());
                    }
                } else {
                    if (pessoa.getRazaoSocial() != null) {
                        this.pessoa.setNome(pessoa.getRazaoSocial());
                    }
                }
                this.pessoaService.save(this.pessoa);
                this.documentos.setDataInsercao(calendar.getTime());
                this.documentos.setDataAlteracao(calendar.getTime());
                this.documentos.setUsuarioFk(usuario.getIdUsuario());
                this.documentos.setPessoaFk(this.pessoaService.carregar("where c.nome ='" + this.pessoa.getNome() + "'"));
                this.documentosService.save(this.documentos);
                if (!documentos.getCpf().isEmpty()) {
                    this.fornecedor.setDocumentoFk(this.documentosService.carregar("where c.cpf ='" + this.documentos.getCpf() + "'"));
                } else {
                    this.fornecedor.setDocumentoFk(this.documentosService.carregar("where c.cnpj ='" + this.documentos.getCnpj() + "'"));
                }
                this.fornecedor.setDataInsercao(calendar.getTime());
                this.fornecedor.setDataAlteracao(calendar.getTime());
                this.fornecedor.setUsuarioFk(usuario);
                this.fornecedorService.save(this.fornecedor);
                this.endereco.setDataAlteracao(calendar.getTime());
                this.endereco.setDataInsercao(calendar.getTime());
                this.endereco.setUsuarioFk(usuario);
                this.endereco.setDocumentoFk(this.fornecedor.getDocumentoFk());
                this.enderecoService.save(endereco);
                this.telefone.setDataAlteracao(calendar.getTime());
                this.telefone.setDataInsercao(calendar.getTime());
                this.telefone.setUsuarioFk(usuario);
                this.telefone.setDocumentoFk(this.fornecedor.getDocumentoFk());
                this.telefoneService.save(telefone);
                this.email.setDataAlteracao(calendar.getTime());
                this.email.setDataInsercao(calendar.getTime());
                this.email.setUsuarioFk(usuario);
                this.email.setDocumentoFk(this.fornecedor.getDocumentoFk());
                this.emailService.save(email);
                this.msg.info("Fornecedor inserido com sucesso.");
                listarDados();
                Util.executarAcao("PF('dlgFornecedores').hide()");
                Util.updateComponente("fortblforne");

            } catch (Exception ex) {
                ex.printStackTrace();
                this.msg.error("Não foi possivel inserir.");
            }
        } else {
            msg.warn("Este fornecedor já esta cadastrado!");

        }
    }

    public void exibirImagem(Integer id, String setar) {

        try {
            CadImagens imagem = new CadImagensService().carregar(" WHERE c.nomeImagem='" + String.valueOf(id) + "" + setar + "'");
            if (imagem.getIdImagem() != null) {

                Path path = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "resources/img");

                System.err.println(path);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                path = Paths.get(path.toRealPath() + "/" + imagem.getNomeImagem() + "." + imagem.getTipo());
                if (!Files.exists(path)) {
                    FileOutputStream fos = new FileOutputStream(path.toString());
                    fos.write(imagem.getImagem());
                    fos.close();
                }

                //imagemDoCarroSelecionado = "../temp/carro/"+carroSelecionado.getCodigo() + ".jpg";
                caminhoDaImagem = "/resources/img/" + imagem.getNomeImagem() + "." + imagem.getTipo();
                Util.executarAcao("PF('dlgImg').show()");
                Util.updateComponente("formImg");
            } else {

                caminhoDaImagem = null;

            }
        } catch (Exception e) {
            caminhoDaImagem = null;
            msg.warn("Não há imagem cadastrada!");
        }
    }

    public void editar() {
        System.out.println("chamou editar");
        try {
            this.pessoa.setDataAlteracao(new Date());
            if (this.tipoPessoa.equalsIgnoreCase("Fisica")) {
                if (pessoa.getNome() != null) {
                    this.pessoa.setRazaoSocial(pessoa.getNome());
                }
            } else {
                if (pessoa.getRazaoSocial() != null) {
                    this.pessoa.setNome(pessoa.getRazaoSocial());
                }
            }
            this.pessoaService.update(pessoa);
            this.documentos.setPessoaFk(pessoa);
            this.documentos.setDataAlteracao(new Date());
            this.documentosService.update(documentos);
            this.fornecedor.setDocumentoFk(documentos);
            this.fornecedorService.update(fornecedor);
            this.telefone.setDataAlteracao(new Date());
            this.telefone.setDocumentoFk(documentos);
            this.telefoneService.update(telefone);
            this.email.setDataAlteracao(new Date());
            this.email.setDocumentoFk(documentos);
            this.emailService.update(email);
            this.endereco.setDataAlteracao(new Date());
            this.endereco.setDocumentoFk(documentos);
            this.enderecoService.update(endereco);

            msg.info("Editado com sucesso.");
            Util.executarAcao("PF('dlgFornecedores').hide()");
            listarDados();
            Util.updateComponente("fortblforne");
        } catch (Exception ex) {
            msg.error("Não foi possivel editar");
        }
    }

    public void setarEditar(CadDocumentos documentos) {
        try {
            this.documentos = documentos;
            this.pessoa = this.pessoaService.carregar(documentos.getPessoaFk().getIdPessoa());
            this.tipoPessoa = this.pessoa.getTipoPessoa();

            this.fornecedor = this.fornecedorService.carregarPorParamentro("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");

            this.telefone = this.telefoneService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");

            this.email = this.emailService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");

            this.endereco = this.enderecoService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");
            //System.out.println(endereco.getDocumentoFk().getPessoaFk().getNome());
            Util.updateComponente("forFornecedor");
            Util.executarAcao("PF('dlgFornecedores').show()");

        } catch (Exception ex) {
            ex.printStackTrace();
            msg.error("Não foi possvel carregar o cliente.");
        }
    }

    public void setarDeletar(CadPessoa cp) {
        this.pessoa = cp;
        Util.executarAcao("PF('dlgConf').show()");
        Util.updateComponente("forUsuConf");
    }

    public void deletar(Integer id) {
        try {
            this.pessoaService.update(id);
            msg.info("Deletado com Sucesso.");
            this.listarDados();
            Util.updateComponente("tblClientes");
        } catch (Exception e) {
            msg.error("Não foi possivel deletar.");
        }

    }

    public void novo() {
        this.fornecedor = new CadFornecedor();
        this.telefone = new CadTelefone();
        this.email = new CadEmail();
        this.endereco = new CadEndereco();
        this.pessoa = new CadPessoa();
        this.pessoa.setTipoPessoa(tipoPessoa);
        this.documentos = new CadDocumentos();
        Util.executarAcao("PF('dlgTipoPessoa').hide()");
        Util.updateComponente("forFornecedor");
        Util.executarAcao("PF('dlgFornecedores').show()");

    }

    public void urlControleVeiculos(Integer id) {
        String nome = String.valueOf(id);
        Util.rediricionar("controleveiculos/controleVeiculos.xhtml?idc=" + nome);
    }
    
     public void urlControleServicos(Integer id) {
        String nome = String.valueOf(id);
        Util.rediricionar("servicos/controleServicos.xhtml?idFornecedor=" + nome);
    }

    public void urlHabilitacao(int id) {
        Util.rediricionar("habilitacao/lista.xhtml?id=" + id);
    }

    public void urlEndereco(int id) {
        Util.rediricionar("endereco/lista.xhtml?id=" + id);
    }

    public void urlEmail(int id) {
        Util.rediricionar("email/lista.xhtml?id=" + id);
    }

    public void urlImagem(Integer id) {
        String nome = String.valueOf(id);
        nome += "novafornecedor";
        Util.rediricionar("imagens/contoleImagensFornecedor.xhtml?id=" + nome);
    }

    public void urlEditaImagem(Integer id) {
        String nome = String.valueOf(id);
        nome += "fornecedor";
        Util.rediricionar("imagens/contoleImagensFornecedor.xhtml?id=" + nome);
    }

    public CadDocumentos getDocumentos() {
        return documentos;
    }

    public void setDocumentos(CadDocumentos documentos) {
        this.documentos = documentos;
    }

    public CadTelefone getTelefone() {
        return telefone;
    }

    public void setTelefone(CadTelefone telefone) {
        this.telefone = telefone;
    }

    public CadEmail getEmail() {
        return email;
    }

    public void setEmail(CadEmail email) {
        this.email = email;
    }

    public CadPessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(CadPessoa pessoa) {
        this.pessoa = pessoa;
    }

    public CadFornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(CadFornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public CadEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(CadEndereco endereco) {
        this.endereco = endereco;
    }

    public List<CadFornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public void setListaFornecedores(List<CadFornecedor> listaFornecedores) {
        this.listaFornecedores = listaFornecedores;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCaminhoDaImagem() {
        return caminhoDaImagem;
    }

    public void setCaminhoDaImagem(String caminhoDaImagem) {
        this.caminhoDaImagem = caminhoDaImagem;
    }

}

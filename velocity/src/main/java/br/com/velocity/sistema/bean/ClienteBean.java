/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadCliente;
import br.com.velocity.sistema.entidades.CadDocumentos;
import br.com.velocity.sistema.entidades.CadEmail;
import br.com.velocity.sistema.entidades.CadEndereco;
import br.com.velocity.sistema.entidades.CadHabilitacao;
import br.com.velocity.sistema.entidades.CadImagens;
import br.com.velocity.sistema.entidades.CadPessoa;
import br.com.velocity.sistema.entidades.CadTelefone;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.CadClienteService;
import br.com.velocity.sistema.service.CadDocumentosService;
import br.com.velocity.sistema.service.CadEmailService;
import br.com.velocity.sistema.service.CadEnderecoService;
import br.com.velocity.sistema.service.CadHabilitacaoService;
import br.com.velocity.sistema.service.CadImagensService;
import br.com.velocity.sistema.service.CadPessoaService;
import br.com.velocity.sistema.service.CadTelefoneService;
import br.com.velocity.sistema.service.UsuarioService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.SessionUtil;
import br.com.velocity.sistema.util.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean(name = "clienteBean")
@ViewScoped
public class ClienteBean implements Serializable {

    private CadDocumentos documentos;

    private CadTelefone telefone;

    private CadEmail email;

    private CadPessoa pessoa;

    private CadCliente cliente;

    private CadEndereco endereco;

    private List<CadCliente> listaClientes;

    private CadEmailService emailService = new CadEmailService();

    private CadTelefoneService telefoneService = new CadTelefoneService();

    private CadEnderecoService enderecoService = new CadEnderecoService();

    private CadDocumentosService documentosService = new CadDocumentosService();

    private CadClienteService clienteService = new CadClienteService();

    private CadPessoaService pessoaService = new CadPessoaService();

    private UsuarioService usuarioService = new UsuarioService();

    private CadHabilitacaoService habilitacaoService = new CadHabilitacaoService();

    private CadImagensService imagensService = new CadImagensService();

    private CadHabilitacao cadHabilitacao;

    private CadImagens imagem;

    private CadImagens imagensAux;

    private Integer idDocumento;

    private MessagesView msg = new MessagesView();

    private String tipoPessoa = "";
    
    private String tipoPessoaAux = "";

    private UploadedFile file;

    private String ddd;

    String caminhoDaImagem = "";

    @PostConstruct
    public void init() {
        this.documentos = new CadDocumentos();
        this.cliente = new CadCliente();
        this.email = new CadEmail();
        this.pessoa = new CadPessoa();
        this.telefone = new CadTelefone();
        this.endereco = new CadEndereco();
        this.listaClientes = new ArrayList<>();
        listarDados();

    }

    public void listarDados() {
        this.listaClientes = this.clienteService.findAll("where c.documentoFk.pessoaFk.situacao ='SIM' ORDER BY c.documentoFk.pessoaFk.nome, c.documentoFk.pessoaFk.razaoSocial ASC");
    }

    public void salvar() {

        String docValidacao = "";
        boolean evalido = false;
        try {
            if (this.pessoa.getTipoPessoa().equalsIgnoreCase("Fisica")) {
                if (!this.documentos.getCpf().isEmpty()) {
                    this.documentos.setCpf(limparFormatacoes(this.documentos.getCpf()));
                    docValidacao = this.documentos.getCpf();
                    evalido = true;
                }
            }
        } catch (Exception e) {
        }

        try {
            if (this.pessoa.getTipoPessoa().equalsIgnoreCase("Juridica")) {
                if (!this.documentos.getCnpj().isEmpty()) {
                    this.documentos.setCnpj(limparFormatacoes(this.documentos.getCnpj()));
                    docValidacao = this.documentos.getCnpj();
                    evalido = true;
                }
            }
        } catch (Exception e) {
        }
        if (evalido) {
            if (isCPF(docValidacao.trim()) || isCNPJ(docValidacao.trim())) {
                Calendar calendar = Calendar.getInstance();
                Usuario usuario = this.usuarioService.carregar(SessionUtil.getUser().getIdUsuario());
                //System.out.println(usuario.getLogin());
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
                    try {
                        CadPessoa cadPessoa = this.pessoaService.carregar("where c.nome ='" + this.pessoa.getNome() + "'");
                        if (cadPessoa.getSituacao().equalsIgnoreCase("NAO")) {
                            this.pessoaService.updateExcliur(cadPessoa.getIdPessoa(), "SIM");
                            cadPessoa = this.pessoaService.carregar("where c.nome ='" + this.pessoa.getNome() + "'");
                        }
                        this.documentos.setPessoaFk(cadPessoa);
                    } catch (NonUniqueResultException e) {
                        List<CadPessoa> lista2 = new ArrayList<>();
                        lista2 = this.pessoaService.findAll("where c.nome ='" + this.pessoa.getNome() + "'");
                        if (lista2.size() > 0) {
                            this.documentos.setPessoaFk(lista2.get(0));
                        }
                    } catch (NoResultException e) {
                        if (this.pessoaService.save(this.pessoa)) {
                            this.documentos.setPessoaFk(this.pessoaService.carregar("where c.nome ='" + this.pessoa.getNome() + "'"));
                        } else {
                            evalido = false;
                        }
                    }
                    if (evalido) {
                        this.documentos.setDataInsercao(calendar.getTime());
                        this.documentos.setDataAlteracao(calendar.getTime());
                        this.documentos.setUsuarioFk(usuario.getIdUsuario());
                        try {
                            CadDocumentos cadDocumentos = new CadDocumentos();
                            if (this.pessoa.getTipoPessoa().equalsIgnoreCase("Fisica")) {
                                cadDocumentos = this.documentosService.carregar("where c.cpf ='" + this.documentos.getCpf() + "'");
                            } else {
                                cadDocumentos = this.documentosService.carregar("where c.cnpj ='" + this.documentos.getCnpj() + "'");
                            }
                            this.cliente.setDocumentoFk(cadDocumentos);
                        } catch (NonUniqueResultException e) {
                            List<CadDocumentos> lista2 = new ArrayList<>();
                            if (this.pessoa.getTipoPessoa().equalsIgnoreCase("Fisica")) {
                                lista2 = this.documentosService.findAll("where c.cpf ='" + this.documentos.getCpf() + "'");
                            } else {
                                lista2 = this.documentosService.findAll("where c.cnpj ='" + this.documentos.getCnpj() + "'");
                            }
                            if (lista2.size() > 0) {
                                this.cliente.setDocumentoFk(lista2.get(0));
                            }
                        } catch (NoResultException e) {
                            if (this.documentosService.save(this.documentos)) {
                                if (this.pessoa.getTipoPessoa().equalsIgnoreCase("Fisica")) {
                                    this.cliente.setDocumentoFk(this.documentosService.carregar("where c.cpf ='" + this.documentos.getCpf() + "'"));
                                } else {
                                    this.cliente.setDocumentoFk(this.documentosService.carregar("where c.cnpj ='" + this.documentos.getCnpj() + "'"));
                                }
                            } else {
                                evalido = false;
                            }
                        }
                        if (evalido) {

                            if (this.clienteService.save(this.cliente)) {
                                this.endereco.setDataAlteracao(calendar.getTime());
                                this.endereco.setDataInsercao(calendar.getTime());
                                this.endereco.setUsuarioFk(usuario);
                                this.endereco.setDocumentoFk(this.cliente.getDocumentoFk());
                                try {
                                    CadEndereco cadEndereco = this.enderecoService.carregar("where c.logradouro ='" + this.endereco.getLogradouro() + "' AND c.documentoFk.cpf='" + this.endereco.getDocumentoFk().getCpf() + "'");
                                } catch (NonUniqueResultException e) {
                                } catch (NoResultException e) {
                                    this.endereco.setCep(limparFormatacoes(this.endereco.getCep()));
                                    if (!this.enderecoService.save(endereco)) {
                                        evalido = false;
                                    }
                                }
                                if (evalido) {
                                    this.telefone.setDataAlteracao(calendar.getTime());
                                    this.telefone.setDataInsercao(calendar.getTime());
                                    this.telefone.setUsuarioFk(usuario);
                                    this.telefone.setDocumentoFk(this.cliente.getDocumentoFk());
                                    try {
                                        this.telefone.setDdd(Integer.valueOf(limparFormatacoesDD(this.ddd)));
                                        CadTelefone telefone2 = this.telefoneService.carregar("where c.ddd=" + this.telefone.getDdd() + " and c.telefone='" + this.telefone.getTelefone() + "' and c.documentoFk.cpf='" + this.telefone.getDocumentoFk().getCpf() + "'");
                                    } catch (NonUniqueResultException e) {
                                    } catch (NoResultException e) {
                                        this.telefone.setTelefone(limparFormatacoes(this.telefone.getTelefone()));

                                        evalido = this.telefoneService.save(telefone);
                                    }
                                    if (evalido) {
                                        this.email.setDataAlteracao(calendar.getTime());
                                        this.email.setDataInsercao(calendar.getTime());
                                        this.email.setUsuarioFk(usuario);
                                        this.email.setDocumentoFk(this.cliente.getDocumentoFk());
                                        try {
                                            CadEmail cadEmail = this.emailService.carregar("where c.email='" + this.email.getEmail() + "' and c.documentoFk.cpf='" + this.email.getDocumentoFk().getCpf() + "'");
                                        } catch (NonUniqueResultException e) {
                                        } catch (NoResultException e) {
                                            evalido = this.emailService.save(email);
                                        }
                                        if (evalido) {
                                            this.msg.info("Cliente inserido com sucesso.");
                                            listarDados();
                                            Util.executarAcao("PF('dlgClientes').hide()");
                                            Util.updateComponente("fortblcli");
                                        } else {
                                            this.msg.error("Não foi possivel inserir email.");
                                        }
                                    } else {
                                        this.msg.error("Não foi possivel inserir telefone.");
                                    }
                                } else {
                                    this.msg.error("Não foi possivel inserir endereço.");
                                }
                            } else {
                                this.msg.error("Não foi possivel inserir  cliente");
                            }
                        } else {
                            this.msg.error("Não foi possivel inserir documentos.");
                        }
                    } else {
                        this.msg.error("Não foi possivel inserir pessoa.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    this.msg.error("Não foi possivel inserir.");
                }
            } else {
                msg.warn("O documento imformado e invalido!");
            }
        } else {
            msg.warn("O documento imformado e invalido!");
        }
    }

    public String limparFormatacoes(String dados) {
        if (dados != null) {
            dados = dados.replaceAll("\\.", "");
            dados = dados.replaceAll("\\-", "");
            dados = dados.replaceAll("\\(", "");
            dados = dados.replaceAll("\\/", "");
            dados = dados.replaceAll("\\)", "");
            return dados;
        } else {
            return "";
        }
    }

    public String limparFormatacoesDD(String dddL) {
        if (dddL != null) {
            if (ddd.isEmpty()) {
                return "0";
            } else {
                dddL = dddL.replaceAll("\\(", "");
                dddL = dddL.replaceAll("\\)", "");
                return dddL;
            }
        } else {
            return "0";
        }
    }

    public String buscarImagem(String nome) {
        try {
            CadImagens img = imagensService.carregar(" WHERE c.nomeImagem='" + nome + "'");
            if (img.getIdImagem() != null) {

                Path path = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "resources/img");

                //System.err.println(path);
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
                //System.out.println(caminhoDaImagem);
            } else {

                caminhoDaImagem = null;

            }
        } catch (Throwable e) {
            caminhoDaImagem = null;
        }
        return caminhoDaImagem;
    }

    public void exibirImagem(Integer id, String setar) {

        try {
            CadImagens imagemD = new CadImagensService().carregar(" WHERE c.nomeImagem='" + String.valueOf(id) + "" + setar + "'");
            if (imagemD.getIdImagem() != null) {

                Path path = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "resources/img");

                // System.err.println(path);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                path = Paths.get(path.toRealPath() + "/" + imagemD.getNomeImagem() + "." + imagemD.getTipo());
                if (!Files.exists(path)) {
                    FileOutputStream fos = new FileOutputStream(path.toString());
                    fos.write(imagemD.getImagem());
                    fos.close();
                }

                //imagemDoCarroSelecionado = "../temp/carro/"+carroSelecionado.getCodigo() + ".jpg";
                caminhoDaImagem = "/resources/img/" + imagemD.getNomeImagem() + "." + imagemD.getTipo();
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

    public void exibirImagemHabilitacao(Integer id) {

        try {
            this.cadHabilitacao = new CadHabilitacao();
            this.cadHabilitacao = this.habilitacaoService.carregarParametros("where c.documentoFk.idDocumentos =" + id + "");
            CadImagens imagem = new CadImagensService().carregar(" WHERE c.nomeImagem='" + this.cadHabilitacao.getIdHabilitacao().toString() + "habilitacao'");
            if (imagem.getIdImagem() != null) {

                Path path = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "resources/img");

                // System.err.println(path);
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
        } catch (Throwable e) {
            //  e.printStackTrace();
            caminhoDaImagem = null;
            msg.warn("Não há imagem cadastrada!");
        }
    }

    public void setarImagemH(Integer id) {
        this.cadHabilitacao = new CadHabilitacao();
        this.imagem = new CadImagens();
        this.imagensAux = new CadImagens();
        try {
            this.cadHabilitacao = this.habilitacaoService.carregarParametros("where c.documentoFk.idDocumentos =" + id + "");

        } catch (Throwable e) {
            this.msg.warn("Este cliente ainda não tem CNH cadastrada");
        }
        if (this.cadHabilitacao != null) {
            if (this.cadHabilitacao.getIdHabilitacao() != null) {
                this.imagem.setIdFk(id);
                this.imagem.setNomeImagem(String.valueOf(this.cadHabilitacao.getIdHabilitacao()) + "habilitacao");
                try {
                    this.imagensAux = new CadImagens();
                    imagensAux = this.imagensService.carregar("WHERE c.nomeImagem='" + String.valueOf(id) + "habilitacao'");
                } catch (Throwable e) {

                }
                Util.updateComponente("frmImagH");
                Util.executarAcao("PF('dlgImgHab').show()");
            }
        }
    }

    public void setarImagemCli(Integer id) {
        this.imagensAux = new CadImagens();
        this.imagem = new CadImagens();
        this.imagem.setIdFk(id);
        this.imagem.setNomeImagem(String.valueOf(id) + "cliente");
        try {
            imagensAux = this.imagensService.carregar("WHERE c.nomeImagem='" + String.valueOf(id) + "cliente'");
        } catch (Throwable e) {

        }
        Util.updateComponente("formImgCli");
        Util.executarAcao("PF('dlgImgCli').show()");
    }

    public void salvarImagem() {
        if (file != null) {

            try {
                if (imagensAux.getIdImagem() != null) {
                    if (this.imagensService.delete(imagensAux)) {
                        File f = new File("/resources/img/" + imagensAux.getNomeImagem() + "." + imagensAux.getTipo());
                        f.delete();
                    }
                }
                this.imagem.setImagem(IOUtils.toByteArray(file.getInputstream()));
                this.imagem.setTipo(file.getContentType().substring(6, 9));
                if (this.imagensService.save(imagem)) {
                    this.msg.info("Imagem inserida com sucesso!");
                } else {
                    this.msg.error("Não foi possivel salvar a imagem");
                }
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
                this.imagensService.update(imagem);
            } catch (Exception ex) {
                ex.printStackTrace();
                this.msg.error("Não foi possivel atualizar a imagem");
            }

        } else {
            this.msg.warn("Escolha uma imagem");
        }
    }

    public void editar() {
        System.out.println("chamou editar");
        try {
            this.pessoa.setDataAlteracao(new Date());
            if (this.tipoPessoa.equalsIgnoreCase("Fisica")) {
                if (pessoa.getNome() != null) {
                    this.pessoa.setRazaoSocial(pessoa.getNome());
                    this.documentos.setCpf(limparFormatacoes(this.documentos.getCpf()));
                }
            } else {
                if (pessoa.getRazaoSocial() != null) {
                    this.documentos.setCnpj(limparFormatacoes(this.documentos.getCnpj()));
                    this.pessoa.setNome(pessoa.getRazaoSocial());
                }
            }
            this.pessoaService.update(pessoa);
            this.documentos.setPessoaFk(pessoa);
            this.documentos.setDataAlteracao(new Date());
            this.documentosService.update(documentos);
            this.cliente.setDocumentoFk(documentos);
            this.clienteService.update(cliente);
            this.telefone.setDataAlteracao(new Date());
            this.telefone.setDocumentoFk(documentos);
            this.telefone.setTelefone(limparFormatacoes(this.telefone.getTelefone()));
            this.telefone.setDdd(Integer.valueOf(limparFormatacoesDD(this.ddd)));
            this.telefoneService.update(telefone);
            this.email.setDataAlteracao(new Date());
            this.email.setDocumentoFk(documentos);
            this.emailService.update(email);
            this.endereco.setDataAlteracao(new Date());
            this.endereco.setDocumentoFk(documentos);
            this.endereco.setCep(limparFormatacoes(this.endereco.getCep()));
            this.enderecoService.update(endereco);

            msg.info("Editado com sucesso.");
            Util.executarAcao("PF('dlgClientes').hide()");
            listarDados();
            Util.updateComponente("fortblcli");
        } catch (Exception ex) {
            ex.printStackTrace();
            msg.error("Não foi possivel editar");
        }
    }

    public void setarEditar(CadDocumentos documentos) {
        try {
            this.documentos = documentos;
            this.pessoa = this.pessoaService.carregar(documentos.getPessoaFk().getIdPessoa());
            this.tipoPessoa = this.pessoa.getTipoPessoa();

            this.cliente = this.clienteService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");

            try {
                this.telefone = this.telefoneService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");
                this.ddd = String.valueOf(this.telefone.getDdd());
            } catch (Exception e) {

            }
            try {
                this.email = this.emailService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");
            } catch (Exception e) {

            }
            try {
                this.endereco = this.enderecoService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");
            } catch (Exception e) {

            }
            //System.out.println(endereco.getDocumentoFk().getPessoaFk().getNome());
            Util.executarAcao("PF('dlgClientes').show()");
            Util.updateComponente("forCliente");

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
            this.pessoaService.updateExcliur(id, "NAO");
            msg.info("Deletado com Sucesso.");
            this.listarDados();
            Util.updateComponente("tblClientes");
        } catch (Exception e) {
            msg.error("Não foi possivel deletar.");
        }

    }

    public void novo() {
        this.cliente = new CadCliente();
        this.telefone = new CadTelefone();
        this.email = new CadEmail();
        this.endereco = new CadEndereco();
        this.pessoa = new CadPessoa();
        this.tipoPessoa = this.tipoPessoaAux;
        this.pessoa.setTipoPessoa(tipoPessoa);
        this.documentos = new CadDocumentos();
        this.tipoPessoaAux = "";
        Util.executarAcao("PF('dlgTipoPessoa').hide()");
        Util.updateComponente("forCliente");
        Util.executarAcao("PF('dlgClientes').show()");

    }

    public void salvarCadhabilitacao() {
        Date atual = new Date();
        try {
            if (cadHabilitacao.getValidade().getTime() > atual.getTime()) {
                //this.cadHabilitacao.setDocumentoFk(documentos);
                if (this.habilitacaoService.save(cadHabilitacao)) {
                    listarDados();
                    this.msg.info("Habilitação inserida com sucesso.");
                }
            } else {
                this.msg.info("Habilitação vencida.");
            }
            Util.executarAcao("PF('dlghab').hide()");
            Util.updateComponente("formHabilitacao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvarN() {
        Date atual = new Date();
        try {
            if (cadHabilitacao.getValidade().getTime() > atual.getTime()) {

                this.documentos = this.documentosService.carregar(idDocumento);
                this.cadHabilitacao.setDocumentoFk(documentos);
                if (this.habilitacaoService.save(cadHabilitacao)) {
                    listarDados();
                    this.msg.info("Habilitação inserida com sucesso.");
                }
            } else {
                this.msg.info("Habilitação vencida.");
            }
            Util.executarAcao("PF('dlghab2').hide()");
            Util.updateComponente("formHabilitacao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarHabilitacao() {
        try {
            Date atual = new Date();
            if (cadHabilitacao.getValidade().getTime() > atual.getTime()) {
                this.cadHabilitacao.setDocumentoFk(documentos);
                this.habilitacaoService.update(cadHabilitacao);
                this.msg.info("Habilitação editada com sucesso.");
                Util.executarAcao("PF('dlghab').hide()");
                Util.updateComponente("formHabilitacao");
            } else {
                this.msg.info("Habilitação vencida.");
            }

        } catch (Exception e) {
            this.msg.info("Não foi  possivel editar habilitação.");
        }
    }

    public void urlControleVeiculos(Integer id) {
        String nome = String.valueOf(id);
        Util.rediricionar("controleveiculos/controleVeiculos.xhtml?idc=" + nome);
    }

    public void urlHabilitacao(CadDocumentos cd) {
        this.cadHabilitacao = new CadHabilitacao();
        this.documentos = cd;
        this.cadHabilitacao.setDocumentoFk(documentos);
        Util.executarAcao("PF('dlghab').show()");
        Util.updateComponente("frmdadosH");
        //Util.rediricionar("habilitacao/lista.xhtml?id=" + id);
    }

    public void setarDadosEditar(CadDocumentos id) {
        this.documentos = new CadDocumentos();
        this.cadHabilitacao = new CadHabilitacao();
        try {
            this.cadHabilitacao = this.habilitacaoService.carregarParametros("where c.documentoFk.idDocumentos =" + id + "");
            this.documentos = this.cadHabilitacao.getDocumentoFk();
        } catch (NoResultException ex) {
            this.msg.error("Habilitação não encontrada.");
            Util.updateComponente("frmdadosH");
            Util.executarAcao("PF('dlghab').show()");
        }
    }

    public void urlEndereco(int id) {
        Util.rediricionar("endereco/lista.xhtml?id=" + id);
    }

    public void urlEmail(int id) {
        Util.rediricionar("email/lista.xhtml?id=" + id);
    }

    public void urlImagem(Integer id) {
        String nome = String.valueOf(id);
        nome += "novacliente";
        Util.rediricionar("imagens/contoleImagensClientes.xhtml?id=" + nome);
    }

    public void urlEditaImagem(Integer id) {
        String nome = String.valueOf(id);
        nome += "cliente";
        Util.rediricionar("imagens/contoleImagensClientes.xhtml?id=" + nome);
    }

    public static boolean isCNPJ(String CNPJ) {
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111")
                || CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333")
                || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
                || CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777")
                || CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999")
                || (CNPJ.length() != 14)) {
            return (false);
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }

// Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000")
                || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public List<CadCliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<CadCliente> listaClientes) {
        this.listaClientes = listaClientes;
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

    public CadCliente getCliente() {
        return cliente;
    }

    public void setCliente(CadCliente cliente) {
        this.cliente = cliente;
    }

    public CadEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(CadEndereco endereco) {
        this.endereco = endereco;
    }

    public CadEmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(CadEmailService emailService) {
        this.emailService = emailService;
    }

    public CadTelefoneService getTelefoneService() {
        return telefoneService;
    }

    public void setTelefoneService(CadTelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    public CadEnderecoService getEnderecoService() {
        return enderecoService;
    }

    public void setEnderecoService(CadEnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    public CadDocumentosService getDocumentosService() {
        return documentosService;
    }

    public void setDocumentosService(CadDocumentosService documentosService) {
        this.documentosService = documentosService;
    }

    public CadClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(CadClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public CadPessoaService getPessoaService() {
        return pessoaService;
    }

    public void setPessoaService(CadPessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public MessagesView getMsg() {
        return msg;
    }

    public void setMsg(MessagesView msg) {
        this.msg = msg;
    }

    public String getCaminhoDaImagem() {
        return caminhoDaImagem;
    }

    public void setCaminhoDaImagem(String caminhoDaImagem) {
        this.caminhoDaImagem = caminhoDaImagem;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public CadHabilitacao getCadHabilitacao() {
        return cadHabilitacao;
    }

    public void setCadHabilitacao(CadHabilitacao cadHabilitacao) {
        this.cadHabilitacao = cadHabilitacao;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public CadImagens getImagem() {
        return imagem;
    }

    public void setImagem(CadImagens imagem) {
        this.imagem = imagem;
    }

    public CadImagens getImagensAux() {
        return imagensAux;
    }

    public void setImagensAux(CadImagens imagensAux) {
        this.imagensAux = imagensAux;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTipoPessoaAux() {
        return tipoPessoaAux;
    }

    public void setTipoPessoaAux(String tipoPessoaAux) {
        this.tipoPessoaAux = tipoPessoaAux;
    }
  
}

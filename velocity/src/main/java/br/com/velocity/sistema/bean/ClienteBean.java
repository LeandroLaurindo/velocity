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
import br.com.velocity.sistema.entidades.CadImagens;
import br.com.velocity.sistema.entidades.CadPessoa;
import br.com.velocity.sistema.entidades.CadTelefone;
import br.com.velocity.sistema.entidades.Usuario;
import br.com.velocity.sistema.service.CadClienteService;
import br.com.velocity.sistema.service.CadDocumentosService;
import br.com.velocity.sistema.service.CadEmailService;
import br.com.velocity.sistema.service.CadEnderecoService;
import br.com.velocity.sistema.service.CadImagensService;
import br.com.velocity.sistema.service.CadPessoaService;
import br.com.velocity.sistema.service.CadTelefoneService;
import br.com.velocity.sistema.service.UsuarioService;
import br.com.velocity.sistema.util.MessagesView;
import br.com.velocity.sistema.util.Util;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private MessagesView msg = new MessagesView();

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
        boolean validacao = false;
        List<CadDocumentos> lista = new ArrayList<>();
        if (!documentos.getCpf().isEmpty()) {
            lista = this.documentosService.findAll("where c.cpf ='" + this.documentos.getCpf() + "'");
            if (lista.size() > 0) {
                validacao = true;
            }
        }
        if (!validacao) {
            if (!documentos.getCnpj().isEmpty()) {
                lista = this.documentosService.findAll("where c.cnpj ='" + this.documentos.getCnpj() + "'");
                if (lista.size() > 0) {
                    validacao = true;
                }
            }
        }
        if (!validacao) {
            Calendar calendar = Calendar.getInstance();
            Usuario usuario = this.usuarioService.carregar(3);
            System.out.println(usuario.getLogin());
            try {
                this.pessoa.setDataInsercao(calendar.getTime());
                this.pessoa.setDataAlteracao(calendar.getTime());
                this.pessoa.setUsuarioFk(usuario);
                this.pessoaService.save(this.pessoa);
                this.documentos.setDataInsercao(calendar.getTime());
                this.documentos.setDataAlteracao(calendar.getTime());
                this.documentos.setUsuarioFk(usuario.getIdUsuario());
                this.documentos.setPessoaFk(this.pessoaService.carregar("where c.nome ='" + this.pessoa.getNome() + "'"));
                this.documentosService.save(this.documentos);
                if (!documentos.getCpf().isEmpty()) {
                    this.cliente.setDocumentoFk(this.documentosService.carregar("where c.cpf ='" + this.documentos.getCpf() + "'"));
                } else {
                    this.cliente.setDocumentoFk(this.documentosService.carregar("where c.cnpj ='" + this.documentos.getCnpj() + "'"));
                }
                this.clienteService.save(this.cliente);
                this.endereco.setDataAlteracao(calendar.getTime());
                this.endereco.setDataInsercao(calendar.getTime());
                this.endereco.setUsuarioFk(usuario);
                this.endereco.setDocumentoFk(this.cliente.getDocumentoFk());
                this.enderecoService.save(endereco);
                this.telefone.setDataAlteracao(calendar.getTime());
                this.telefone.setDataInsercao(calendar.getTime());
                this.telefone.setUsuarioFk(usuario);
                this.telefone.setDocumentoFk(this.cliente.getDocumentoFk());
                this.telefoneService.save(telefone);
                this.email.setDataAlteracao(calendar.getTime());
                this.email.setDataInsercao(calendar.getTime());
                this.email.setUsuarioFk(usuario);
                this.email.setDocumentoFk(this.cliente.getDocumentoFk());
                this.emailService.save(email);
                this.msg.info("Cliente inserido com sucesso.");
                listarDados();
                Util.executarAcao("PF('dlgClientes').hide()");
                Util.updateComponente("fortblcli");

            } catch (Exception ex) {
                ex.printStackTrace();
                this.msg.error("Não foi possivel inserir.");
            }
        } else {
            msg.warn("Este cliente já esta cadastrado!");

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
        } catch (IOException e) {
            caminhoDaImagem = null;
        }
    }

    public void editar() {
        System.out.println("chamou editar");
        try {
            this.pessoa.setDataAlteracao(new Date());
            this.pessoaService.update(pessoa);
            this.documentos.setPessoaFk(pessoa);
            this.documentos.setDataAlteracao(new Date());
            this.documentosService.update(documentos);
            this.cliente.setDocumentoFk(documentos);
            this.clienteService.update(cliente);
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
            Util.executarAcao("PF('dlgClientes').hide()");
            listarDados();
            Util.updateComponente("fortblcli");
        } catch (Exception ex) {
            msg.error("Não foi possivel editar");
        }
    }

    public void setarEditar(CadDocumentos documentos) {
        try {
            this.documentos = documentos;
            this.pessoa = this.pessoaService.carregar(documentos.getPessoaFk().getIdPessoa());

            this.cliente = this.clienteService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");

            this.telefone = this.telefoneService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");

            this.email = this.emailService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");

            this.endereco = this.enderecoService.carregar("where c.documentoFk.idDocumentos =" + documentos.getIdDocumentos() + "");
            System.out.println(endereco.getDocumentoFk().getPessoaFk().getNome());
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
            this.pessoaService.update(id);
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
        this.documentos = new CadDocumentos();
        Util.executarAcao("PF('dlgClientes').show()");
        Util.updateComponente("forCliente");

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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

import br.com.velocity.sistema.entidades.CadImagens;
import br.com.velocity.sistema.entidades.CadModeloVeiculo;
import br.com.velocity.sistema.service.CadImagensService;
import br.com.velocity.sistema.service.CadModeloVeiculoService;
import br.com.velocity.sistema.util.Util;
import java.io.FileOutputStream;
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

/**
 *
 * @author Leandro Laurindo
 */
@ManagedBean
@ViewScoped
public class ControleVisaoTelaInicialBean implements Serializable {

    private List<CadModeloVeiculo> listaVeiculos;

    private List<CadModeloVeiculo> veiculosDisponiveis;

    private List<CadModeloVeiculo> veiculosIndisponiveis;

    private CadModeloVeiculoService veiculoService;
    private String motivoMsg = "";
    private String caminhoDaImagem = "";

    @PostConstruct
    public void init() {
        this.veiculoService = new CadModeloVeiculoService();
        listaV();
    }
    
    public void listaV(){
        this.veiculosDisponiveis = new ArrayList<>();
        this.veiculosIndisponiveis = new ArrayList<>();
        this.listaVeiculos = new ArrayList<>();
        this.listaVeiculos = this.veiculoService.findAll();
        for (CadModeloVeiculo veiculo : this.listaVeiculos) {
            if (veiculo.getDisponivel() == null) {
                veiculosDisponiveis.add(veiculo);
            } else {
                if (veiculo.getDisponivel()) {
                    veiculosDisponiveis.add(veiculo);
                } else {
                    veiculosIndisponiveis.add(veiculo);
                }
            }
        }
    }
     public String disponivelVeiculo(Boolean d){
         System.err.println(d);
         if(d == null){
             return "SIM";
         }else{
             if(d == true){
                 return "SIM";
             }else{
                return "NÂO";
             }
         }
     }
    
     public void mostrarMotivo(String cv){
        motivoMsg = "";
        try{
        
        if(cv!= null){
        this.motivoMsg = cv;
        }else{
            motivoMsg = "Não a dados para exibir!";
        }
        }catch(Exception e){
            motivoMsg = "Não a dados para exibir!"; 
            //e.printStackTrace();
        }
        Util.updateComponente("formMotivo");
        Util.executarAcao("PF('dlgMotivo').show()");
    }
     
     public String buscarImagem(Integer id) {
        try {
            CadImagens img = new CadImagensService().carregar(" WHERE c.nomeImagem='" + String.valueOf(id) + "veiculo'");
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
               // System.out.println(caminhoDaImagem);
            } else {

                caminhoDaImagem = null;

            }
        } catch (Throwable e) {
            caminhoDaImagem = null;
        }
        return caminhoDaImagem;
    }

    public List<CadModeloVeiculo> getVeiculosDisponiveis() {
        return veiculosDisponiveis;
    }

    public void setVeiculosDisponiveis(List<CadModeloVeiculo> veiculosDisponiveis) {
        this.veiculosDisponiveis = veiculosDisponiveis;
    }

    public List<CadModeloVeiculo> getVeiculosIndisponiveis() {
        return veiculosIndisponiveis;
    }

    public void setVeiculosIndisponiveis(List<CadModeloVeiculo> veiculosIndisponiveis) {
        this.veiculosIndisponiveis = veiculosIndisponiveis;
    }

    public String getMotivoMsg() {
        return motivoMsg;
    }

    public void setMotivoMsg(String motivoMsg) {
        this.motivoMsg = motivoMsg;
    }
  
}

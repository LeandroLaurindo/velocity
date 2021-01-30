/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.velocity.sistema.bean;

/**
 *
 * @author Felipe Rios
 */
public class TestarDoc {

    public TestarDoc() {
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
            return dados;
        }
    }

    /**
     * @param args the command line arguments
     */
    /* public static void main(String[] args) {
    
      System.err.println(ClienteBean.isCNPJ("00874954000130") && ClienteBean.isCNPJ("20874954000130"));
        TestarDoc td = new TestarDoc();
        System.err.println(td.limparFormatacoes("20.874.954/0001-30"));
        System.err.println(td.limparFormatacoes("071.046.756-71"));
        System.err.println(td.limparFormatacoes("(035)"));
        System.err.println(td.limparFormatacoes("(37270-000)"));
    }*/
}

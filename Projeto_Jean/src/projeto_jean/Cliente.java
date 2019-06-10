/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_jean;

import java.io.Serializable;

/**
 *
 * @author jeanp
 */
public class Cliente implements Serializable{
    protected String nome;
    protected String email;
    protected String telefone;
    protected int idade;
    protected String sessaoatual;
    protected String proximasessao;
    protected String foto;
    protected boolean check[];
    protected boolean fototiposcheck[];
    protected boolean tratamentoscheck[];
    protected int intervalosessao;

    public int getIntervalosessao() {
        return intervalosessao;
    }

    public void setIntervalosessao(int intervalosessao) {
        this.intervalosessao = intervalosessao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSessaoatual() {
        return sessaoatual;
    }

    public void setSessaoatual(String sessaoatual) {
        this.sessaoatual = sessaoatual;
    }

    public String getProximasessao() {
        return proximasessao;
    }

    public void setProximasessao(String proximasessao) {
        this.proximasessao = proximasessao;
    }

    public boolean[] getCheck() {
        return check;
    }

    public void setCheck(boolean[] check) {
        this.check = check;
    }

    public boolean[] getFototiposcheck() {
        return fototiposcheck;
    }

    public void setFototiposcheck(boolean[] fototiposcheck) {
        this.fototiposcheck = fototiposcheck;
    }
     public boolean[] getTratamentoscheck() {
        return tratamentoscheck;
    }

    public void setTratamentoscheck(boolean[] tratamentoscheck) {
        this.tratamentoscheck = tratamentoscheck;
    }
    public Cliente(String nome, String email, String telefone, int idade, String sessaoatual, String proximasessao, int intervalosessao) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.idade = idade;
        this.sessaoatual = sessaoatual;
        this.proximasessao = proximasessao;
        this.intervalosessao = idade;
        check = new boolean[11];
        fototiposcheck = new boolean[6];
        tratamentoscheck = new boolean[4];
    }

    public Cliente() {
        check = new boolean[11];
        fototiposcheck = new boolean[6];
        tratamentoscheck = new boolean[4];
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

   
    
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.tcc.ManagedBean;

import edu.br.model.dao.BibliotecarioDAO;
import edu.br.model.pojo.Bibliotecario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Alcivan
 */
@ManagedBean
public class BibliotecarioBean {

    private Bibliotecario bibliotecario;
    private BibliotecarioDAO bibliotecariodao;
    private List<Bibliotecario> listaSolicitacoes;

    public List<Bibliotecario> getListaSolicitacoes() {
        return listaSolicitacoes;
    }

    public void setListaSolicitacoes(List<Bibliotecario> listaSolicitacoes) {
        this.listaSolicitacoes = listaSolicitacoes;
    }
     
    public BibliotecarioBean() {
        this.bibliotecario = new Bibliotecario();
        pedidoListarSolicitaco();
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public BibliotecarioDAO getBibliotecariodao() {
        return bibliotecariodao;
    }

    public void setBibliotecariodao(BibliotecarioDAO bibliotecariodao) {
        this.bibliotecariodao = bibliotecariodao;
    }
    
    
    //chama o método que faz o login
    public String Logar(){
        System.out.println("entrou no md");
        try{
            System.out.println("ENTROU NO TRY");
            this.bibliotecariodao = new BibliotecarioDAO();
            this.bibliotecario = this.bibliotecariodao.getBibliotecario(this.bibliotecario);
            switch(this.bibliotecario.getPermissao()){
                case "ADMINISTRADOR":
            {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("Admin.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
                    
                case "BIBLIOTECARIO":
            {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("trabalhos.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
                    
                case "ALUNO":
            {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("formulario.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
                default:
            {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
            }
    }catch(ClassNotFoundException ex){
             Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesMessage message = new FacesMessage("Usuario ou Senha Invalidos!");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        return "index";
    }
    public void cadastro2(){
        try {
            this.bibliotecariodao = new BibliotecarioDAO();
            boolean chamasolicitacao = this.bibliotecariodao.solicitacao(bibliotecario);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }this.bibliotecario = new Bibliotecario();
 
    }
    public void redirect(){
        System.out.println("entrou no redirect");
        try {
            System.out.println("entrou no try");
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastro.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //chama o método que cadastra o bibliotecário ou administrador
    public void cadastro1(){
         System.out.println("entrou no cadastro");
        try {
            this.bibliotecariodao = new BibliotecarioDAO();
            boolean chamacadas = this.bibliotecariodao.cadastroBiblio(this.bibliotecario);
           
             
            System.out.println("boolean" + chamacadas);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }this.bibliotecario = new Bibliotecario();
       }
    public void ativar(Bibliotecario ativa){
        System.out.println("entrou no mb");
        try {
            this.bibliotecariodao = new BibliotecarioDAO();
           boolean corrige = this.bibliotecariodao.ativar(ativa);
           if(corrige){
            pedidoListarSolicitaco();
            this.bibliotecario = new Bibliotecario();
            this.bibliotecariodao = new BibliotecarioDAO();
           }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void clicarBotaoCadastraUsuario() {
        addMessage("Sucesso!", "Usuário cadastrado com sucesso.");
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     public void clicarBotaoPesquisa() {

    }
     //chama o método que seleciona o bibliotecário ou administrador
      public void selecionarUsuario(){
          System.out.println("entrou no mb");
          try{
              System.out.println("ENTROU NO TRY");
             
              this.bibliotecariodao = new BibliotecarioDAO();
              this.bibliotecario = this.bibliotecariodao.selecionaUsuario(this.bibliotecario);
              if(bibliotecario != null){
                  System.out.println("entrou no if");
                FacesMessage mensagem = new FacesMessage("Usuário encontrado"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
              }
              else{
                FacesMessage mensagem = new FacesMessage("Usuário não encontrado"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
              }
          }catch (ClassNotFoundException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      //chama o método que exclui o bibliotecário ou administrador
      public void excluiLogin(){
        System.out.println("entrou no método para excluir no mb " + this.bibliotecario.getMatricula());
        
        try {
            this.bibliotecariodao = new BibliotecarioDAO();
            boolean excluirLogin = this.bibliotecariodao.excluiLogin(this.bibliotecario);
            System.out.println("chamou método exclui no mb");
            if(excluirLogin){
                System.out.println("retornou pro mb como excluido");
                FacesMessage mensagem = new FacesMessage("Usuario excluído"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }else{
                FacesMessage mensagem = new FacesMessage("Falha na exclusão"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.bibliotecario = new Bibliotecario();
    }
      //chama o método que atualiza as informações do bibliotecário ou administrador
      public void alteraLogin(){
        System.out.println("entrou no método alterar do mb");
        try {
            
            this.bibliotecariodao = new BibliotecarioDAO();
            boolean alteraLogin = this.bibliotecariodao.atualizaLogin(this.bibliotecario);
            if(alteraLogin){
                FacesMessage mensagem = new FacesMessage("Usuario alterado"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }else{
                FacesMessage mensagem = new FacesMessage("Falha na alteração"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.bibliotecario = new Bibliotecario();
    }
      public void Limpar(){
          System.out.println("entru no mb");
         this.bibliotecario = new Bibliotecario();
        
         
         
      }
      public final void pedidoListarSolicitaco(){
        try {
            this.bibliotecariodao = new BibliotecarioDAO();
            this.listaSolicitacoes = this.bibliotecariodao.listaDeSolicitacoes();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      //esse é o método que saí da aplicação
      public void Logout(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
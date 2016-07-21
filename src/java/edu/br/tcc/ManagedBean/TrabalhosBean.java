/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.tcc.ManagedBean;



import edu.br.model.dao.FormularioDAO;
import edu.br.model.pojo.Formulario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alcivan
 */
@ManagedBean
@ViewScoped
public class TrabalhosBean {
    private List<Formulario> listaFormularioCorrigido;
    private List<Formulario> listaFormulario;
    
    private FormularioDAO formulariodao;
    private Formulario formulario;
    private String caminho ="../../web/corrigidos/";
    private String caminho2 = "http://localhost:8080/Sintacv1/faces/corrigidos/";
    private UploadedFile file;
    private StreamedContent filea;

    public StreamedContent getFilea() {
        return filea;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public List<Formulario> getListaFormularioCorrigido() {
        return listaFormularioCorrigido;
    }

    public void setListaFormularioCorrigido(List<Formulario> listaFormularioCorrigido) {
        this.listaFormularioCorrigido = listaFormularioCorrigido;
    }

    public FormularioDAO getFormulariodao() {
        return formulariodao;
    }

    public void setFormulariodao(FormularioDAO formulariodao) {
        this.formulariodao = formulariodao;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
    public TrabalhosBean() {
        this.formulario = new Formulario();
        pedidoListarTrabalho();
        pedidoListarTrabalhoCorrigido();
    }
    

    public List<Formulario> getListaFormulario() {
        return listaFormulario;
    }

    public void setListaFormulario(List<Formulario> listaFormulario) {
        this.listaFormulario = listaFormulario;
    }
    //chama o método que lista todos trabalhos não corrigidos 
    public final void pedidoListarTrabalho(){
        System.out.println("entrou no metodo mb");
        try {
            this.formulariodao = new FormularioDAO();
            this.listaFormulario = this.formulariodao.listaDeTrabalhos();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrabalhosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //chama o método que lista os trabalhos corrigidos
    public final void pedidoListarTrabalhoCorrigido(){
        System.out.println("entrou no metodo mb");
        try {
            this.formulariodao = new FormularioDAO();
            this.listaFormularioCorrigido = this.formulariodao.listaDeTrabalhosResolvidos();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrabalhosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //chama o método que corrige um formulário
    public void Corrigir(Formulario trabalho){
        System.out.println("entrou no método corrigir do mb");
        try {
            System.out.println("id: "+trabalho.getId_nome());
            this.formulariodao = new FormularioDAO();
            boolean corrige = this.formulariodao.atualizaFormulario(trabalho);
            if(corrige){
                this.formulariodao = new FormularioDAO();
                pedidoListarTrabalho();
                FacesMessage mensagem = new FacesMessage("Trabalho Corrigido"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }else{
                FacesMessage mensagem = new FacesMessage("Falha na Correção"); 
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(BibliotecarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //faz o envio de E-mails
    public Formulario enviarEmail(Formulario trabalho) {
         System.out.println("entrou no metodo do mb enviarEmail");
         try {
             System.out.println("teste: "+trabalho.getEmail());   
         
         Email emailSimples = new SimpleEmail();
            
         emailSimples.setHostName("smtp.live.com");
         emailSimples.setStartTLSEnabled(true);
         emailSimples.setSmtpPort(587);
         emailSimples.setDebug(true);
         emailSimples.setAuthenticator(new DefaultAuthenticator("Seu email outlook", "sua senha"));
         emailSimples.setFrom("Seu email outlook");
         emailSimples.setSubject(formulario.getAssunto());
         emailSimples.setMsg(formulario.getTexto()+"     " +caminho2+trabalho.getMatricula()+".docx");
         emailSimples.addTo(trabalho.getEmail());
         emailSimples.send();
         }catch(EmailException ex){
          //   System.out.println(""+ex);
             Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        return null;
           
    }
    

    
    //faz o upload de arquivos de um formulário para poder corrigir junto com o método Upload
    public void transferFile(String fileName , InputStream in){
        System.out.println("entrou no transferfile");
        try{
            OutputStream out = new FileOutputStream(new File(caminho + fileName));
            int reader = 0;
            System.out.println("tamanho: " +(int)getFile().getSize());
            byte [] bytes = new byte [(int)getFile().getSize()];
            while ((reader = in.read(bytes)) != -1){
                System.out.println("reader: " +reader);
                out.write(bytes, 0, reader);
            }
            in.close();
            out.flush();
            out.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    //faz o upload dos arquivos de um formulário para poder corrigir junto com o método transferFile
    public void Upload(){
        System.out.println("ENTROU NO UPLOAD");
        String extValidate;
        if(getFile()!= null){
        String ext=getFile().getFileName();
        if(ext != null){
            extValidate=ext.substring(ext.indexOf(".") +1);
        }else{
            extValidate = "null"; 
        }
        if(extValidate.equals("docx")||extValidate.equals("doc")){
            try{
                
                transferFile(getFile().getFileName(), getFile().getInputstream());
            }catch(IOException ex){
                   Logger.getLogger(TrabalhosBean.class.getName()).log(Level.SEVERE, null, ex);
                   
                   FacesContext context = FacesContext.getCurrentInstance();
                   context.addMessage(null, new FacesMessage("Erro","Erro no Upload de Arquivo"));
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", getFile().getFileName()));
            
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERRO","Por Favor Envie um Arquivo .docx ou .doc"));
        }
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERRO","Selecione o Arquivo!!!"));
        }
    }
    //faz o download dos arquivos
    public void Download(Formulario trabalho) throws FileNotFoundException{
        System.out.println("entrou no metodo download");
        InputStream stream = new FileInputStream("../../web/download/"+trabalho.getMatricula()+".docx" );
        filea = new DefaultStreamedContent(stream, "text/docx",  trabalho.getMatricula()+".docx" );
       if(filea != null){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Download Feito com Sucesso!"));
       }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro no Donwload!"));
       }
    }
}
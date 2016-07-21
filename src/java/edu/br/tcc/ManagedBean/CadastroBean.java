 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package edu.br.tcc.ManagedBean;

import edu.br.model.dao.FormularioDAO;
import edu.br.model.pojo.Formulario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author Alcivan
 */
@ManagedBean

public class CadastroBean{
   private Formulario formulario; 
   private FormularioDAO formulariodao;
   private String caminho ="../../web/download/";
   private UploadedFile file;

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }
    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public FormularioDAO getFormulariodao() {
        return formulariodao;
    }

    public void setFormulariodao(FormularioDAO formulariodao) {
        this.formulariodao = formulariodao;
    }
    public CadastroBean(){
        this.formulario = new Formulario();
    }
    //chama o método que cadastra um formulário
     public void Cadastro(){
         System.out.println("entrou no me");
        try {
            System.out.println("titulo "+this.formulario.getTitulo());
            this.formulariodao = new FormularioDAO();
            boolean chamacadas = this.formulariodao.cadastroFormu(this.formulario);
            uploadFormu();
             
            System.out.println("boolean" + chamacadas);
             try {
                 FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
             } catch (IOException ex) {
                 Logger.getLogger(CadastroBean.class.getName()).log(Level.SEVERE, null, ex);
             }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CadastroBean.class.getName()).log(Level.SEVERE, null, ex);
        }this.formulario = new Formulario();
       }
    

    
     //faz o upload de arquivos de um formulário junto com o método uploadFormu 
    public void transferFileFormu(String fileName , InputStream in){
        System.out.println("entrou no transferfile");
        try{
            String extencao = fileName.substring(fileName.indexOf("."));
            OutputStream out = new FileOutputStream(new File(caminho + formulario.getMatricula()+extencao));
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
    //faz o upload de arquivos de um formulário junto com o método transferFileFormu
    public void uploadFormu(){
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
                
                transferFileFormu(getFile().getFileName(), getFile().getInputstream());
            }catch(IOException ex){
                   Logger.getLogger(CadastroBean.class.getName()).log(Level.SEVERE, null, ex);
                   
                   FacesContext context = FacesContext.getCurrentInstance();
                   context.addMessage(null, new FacesMessage("Erro","Erro no Upload de Arquivo"));
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Enviado com Sucesso!"));
            
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERRO","Por Favor Envie um Arquivo .docx ou .doc"));
        }
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("ERRO","Selecione o Arquivo!!!"));
        }
    }
}
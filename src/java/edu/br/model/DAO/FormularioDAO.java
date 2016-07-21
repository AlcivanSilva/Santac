/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.br.model.dao;

import edu.br.model.conexao.ConexaoBd;
import edu.br.model.pojo.Bibliotecario;
import edu.br.model.pojo.Formulario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


/**
 *
 * @author Alcivan
 */
public class FormularioDAO {
    private Connection conexaobd;
    private Formulario formu;

    public Formulario getFormu() {
        return formu;
    }

    public void setFormu(Formulario formu) {
        this.formu = formu;
    }
    public FormularioDAO()throws ClassNotFoundException{
        this.conexaobd = new ConexaoBd().getConnection();
        System.out.println("Deu Tudo Certo!");
    }
    //faz o cadastro de formulário de normalização
    public boolean cadastroFormu(Formulario formulario){
        String bd = "INSERT INTO formulario(nome, matricula, email, telefone, titulo_proj, orientador,"
                + "curso, nivel_medio, nivel_superior, data_solicitacao, corrigido)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        System.out.println("ti"+formulario.getTitulo());
        try(PreparedStatement ps = conexaobd.prepareStatement(bd)){
            ps.setString(1,formulario.getUsuario());
            ps.setString(2,formulario.getMatricula());
            ps.setString(3,formulario.getEmail());
            ps.setString(4,formulario.getContato());
            ps.setString(5,formulario.getTitulo());
            ps.setString(6,formulario.getOrientador());
            ps.setString(7,formulario.getCurso());
            ps.setString(8,formulario.getMedio());
            ps.setString(9,formulario.getSuperior());
            ps.setDate(10, new Date(formulario.getData().getTime()));
            ps.setString(11, "nao");
            
          int testar  = ps.executeUpdate();
        if(testar == 1){
        return true;           
        
        }
        } catch(SQLException e){
            Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE,null,e);
    }finally{
            if(conexaobd!=null){
                try{
                    conexaobd.close();
                }catch(SQLException ex){
                     Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE,null,ex);
                }
                System.out.println("Desconectado!");
            }
        }
            return false;
     }
  
    //lista todos formulários não corrigidos em ordem decrescente
    public List<Formulario> listaDeTrabalhos(){
        String sqls = "SELECT * FROM formulario  WHERE corrigido = ?  ORDER BY id_nome DESC ";
         
        List<Formulario> lista = new ArrayList<>();
        try (PreparedStatement pst = conexaobd.prepareStatement(sqls)){
            pst.setString(1, "nao");
            ResultSet result = pst.executeQuery();
                while(result.next()){
                    Formulario formularionew = new Formulario();
                        formularionew.setUsuario(result.getString("nome"));
                        formularionew.setMatricula(result.getString("matricula"));
                        formularionew.setEmail(result.getString("email"));
                        formularionew.setContato(result.getString("telefone"));
                        formularionew.setTitulo(result.getString("titulo_proj"));
                        formularionew.setOrientador(result.getString("orientador"));
                        formularionew.setCurso(result.getString("curso"));
                        formularionew.setData(result.getDate("data_solicitacao"));
                        formularionew.setMedio(result.getString("nivel_medio"));
                        formularionew.setSuperior(result.getString("nivel_superior"));
                        formularionew.setId_nome(result.getInt("id_nome"));
                    
                    lista.add(formularionew);
                    System.out.println("era p sair o nome " + lista.get(0).getId_nome() + " !");
                }
                return lista;
        } catch (SQLException ex) {
            Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(conexaobd != null){
                try {
                    conexaobd.close();
                    System.out.println("Desconectado com banco!");
                } catch (SQLException ex) {
                    Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
        return null;
    }
    //lista todos trabalhos corrigidos
    public List<Formulario> listaDeTrabalhosResolvidos(){
        String sqls = "SELECT * FROM formulario WHERE corrigido = ? ";
         
        List<Formulario> lista = new ArrayList<>();
        try (PreparedStatement pst = conexaobd.prepareStatement(sqls)){
            pst.setString(1, "sim");
            ResultSet result = pst.executeQuery();
                while(result.next()){
                    Formulario formularionew = new Formulario();
                        formularionew.setUsuario(result.getString("nome"));
                        formularionew.setMatricula(result.getString("matricula"));
                        formularionew.setEmail(result.getString("email"));
                        formularionew.setContato(result.getString("telefone"));
                        formularionew.setTitulo(result.getString("titulo_proj"));
                        formularionew.setOrientador(result.getString("orientador"));
                        formularionew.setCurso(result.getString("curso"));
                        formularionew.setData(result.getDate("data_solicitacao"));
                        formularionew.setMedio(result.getString("nivel_medio"));
                        formularionew.setSuperior(result.getString("nivel_superior"));
              
                    lista.add(formularionew);
                    System.out.println("era p sair o nome " + lista.get(0).getUsuario() + " !");
                }
                return lista;
        } catch (SQLException ex) {
            Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(conexaobd != null){
                try {
                    conexaobd.close();
                    System.out.println("Desconectado com banco!");
                } catch (SQLException ex) {
                    Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
        return null;
    }
    //corrige um formulário
      public boolean atualizaFormulario(Formulario formulario){
        String sql = "UPDATE formulario SET corrigido = ? WHERE id_nome = ?";
                    
                try (PreparedStatement ps = conexaobd.prepareStatement(sql)){
                    ps.setString(1, "sim");
                    ps.setInt(2, formulario.getId_nome());
//                    ps.setString(3, formulario.getContato());
//                    ps.setString(4, formulario.getEmail());
//                    ps.setString(5, formulario.getTitulo());
//                    ps.setString(6, formulario.);
////                    ps.setString(7, bibliotecario.getMatricula());
//                    ps.setInt(7, bibliotecario.getIdUsuario());

                        int retornos = ps.executeUpdate();
                            if(retornos == 1){
                                return true;
                            }
                } catch (SQLException ex) {
                        Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    if(conexaobd != null){
                        try {
                            conexaobd.close();
                            System.out.println("Desconectado com banco!");
                        } catch (SQLException ex) {
                            Logger.getLogger(FormularioDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
       
        return false;
    }
}
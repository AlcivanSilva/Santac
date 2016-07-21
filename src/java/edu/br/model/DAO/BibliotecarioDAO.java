/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.model.dao;



import edu.br.model.conexao.ConexaoBd;
import edu.br.model.pojo.Bibliotecario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author 20131064110023
 */

public class BibliotecarioDAO {
private Connection cone; 
private Bibliotecario bibliotecario;
    public BibliotecarioDAO() throws ClassNotFoundException{
        //chama a classe de conexão com o banco de dados
        this.cone =  new ConexaoBd().getConnection();
        System.out.println("Conexão realizada com sucesso");
        bibliotecario = new Bibliotecario();
    }
    //faz o login
     public Bibliotecario getBibliotecario(Bibliotecario bibliotecarios){
         System.out.println("ENTROU NO DAO DO LOGIN");
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";
        try (PreparedStatement ps = cone.prepareStatement(sql)) {
            ps.setString(1, bibliotecarios.getMatricula());
            ps.setString(2, bibliotecarios.getSenha());
            ResultSet result = ps.executeQuery();
                if(result.next()){
                    Bibliotecario usuarioLogado = new Bibliotecario();
                    usuarioLogado.setMatricula(bibliotecarios.getMatricula());
                    usuarioLogado.setSenha(bibliotecarios.getSenha());
                    usuarioLogado.setPermissao(result.getString("permissao"));
                    usuarioLogado.setNomes(result.getString("nome"));
                    
                    return usuarioLogado;  
                }
        }catch(SQLException e){
            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            if(cone != null){
                try {
                    cone.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Desconectado!");
            }
        }
        return null;
    }
          public boolean cadastroBiblio(Bibliotecario bibliotecario){
        String bd = "INSERT INTO usuario (nome, usuario, email, contato, senha, permissao)"
                + "VALUES(?,?,?,?,?,?)";
        try(PreparedStatement ps = cone.prepareStatement(bd)){
            ps.setString(1,bibliotecario.getNome());
            ps.setString(2,bibliotecario.getMatricula());
            ps.setString(3,bibliotecario.getEmail());
            ps.setString(4,bibliotecario.getContato());
            ps.setString(5,bibliotecario.getSenha());
            ps.setString(6,bibliotecario.getPermissao());
            
            
            
            
            
            
          int testar  = ps.executeUpdate();
        if(testar == 1){
        return true;           
        
        }
        } catch(SQLException e){
            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE,null,e);
    }finally{
            if(cone!=null){
                try{
                    cone.close();
                }catch(SQLException ex){
                     Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE,null,ex);
                }
                System.out.println("Desconectado!");
            }
        }
            return false;
     }
     //faz o cadastro de bibliotecário e do administrador
     public boolean solicitacao (Bibliotecario bibliotecario){
        String bd = "INSERT INTO usuario (nome, usuario, email, contato, senha, permissao,ativo)"
                + "VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement ps = cone.prepareStatement(bd)){
            ps.setString(1,bibliotecario.getNome());
            ps.setString(2,bibliotecario.getMatricula());
            ps.setString(3,bibliotecario.getEmail());
            ps.setString(4,bibliotecario.getContato());
            ps.setString(5,bibliotecario.getSenha());
            ps.setString(6,bibliotecario.getPermissao());
            ps.setString(7, "nao");
            
            
            
            
            
          int testar  = ps.executeUpdate();
        if(testar == 1){
        return true;           
        
        }
        } catch(SQLException e){
            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE,null,e);
    }finally{
            if(cone!=null){
                try{
                    cone.close();
                }catch(SQLException ex){
                     Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE,null,ex);
                }
                System.out.println("Desconectado!");
            }
        }
            return false;
     }
       public List<Bibliotecario> listaDeSolicitacoes(){
        String sql = "SELECT * FROM usuario  WHERE ativo = ?  ORDER BY id_usuario DESC";
                
        List<Bibliotecario> lista = new ArrayList<>();
        try(PreparedStatement ps = cone.prepareStatement(sql)){
            ps.setString(1, "nao");
            ResultSet resul = ps.executeQuery();
            while (resul.next()) {
                Bibliotecario bibliotecarionew = new Bibliotecario();
                bibliotecarionew.setNome(resul.getString("nome"));
                bibliotecarionew.setMatricula(resul.getString("usuario"));
                bibliotecarionew.setContato(resul.getString("contato"));
                bibliotecarionew.setEmail(resul.getString("email"));
                bibliotecarionew.setPermissao(resul.getString("permissao"));
                bibliotecarionew.setIdUsuario(resul.getInt("id_usuario"));
                lista.add(bibliotecarionew);
                
            }
            return lista;
        }catch (SQLException ex) {
            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(cone != null){
                try {
                    cone.close();
                    System.out.println("Desconectado com banco!");
                } catch (SQLException ex) {
                    Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
        return null;
    }
    
    public boolean ativar(Bibliotecario bibliotecario){
        System.out.println("dao");
        String sql = "UPDATE usuario SET ativo = ? WHERE id_usuario = ?";
        try(PreparedStatement ps = cone.prepareStatement(sql)){
            ps.setString(1, "sim");
            ps.setInt(2, bibliotecario.getIdUsuario());
            System.out.println("ativou");
        }catch (SQLException ex) {
                        Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    if(cone != null){
                        try {
                            cone.close();
                            System.out.println("Desconectado com banco!");
                        } catch (SQLException ex) {
                            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
       
        return false;
    }
    
    //seleciona um administrador ou bibliotecário 
    public Bibliotecario selecionaUsuario(Bibliotecario bibliotecario){
        String sqls = "SELECT * FROM usuario WHERE usuario = ?";
         System.out.println("entrou no select");
        try (PreparedStatement pst = cone.prepareStatement(sqls)){
             System.out.println("ENTROU NO TRY2");
            pst.setString(1, bibliotecario.getMatricula());
            
            ResultSet result = pst.executeQuery();
                if(result.next()){
                    System.out.println("entrou no if2");
                    Bibliotecario usuarioEncontrado =  new Bibliotecario();
                    usuarioEncontrado.setNome(result.getString("nome"));
                    usuarioEncontrado.setMatricula(result.getString("usuario"));
                    usuarioEncontrado.setSenha(result.getString("senha"));
                    usuarioEncontrado.setEmail(result.getString("email"));
                    usuarioEncontrado.setContato(result.getString("contato"));
                    usuarioEncontrado.setPermissao(result.getString("permissao"));
                    usuarioEncontrado.setIdUsuario(result.getInt("id_usuario"));
                    
                    System.out.println("era p sair "+usuarioEncontrado.getNome()+", " + usuarioEncontrado.getMatricula() + ", " + usuarioEncontrado.getPermissao() );
                    
                    return usuarioEncontrado;
                }
        } catch (SQLException ex) {
            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(cone != null){
                try {
                    cone.close();
                    System.out.println("Desconectado com banco!");
                } catch (SQLException ex) {
                    Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
        return null;
    }
    //exclui um administrador ou biblotecário
    public boolean excluiLogin(Bibliotecario bibliotecario){
        String sql = "DELETE FROM login WHERE usuario = ?";
                    System.out.println("entrou no método pra excluir no dao");
            try (PreparedStatement ps = cone.prepareStatement(sql)){
                ps.setString(1, bibliotecario.getMatricula());
                System.out.println("entrou no ps do excluir no dao");
                int retorno = ps.executeUpdate();
                    System.out.println(retorno);
                        if(retorno == 1){
                            System.out.println("excluiu no dao");
                            return true;
                        }
            } catch (SQLException ex) {
                        Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                    if(cone != null){
                        try {
                            cone.close();
                            System.out.println("Desconectado com banco!");
                        } catch (SQLException ex) {
                            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
       
        return false;
    }
    //atualiza as informações de bibliotecário ou adminidtrador
    public boolean atualizaLogin(Bibliotecario bibliotecario){
        String sql = "UPDATE usuario SET usuario = ?, senha = ?, permissao = ?, nome = ?, contato = ?,"
                + " email = ? WHERE id_usuario = ?";
                    
                try (PreparedStatement ps = cone.prepareStatement(sql)){
                    ps.setString(1, bibliotecario.getMatricula());
                    ps.setString(2, bibliotecario.getSenha());
                    ps.setString(3, bibliotecario.getPermissao());
                    ps.setString(4,bibliotecario.getNome());
                    ps.setString(5, bibliotecario.getContato());
                    ps.setString(6, bibliotecario.getEmail());
                    ps.setInt(7, bibliotecario.getIdUsuario());

                        int retornos = ps.executeUpdate();
                            if(retornos == 1){
                                return true;
                            }
                } catch (SQLException ex) {
                        Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    if(cone != null){
                        try {
                            cone.close();
                            System.out.println("Desconectado com banco!");
                        } catch (SQLException ex) {
                            Logger.getLogger(BibliotecarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
       
        return false;
    }
    
    
}
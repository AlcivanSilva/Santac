/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.br.model.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alcivan
 */

//faz a conexão com o banco de dados
public class ConexaoBd {
    private String driver = "org.postgresql.Driver";
     private String url = "jdbc:postgresql://localhost/tcc", user = "postgres", password = "94415944";
     public Connection getConnection() throws ClassNotFoundException{
         try{
             Class.forName(driver);
             return DriverManager.getConnection(url,user,password);
         }catch(SQLException e){
             throw new RuntimeException(e);
         }
     }
}
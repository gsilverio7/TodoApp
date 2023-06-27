/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Project;
import util.ConnectionFactory;

/**
 *
 * @author biels
 */
public class ProjectController {
    
    public void save(Project project){
        String sql = "INSERT INTO projects (name, description, " + 
                "createdAt, updatedAt) " + 
                "VALUES(?,?,?,?)";

        Connection conexao = null;
        PreparedStatement statement = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar o projeto: " 
                    + ex.getMessage(), ex);        
        } finally {
            ConnectionFactory.closeConnection(conexao, statement);
        }
    }
    
    public void update(Project projeto) {
        String sql = "UPDATE projects SET "
                + "name = ?,"
                + "description = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        
        Connection conexao = null;
        PreparedStatement statement = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            statement.setString(1, projeto.getName());
            statement.setString(2, projeto.getDescription());
            statement.setDate(3, new Date(projeto.getUpdatedAt().getTime()));
            statement.setInt(4, projeto.getId());
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar o projeto: " 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conexao, statement);
        }
    }
    
    public void removeById(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection conexao = null;
        PreparedStatement statement = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar o projeto: " 
                    + ex.getMessage(), ex);        
        } finally {
            ConnectionFactory.closeConnection(conexao, statement);
        }
    }
    
    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Project> projects = new ArrayList<Project>();
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {                
                Project projeto = new Project();
                projeto.setId(resultSet.getInt("id"));
                projeto.setName(resultSet.getString("name"));
                projeto.setDescription(resultSet.getString("description"));
                projeto.setCreatedAt(resultSet.getDate("createdAt"));
                projeto.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(projeto);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao listar projetos: " 
                    + ex.getMessage(), ex);        
        } finally {
            ConnectionFactory.closeConnection(conexao, statement, resultSet);
        }
        return projects;
    }
}

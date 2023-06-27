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
import models.Task;
import util.ConnectionFactory;

/**
 *
 * @author biels
 */
public class TaskController {
    public void save(Task task){
        String sql = "INSERT INTO tasks (project_id, name, description, " + 
                "completed, notes, deadline, createdAt, updatedAt) " + 
                "VALUES(?,?,?,?,?,?,?,?)";

        Connection conexao = null;
        PreparedStatement statement = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));

            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa: " 
                    + ex.getMessage(), ex);        
        } finally {
            ConnectionFactory.closeConnection(conexao, statement);
        }
    }
    
    public void update(Task task) {
        String sql = "UPDATE tasks SET "
                + "project_id = ?,"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        
        Connection conexao = null;
        PreparedStatement statement = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(8, task.getId());
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa: " 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(conexao, statement);
        }
    }
    
    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection conexao = null;
        PreparedStatement statement = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao apagar a tarefa: " 
                    + ex.getMessage(), ex);        
        } finally {
            ConnectionFactory.closeConnection(conexao, statement);
        }
    }
    
    public List<Task> getAll(int projectId) {
        String sql = "SELECT * FROM tasks WHERE project_id = ?";
        
        Connection conexao = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            conexao = ConnectionFactory.getConnection();
            statement = conexao.prepareStatement(sql);
            statement.setInt(1, projectId);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {                
                Task tarefa = new Task();
                tarefa.setId(resultSet.getInt("id"));
                tarefa.setIdProject(resultSet.getInt("project_id"));
                tarefa.setName(resultSet.getString("name"));
                tarefa.setDescription(resultSet.getString("description"));
                tarefa.setIsCompleted(resultSet.getBoolean("completed"));
                tarefa.setNotes(resultSet.getString("notes"));
                tarefa.setDeadline(resultSet.getDate("deadline"));
                tarefa.setCreatedAt(resultSet.getDate("createdAt"));
                tarefa.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(tarefa);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao listar tarefas: " 
                    + ex.getMessage(), ex);        
        } finally {
            ConnectionFactory.closeConnection(conexao, statement, resultSet);
        }
        return tasks;
    }
}

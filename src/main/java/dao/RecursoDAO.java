package dao;

import dao.Conexao;
import model.Interesses;
import model.Recurso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecursoDAO {

    public void inserir(Recurso recurso) throws SQLException {
        String sql = "INSERT INTO recursos (titulo, autor, categoria_id, usuario_id, url, descricao) VALUES (?, ?, 1, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recurso.getTitulo());
            stmt.setString(2, recurso.getAutor());
            //stmt.setString(3, "1");
            stmt.setInt(3, recurso.getUsuarioId());
            stmt.setString(4, recurso.getUrl());
            stmt.setString(5, recurso.getDescricao());
            stmt.executeUpdate();
        }
    }

    public List<Recurso> listarPorCategoria(Interesses categoria) throws SQLException {
        List<Recurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM recursos WHERE categoria = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recurso r = new Recurso(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        //rs.getString("categoria_id"),
                        rs.getInt("usuario_id"),
                        rs.getString("url"),
                        rs.getString("descricao")
                );
                lista.add(r);
            }
        }
        return lista;
    }
            //to usando esse método pra fazer a pagina de posts!
    public List<Recurso> listarTodos(){
        List<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT * FROM recursos ORDER BY id DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recurso r = new Recurso();
                r.setId(rs.getInt("id"));
                r.setTitulo(rs.getString("titulo"));
                r.setAutor(rs.getString("autor"));
                r.setUsuarioId(rs.getInt("usuario_id"));
                r.setUrl(rs.getString("url"));
                r.setDescricao(rs.getString("descricao"));

                //tentei puxar as categorias mas n consegui nao manos
                /*try {
                    r.setCategoria(Interesses.valueOf(String.valueOf(rs.getInt("categoria_id"))));
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println("Categoria inválida no banco: " + rs.getString("categoria_id"));
                    // r.setCategoria(Interesses.OUTROS); //define um padrão
                }*/

                recursos.add(r);
            }

        }catch (SQLException e){
            System.out.println("Erro ao listar recursos: "+ e.getMessage());
            e.printStackTrace();
        }

        
        return recursos;
    }
 public void deletar(int idRecurso) throws SQLException {
        String sql = "DELETE FROM recursos WHERE id = ?";
        
        try (Connection conn = Conexao.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idRecurso);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar o recurso ID: " + idRecurso, e);
        }
    } 

}
package br.com.ivia.usuarioDAO;

import br.com.ivia.usuarioEntidades.Usuario;
import br.com.ivia.usuarioUtil.Conexoes;
import br.com.ivia.usuarioUtil.Exception.ErroSistema;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.NamedEvent;


@NamedEvent
@SessionScoped
@ManagedBean
public class UsuarioDAO implements Serializable{
    private static Object conn;
    private Usuario usuario;

    public void cadastar(Usuario usuario) throws ErroSistema {

        try {
            Connection conexao = Conexoes.getConexao();
            PreparedStatement ps;
            if (usuario.getId() == null) {

                ps = conexao.prepareStatement("INSERT INTO `usuarios` (`nome`,`email`,`senha`,`telefone`) VALUES(?,?,?,?);");
            } else {
                ps = conexao.prepareStatement("UPDATE usuarios set nome=?, email=?, senha=?, telefone=? where id=?;");
                ps.setInt(5, usuario.getId());
            }
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getTelefone());
            ps.execute();
            Conexoes.fecharConexao();

        } catch (SQLException ex) {
           throw new ErroSistema("Erro ao tenatar salvar usuário!",ex);
        }
    }
    
    public void excluir(Integer idUsuario) throws ErroSistema{
        try {
            Connection conexao = Conexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from usuarios where id=?");
            ps.setInt(1,idUsuario);
            ps.execute();
        } catch (SQLException ex) {
           throw new ErroSistema("Erro ao deletar o usuário!",ex);
        }
        
        
    }
    
    public List<Usuario> consultar() throws ErroSistema {

        try {
            Connection conexao = Conexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from usuarios;");
            ResultSet resultSet = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
                usuario.setTelefone(resultSet.getString("telefone"));
                usuarios.add(usuario);

            }
            Conexoes.fecharConexao();
            return usuarios;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar buscar usuário",ex);
        }

    }
    
    

     public  void  validaLoginSeguro(Usuario usuario){
        try {
            PreparedStatement ps;
            Connection conn = null;
            new Usuario();
            
            String sql = "select * from usuarios where nome =? and senha =?"; // declaração da cláusula sql
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getSenha());
            
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            
            usuario.setNome(rs.getString("login"));
            usuario.setNome(rs.getString("nome"));
            
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
		
        
        
		
	}
   


    


    
    
    
    
    
    
    
    
    
    
    
 
 


 

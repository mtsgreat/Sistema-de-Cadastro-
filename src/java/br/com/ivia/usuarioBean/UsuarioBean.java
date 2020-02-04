package br.com.ivia.usuarioBean;

import br.com.ivia.usuarioDAO.UsuarioDAO;
import br.com.ivia.usuarioEntidades.Usuario;
import br.com.ivia.usuarioUtil.Exception.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.NamedEvent;

@NamedEvent
@SessionScoped
@ManagedBean
public class UsuarioBean implements Serializable{

    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios = new ArrayList<>();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void incluirUsuario() {
        try {
            usuarios.add(usuario);
            usuarioDAO.cadastar(usuario);
            usuario = new Usuario();
            adicionarMensagem("Cadastrado", "Usuário cadastrado com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void consultarUsuarios() {
        try {
            
            usuarios = usuarioDAO.consultar();
            if (usuarios == null || usuarios.size() == 0) {
                adicionarMensagem("Nunhum dado encontrado!", "Lista vazia", FacesMessage.SEVERITY_WARN  );
            }
            
 
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    public void excluir(Usuario usuario) {
        try {
            usuarioDAO.excluir(usuario.getId());
            adicionarMensagem("Excluido", "Usuário excluido com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void alterar(Usuario edit) {
        usuario = edit;
    }

   
     public String logar(Usuario usuario) {
         
         usuarioDAO.validaLoginSeguro(usuario);

        if (usuario.getNome() != null && usuario.getSenha() != null) {
            
            return "/paginas/gerenciamento.xhtml?faces-redirect=true";
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario nao cadastrado", ""));
        return null;
    }
    
    
      public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        usuario = null;
        return "/index.xhtml?faces-redirect=true";
    }

   
    

    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }
    
  

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}

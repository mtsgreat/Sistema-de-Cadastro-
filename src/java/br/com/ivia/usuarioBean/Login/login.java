package br.com.ivia.usuarioBean.Login;

import br.com.ivia.usuarioEntidades.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.NamedEvent;

@NamedEvent
@SessionScoped
public class login implements Serializable {

    private String nome;
    private String senha;
    private Usuario usuario;

    public String logar() {
        // faz uma busca no banco

        if (nome.equals("w") && senha.equals("1")) {
            usuario = new Usuario();
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}

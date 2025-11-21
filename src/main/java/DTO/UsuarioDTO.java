package DTO;

import model.Usuario;

public class UsuarioDTO {
    private String nome;
    private String username;

    public UsuarioDTO(Usuario usuario) {
        this.nome= usuario.getNome();
        this.username= usuario.getUsername();
    }

    public String getNome(){return nome;};
    public String getEmail(){return username;};


    @Override
    public String toString(){
        return "Usuário: " + username + " (" + nome+")";
    }
}

package br.com.drytech;
import dao.Conexao;
import view.LoginFrame;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Conexao.iniciarBanco();
        System.out.println("Iniciando Banco...");

        try (Connection conexao = Conexao.getConnection()) {

            new LoginFrame(conexao).setVisible(true);
        } catch (Exception e) {
            System.out.println("ERRO:" + e.getMessage());
        }

    }
}


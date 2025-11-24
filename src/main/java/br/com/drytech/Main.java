package br.com.drytech;
import dao.Conexao;
import view.LoginUsuario;
import view.TelaInicial;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;


public class Main {
    public static void main(String[] args) {

        System.out.println("Iniciando Banco...");

        try (Connection conn = Conexao.getConnection()) {
            SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
        } catch (Exception e) {
            System.out.println("ERRO:" + e.getMessage());
        }


    }
}


package me.wolfe.cefcaixas.funcoes;

import me.wolfe.cefcaixas.CEFCaixas;

public class FuncoesManager {

    CEFCaixas Main;
    public static boolean ativado;


    public FuncoesManager(CEFCaixas main) {
        Main = main;
        setupFuncoes();
    }

    public void setupFuncoes() {
        ativado = Main.getConfig().getBoolean("Funcoes.ativado");
    }
}

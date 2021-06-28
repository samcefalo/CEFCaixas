package me.wolfe.cefcaixas.inventarios;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.inventarios.eventos.onClick;

public class InventarioManager {

    CEFCaixas Main;
    private InventarioCaixa inventarioCaixa;
    private InventarioMain inventarioMain;
    private InventarioSpin spin;


    public InventarioManager(CEFCaixas main) {
        Main = main;
        inventarioCaixa = new InventarioCaixa(main);
        inventarioMain = new InventarioMain(main);
        spin = new InventarioSpin(main);
        new onClick(main);
    }

    public InventarioCaixa getInventarioCaixa() {
        return inventarioCaixa;
    }

    public InventarioMain getInventarioMain() {
        return inventarioMain;
    }

    public InventarioSpin getSpin() {
        return spin;
    }
}

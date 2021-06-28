package me.wolfe.cefcaixas.manager;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.objetos.Caixa;
import me.wolfe.cefcaixas.objetos.Raridade;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

import static me.wolfe.cefcaixas.metodos.Metodos.f;

public class CaixasManager {

    CEFCaixas Main;
    public static ArrayList<Raridade> raridades = new ArrayList<Raridade>();
    public static ArrayList<Caixa> caixas = new ArrayList<Caixa>();

    public CaixasManager(CEFCaixas m) {
        Main = m;
    }

    public void reloadConfigs() {
        raridades.clear();
        caixas.clear();
        Main.getLoadManager().loadRaridade();
        Main.getLoadManager().loadCaixas();
    }

    public void darCaixa(Player p, Caixa caixa, int quantidade) {
        if (caixa == null) return;
        ItemStack itemStack = Main.getItemManager().getCaixa(caixa).clone();
        itemStack.setAmount(quantidade);
        HashMap<Integer, ItemStack> resto = p.getInventory().addItem(itemStack);
        for (ItemStack item : resto.values()) {
            p.getWorld().dropItemNaturally(p.getLocation(), item);
        }

        if (Main.getMetodos().isFull(p.getInventory())) {
            p.sendTitle(f("&cAlerta"), f("&fInventário Cheio"));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 0.8f);
        }

        p.sendMessage(f("&e[Caixas] &7Você recebeu " + quantidade + " Caixa(s) " + caixa.getNome() + "."));
    }

    public Raridade getRaridade(String nome) {
        return raridades.stream().filter(raridade -> raridade.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }

    public Caixa getCaixa(String nome) {
        return caixas.stream().filter(caixa -> caixa.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }
}

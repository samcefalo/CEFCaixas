package me.wolfe.cefcaixas.inventarios;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.manager.CaixasManager;
import me.wolfe.cefcaixas.objetos.Caixa;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static me.wolfe.cefcaixas.metodos.Metodos.f;

public class InventarioMain {

    CEFCaixas Main;

    public InventarioMain(CEFCaixas m) {
        Main = m;
    }

    public Inventory getCaixas(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, f("Caixas"));
        for (Caixa caixa : CaixasManager.caixas) {
            if (!caixa.isMenu()) continue;
            ItemStack is = Main.getItemManager().getCaixa(caixa).clone();
            ArrayList<String> lore = new ArrayList<>();
            ItemMeta im = is.getItemMeta();
            if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
                lore = (ArrayList<String>) is.getItemMeta().getLore();
            }
            if (!jaTemLore(lore)) {
                if (caixa.isPermissao() && !player.hasPermission(caixa.getPermissao())) {
                    lore.add(f(caixa.getNopermMSG()));
                }
                lore.add(f(""));
                lore.add(f("&7Botão Esquerdo abre o prever"));
                im.setLore(lore);
                is.setItemMeta(im);
            }
            inv.setItem(caixa.getSlot(), is);
        }
        return inv;
    }

    private boolean jaTemLore(ArrayList<String> Lore) {
        for (String linha : Lore) {
            if (linha.contains("Botão Esquerdo")) return true;
        }
        return false;
    }
}

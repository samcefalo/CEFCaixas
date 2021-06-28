package me.wolfe.cefcaixas.inventarios;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.objetos.Caixa;
import me.wolfe.cefcaixas.objetos.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static me.wolfe.cefcaixas.metodos.Metodos.f;


public class InventarioCaixa {

    CEFCaixas Main;

    public InventarioCaixa(CEFCaixas main) {
        Main = main;
    }


    public void OrdenarLista(Caixa caixa) {
        Collections.sort(caixa.getItems(), Comparator.comparing(Item::getChance));
        Collections.reverse(caixa.getItems());
    }

    public Inventory getPreview(Caixa caixa) {
        OrdenarLista(caixa);
        Inventory inv = Bukkit.createInventory(null, 54, f("Prever - " + caixa.getNome()));
        Bukkit.getScheduler().runTaskAsynchronously(Main, new Runnable() {
            @Override
            public void run() {
                int slot = 10;
                for (Item item : caixa.getItems()) {
                    switch (slot) {
                        case 17:
                            slot = 19;
                            break;
                        case 26:
                            slot = 28;
                            break;
                        case 35:
                            slot = 37;
                            break;
                    }
                    ItemStack is = item.getItem().clone();
                    ItemMeta im = is.getItemMeta();
                    ArrayList<String> lore = new ArrayList<String>();
                    if (is.hasItemMeta() && im.hasLore()) {
                        lore = (ArrayList<String>) im.getLore();
                    }
                    if (!jaTemLore(lore)) {
                        lore.add(f(""));
                        lore.add(f("&7Chance: &e" + item.getChance() + "%"));
                        im.setLore(lore);
                    }
                    im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    is.setItemMeta(im);
                    inv.setItem(slot, is);
                    slot++;
                }
                inv.setItem(4, Main.getItemManager().getCaixa(caixa));
                inv.setItem(49, voltar());
            }
        });
        return inv;
    }

    public Inventory getConfirmacao(Caixa caixa) {
        Inventory inv = Bukkit.createInventory(null, 27, f("Abrir - " + caixa.getNome()));
        Bukkit.getScheduler().runTaskAsynchronously(Main, new Runnable() {
            @Override
            public void run() {
                inv.setItem(13, Main.getItemManager().getCaixa(caixa));
                inv.setItem(11, confirmar());
                inv.setItem(15, cancelar());
            }
        });
        return inv;
    }

    private ItemStack confirmar() {
        ItemStack confirmar = new ItemStack(Material.LIME_WOOL, 1);
        ItemMeta meta = confirmar.getItemMeta();
        meta.setDisplayName(f("&aConfirmar"));
        ArrayList<String> lore = new ArrayList();
        lore.add(f("&7Clique para confirmar."));
        meta.setLore(lore);
        confirmar.setItemMeta(meta);
        return confirmar;
    }

    private ItemStack cancelar() {
        ItemStack confirmar = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta meta = confirmar.getItemMeta();
        meta.setDisplayName(f("&4Cancelar"));
        ArrayList<String> lore = new ArrayList();
        lore.add(f("&7Clique para cancelar."));
        meta.setLore(lore);
        confirmar.setItemMeta(meta);
        return confirmar;
    }

    private ItemStack voltar() {
        ItemStack vidroAmarelo = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = vidroAmarelo.getItemMeta();
        meta.setDisplayName(f("&aVoltar"));
        vidroAmarelo.setItemMeta(meta);
        return vidroAmarelo;
    }


    private boolean jaTemLore(ArrayList<String> Lore) {
        for (String linha : Lore) {
            if (linha.contains("Chance:")) return true;
        }
        return false;
    }


}

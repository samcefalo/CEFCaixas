package me.wolfe.cefcaixas.objetos;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.wolfe.cefcaixas.metodos.Metodos.random;

public class Caixa {
    String nome, permissao, noperm;
    int slot;
    boolean menu;
    ItemStack itemStack;
    ArrayList<Item> items = new ArrayList<>();

    public Caixa(String nome, int slot, ItemStack itemStack, boolean preview, String permissao, String noperm) {
        this.nome = nome;
        this.slot = slot;
        this.itemStack = itemStack;
        this.permissao = permissao;
        this.noperm = noperm;
        this.menu = preview;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public int getSlot() {
        return slot;
    }

    public boolean isMenu() {
        return menu;
    }

    public String getNopermMSG() {
        return noperm;
    }

    public boolean isPermissao() {
        if (permissao.equalsIgnoreCase("null") || permissao.length() < 3) {
            return false;
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public List<Item> getItemsChance() {
        List<Item> chances = new ArrayList<>();
        for (Item iten : items) {
            for (int i = 0; i < iten.getChance(); i++) {
                chances.add((Item) iten.clone());
            }
        }
        Collections.shuffle(chances);
        return chances;
    }

    public Item getItem(ItemStack itemStack) {
        return items.stream().filter(item -> item.getItem().isSimilar(itemStack)).findFirst().orElse(null);
    }

    public Item getItemRandom() {
        return getItemsChance().get((int) random(0, getItemsChance().size()));
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getPermissao() {
        return permissao;
    }
}

package me.wolfe.cefcaixas.objetos;

import me.wolfe.cefcaixas.objetos.enums.Tipo;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Item implements Cloneable {

    ItemStack item;
    Tipo tipo;
    List<String> comando;
    int chance;
    Raridade raridade;

    public Item(ItemStack item, List<String> comando, int chance, Tipo tipo, Raridade raridade) {
        this.chance = chance;
        this.comando = comando;
        this.tipo = tipo;
        this.item = item;
        this.raridade = raridade;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getChance() {
        return chance;
    }

    public List<String> getComandos() {
        return comando;
    }

    public Tipo getTipo() {
        return tipo;
    }
}

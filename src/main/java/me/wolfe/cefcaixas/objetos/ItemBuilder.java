package me.wolfe.cefcaixas.objetos;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.wolfe.cefcaixas.metodos.Metodos.f;
import static me.wolfe.cefcaixas.metodos.Metodos.log;

public class ItemBuilder {

    ItemStack itemStack;

    public ItemBuilder(Material material, int quantidade) {
        this.itemStack = new ItemStack(material, quantidade);

    }

    public ItemBuilder setDisplayNome(String displaynome) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(f(displaynome));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setGlow(boolean glow) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (glow) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.DURABILITY, 2, true);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantments(String s) {
        if (s.equalsIgnoreCase("null") || s.length() < 4) {
            return this;
        }
        Map<Enchantment, Integer> encants = new HashMap<>();
        if (s.contains(";")) {
            for (String split : s.split(";")) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(split.split("-")[0].toLowerCase()));
                if (enchantment == null) {
                    log(f("&c[Caixas] Encantamento '" + split.split("-")[0] + "' não existe"));
                    continue;
                }
                encants.put(enchantment, Integer.valueOf(split.split("-")[1]));
            }
        } else {
            if (Integer.valueOf(s.split("-")[1]) != 0) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(s.split("-")[0].toLowerCase()));
                if (enchantment == null) {
                    log(f("&c[Caixas] Encantamento '" + s.split("-")[0] + "' não existe"));
                    return this;
                }
                encants.put(enchantment, Integer.valueOf(s.split("-")[1]));
            }
        }
        itemStack.addUnsafeEnchantments(encants);
        return this;
    }

    public ItemStack toItemStack() {
        return itemStack;
    }

}

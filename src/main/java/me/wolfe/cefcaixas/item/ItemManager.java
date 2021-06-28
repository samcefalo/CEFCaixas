package me.wolfe.cefcaixas.item;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.objetos.Caixa;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemManager {
    CEFCaixas Main;
    private NamespacedKey caixa_id;

    public ItemManager(CEFCaixas main) {
        Main = main;
        caixa_id = new NamespacedKey(Main, "cefcaixa-uuid");
    }

    public Caixa getCaixa(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer tagContainer = itemMeta.getPersistentDataContainer();
        if (!tagContainer.has(caixa_id, PersistentDataType.STRING)) return null;
        String uuid = tagContainer.get(caixa_id, PersistentDataType.STRING);
        return Main.getCaixasManager().getCaixa(uuid);
    }

    public ItemStack getCaixa(Caixa caixa) {
        ItemStack itemStack = caixa.getItemStack().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer tagContainer = itemMeta.getPersistentDataContainer();
        itemMeta.getPersistentDataContainer().set(caixa_id, PersistentDataType.STRING, caixa.getNome());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

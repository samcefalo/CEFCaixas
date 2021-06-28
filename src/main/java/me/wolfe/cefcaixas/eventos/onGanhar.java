package me.wolfe.cefcaixas.eventos;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.newevents.GanharItemCaixaEvent;
import me.wolfe.cefcaixas.objetos.Item;
import me.wolfe.cefcaixas.objetos.Raridade;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static me.wolfe.cefcaixas.metodos.Metodos.f;

public class onGanhar implements Listener {

    CEFCaixas Main;

    public onGanhar(CEFCaixas m) {
        Main = m;
        Bukkit.getPluginManager().registerEvents(this, m);
    }

    @EventHandler
    public void onGanha(GanharItemCaixaEvent e) {
        Item i = e.getItem();
        Raridade r = i.getRaridade();
        Color cor = r.getColor();
        Main.getMetodos().fw(e.getLoc(), cor, cor, 1);
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.8f);
        if (r.isAnunciar()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("");
                Main.getMetodos().json(e.getPlayer(), f("&e[Caixas] &7" + p.getName() + " acabou de encontrar um item " + r.getDisplayNome()), i.getItem());
                p.sendMessage("");
                if (r.isSom())
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1, 0.8f);
            }
        } else {
            Main.getMetodos().json(e.getPlayer(), f("&e[Caixas] &7Você recebeu um item " + r.getDisplayNome()), i.getItem());
        }
        if (i.getItem().hasItemMeta() && i.getItem().getItemMeta().hasDisplayName()) {
            e.getPlayer().sendTitle(f("&fCaixa Misteriosa"), f(r.getCor() + "* " + i.getItem().getItemMeta().getDisplayName()));
        } else {
            e.getPlayer().sendTitle(f("&fCaixa Misteriosa"), f(r.getCor() + "* " + StringUtils.capitalize(i.getItem().getType().toString().toLowerCase())));
        }
        switch (i.getTipo()) {
            case ITEM:
                //da item
                HashMap<Integer, ItemStack> resto = e.getPlayer().getInventory().addItem(i.getItem());
                for (ItemStack item : resto.values()) {
                    e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), item);
                }

                if (Main.getMetodos().isFull(e.getPlayer().getInventory())) {
                    e.getPlayer().sendTitle(f("&cAlerta"), f("&fInventário Cheio"));
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 0.8f);
                }
                break;
            case COMANDO:
                for (String comando : i.getComandos()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comando
                            .replace("%player%", e.getPlayer().getName())
                            .replace("%player", e.getPlayer().getName()));
                }
                break;
        }
    }
}

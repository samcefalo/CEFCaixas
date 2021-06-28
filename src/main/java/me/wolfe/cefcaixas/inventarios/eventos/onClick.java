package me.wolfe.cefcaixas.inventarios.eventos;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.manager.CaixasManager;
import me.wolfe.cefcaixas.newevents.AbrirCaixaEvent;
import me.wolfe.cefcaixas.objetos.Caixa;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static me.wolfe.cefcaixas.metodos.Metodos.f;

public class onClick implements Listener {

    CEFCaixas Main;

    public onClick(CEFCaixas main) {
        Main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("Caixas")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                for (Caixa c : CaixasManager.caixas) {
                    if (!c.isMenu()) continue;
                    if (e.getSlot() == c.getSlot()) {
                        p.closeInventory();
                        p.openInventory(Main.getInventarioManager().getInventarioCaixa().getPreview(c));
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1f);
                        break;
                    }
                }
            }
        }

        if (e.getView().getTitle().startsWith("Prever -")) {
            e.setCancelled(true);
            if (e.getSlot() == 49) {
                p.openInventory(Main.getInventarioManager().getInventarioMain().getCaixas(p));
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1f);
            }
        }

        if (e.getView().getTitle().startsWith("Abrindo -")) {
            e.setCancelled(true);
        }

        if (e.getView().getTitle().startsWith("Abrir -")) {
            e.setCancelled(true);
            if (e.getInventory().getItem(13) == null) {
                return;
            }
            Caixa caixa = Main.getItemManager().getCaixa(e.getInventory().getItem(13));
            if (e.getSlot() == 15) {
                p.closeInventory();
            }

            if (e.getSlot() == 11) {
                if (caixa == null) {
                    return;
                }
                //sem espaço no inv
                if (!Main.getMetodos().SlotSobrando(p.getInventory(), 2)) {
                    p.sendMessage(f("&cVocê precisa ter espaço sobrando no inventário para abrir caixas.."));
                    return;
                }

                if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore()) {
                    Caixa c2 = Main.getItemManager().getCaixa(p.getItemInHand());
                    if (caixa != c2) {
                        p.closeInventory();
                        p.sendMessage(f("&e[Caixas] &cVocê precisa estar com a caixa " + caixa.getNome() + " na mão."));
                    }
                    p.closeInventory();
                    AbrirCaixaEvent ev = new AbrirCaixaEvent(p, caixa);
                    Bukkit.getServer().getPluginManager().callEvent(ev);
                    if (!ev.isCancelled()) {
                        if (p.getItemInHand().getAmount() > 1) {
                            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                        } else {
                            p.getInventory().setItemInHand(new ItemStack(Material.AIR));
                            p.updateInventory();
                        }
                        Main.getInventarioManager().getSpin().startSpinCSGO(p, caixa);
                    }
                } else {
                    p.closeInventory();
                    p.sendMessage(f("&e[Caixas] &cVocê não possui nenhuma caixa para ser aberta."));
                }
            }
        }
    }
}

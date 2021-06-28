package me.wolfe.cefcaixas.eventos;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.funcoes.FuncoesManager;
import me.wolfe.cefcaixas.objetos.Caixa;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static me.wolfe.cefcaixas.metodos.Metodos.f;


public class PlayerClick implements Listener {
    CEFCaixas Main;

    public PlayerClick(CEFCaixas m) {
        Main = m;
        Bukkit.getPluginManager().registerEvents(this, m);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction().name().contains("RIGHT")) {
            if (e.hasItem() && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasLore()) {
                Caixa caixa = Main.getItemManager().getCaixa(e.getItem());
                if (caixa != null) {
                    Player p = e.getPlayer();
                    e.setCancelled(true);

                    if (!FuncoesManager.ativado) {
                        p.sendMessage(f("&cO sistema de caixas está desabilitado no momento.."));
                        return;
                    }

                    if (caixa.isPermissao() && !p.hasPermission(caixa.getPermissao())) {
                        p.sendMessage(f("&e[Caixas] " + caixa.getNopermMSG()));
                        return;
                    }
                    if (!Main.getMetodos().SlotSobrando(p.getInventory(), 2)) {
                        p.sendMessage(f("&cVocê precisa ter espaço sobrando no inventário para abrir caixas.."));
                        return;
                    }
                    p.openInventory(Main.getInventarioManager().getInventarioCaixa().getConfirmacao(caixa));
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1f);
                }
            }
        }
        if (e.getAction().name().contains("LEFT")) {
            if (e.hasItem() && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasLore()) {
                Caixa caixa = Main.getItemManager().getCaixa(e.getItem());
                if (caixa != null) {
                    Player p = e.getPlayer();
                    if (!FuncoesManager.ativado) {
                        p.sendMessage(f("&cO sistema de caixas está desabilitado no momento.."));
                        return;
                    }

                    e.setCancelled(true);
                    p.openInventory(Main.getInventarioManager().getInventarioCaixa().getPreview(caixa));
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1f);
                }
            }
        }

    }
}

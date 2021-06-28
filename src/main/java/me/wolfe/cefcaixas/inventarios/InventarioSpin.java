package me.wolfe.cefcaixas.inventarios;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.metodos.Metodos;
import me.wolfe.cefcaixas.newevents.GanharItemCaixaEvent;
import me.wolfe.cefcaixas.objetos.Caixa;
import me.wolfe.cefcaixas.objetos.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.wolfe.cefcaixas.metodos.Metodos.f;
import static me.wolfe.cefcaixas.metodos.Metodos.random;

public class InventarioSpin {

    CEFCaixas Main;

    public InventarioSpin(CEFCaixas m) {
        Main = m;
    }


    public void startSpinCSGO(Player p, Caixa caixa) {
        final Inventory inv = Bukkit.createInventory(null, 27, f("Abrindo - " + caixa.getNome() + " ..."));
        inv.setItem(4, Main.getItemManager().getCaixa(caixa));
        p.openInventory(inv);
        ArrayList<Item> listaitems = new ArrayList<>();
        while (listaitems.size() < 9) {
            listaitems.add(caixa.getItemRandom());
        }
        new BukkitRunnable() {
            int tick = 0;
            Item item;
            int itemcursor = 0;
            int velocidade = 3;

            public void run() {
                ++tick;
                if (p == null) {
                    this.cancel();
                }
                if (tick % 15 == 0) velocidade += 1;

                switch (tick) {
                    case 200:
                        this.cancel();
                        break;
                    case 160:
                        if (inv.getItem(13) != null) {
                            Item i = caixa.getItem(inv.getItem(13));
                            item = i == null ? caixa.getItemRandom() : i;
                        }
                        clear(inv);
                        if (p != null) {
                            GanharItemCaixaEvent ev = new GanharItemCaixaEvent(p, item, p.getLocation());
                            Bukkit.getServer().getPluginManager().callEvent(ev);
                        }
                        break;
                    default:
                        if (tick % velocidade == 0 && tick < 160) {
                            if (p.getOpenInventory().getTitle().startsWith("Abrindo")) {
                                glass(inv);
                                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1f);
                                for (int slot = 9; slot < 18; slot++) {
                                    item = listaitems.get((slot + itemcursor) % listaitems.size());
                                    inv.setItem(slot, item.getItem());
                                }
                                itemcursor++;
                            } else {
                                if (tick < 150) {
                                    tick = 150;
                                }
                            }
                        }
                        break;
                }
            }
        }.runTaskTimer(Main, 0L, 1L);
    }

    private void glass(Inventory inv) {
        Material material;
        for (int i = 0; i < inv.getSize(); i++) {
            if (i > 8 && i < 18) continue;
            if (i == 4) continue;
            material = VIDROS.get((int) random(0, VIDROS.size()));
            inv.setItem(i, criarGlass(material));
        }
    }

    private List<Material> VIDROS = Arrays.asList(
            Material.RED_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.BLUE_STAINED_GLASS_PANE,
            Material.BROWN_STAINED_GLASS_PANE,
            Material.CYAN_STAINED_GLASS_PANE
    );

    private void clear(Inventory inv) {
        Material material;
        for (int i = 0; i < inv.getSize(); i++) {
            if (i == 13) continue;
            material = VIDROS.get((int) random(0, VIDROS.size()));
            inv.setItem(i, criarGlass(material));
        }
    }

    private ItemStack criarGlass(Material m) {
        Metodos metodos = Main.getMetodos();
        Material material = m;
        List<String> lore = new ArrayList();
        String nome = f("&eAbrindo...");
        return metodos.criarStack(material, nome, lore, false);
    }


}

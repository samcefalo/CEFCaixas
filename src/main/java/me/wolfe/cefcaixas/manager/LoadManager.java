package me.wolfe.cefcaixas.manager;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.objetos.Caixa;
import me.wolfe.cefcaixas.objetos.Item;
import me.wolfe.cefcaixas.objetos.ItemBuilder;
import me.wolfe.cefcaixas.objetos.Raridade;
import me.wolfe.cefcaixas.objetos.enums.Tipo;
import org.apache.commons.lang.StringUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static me.wolfe.cefcaixas.metodos.Metodos.f;
import static me.wolfe.cefcaixas.metodos.Metodos.log;

public class LoadManager {

    CEFCaixas Main;

    public LoadManager(CEFCaixas main) {
        Main = main;
        loadRaridade();
        loadCaixas();
    }

    public void loadRaridade() {
        CaixasManager.raridades.clear();
        for (String key : Main.getConfig().getConfigurationSection("Raridades").getKeys(false)) {
            String nome = key;
            boolean anunciar = Main.getConfig().getBoolean("Raridades." + key + ".anunciar");
            boolean som = Main.getConfig().getBoolean("Raridades." + key + ".som");
            String displaynome = Main.getConfig().getString("Raridades." + key + ".nome");
            DyeColor cor = DyeColor.valueOf(Main.getConfig().getString("Raridades." + key + ".cor").toUpperCase());
            Raridade raridade = new Raridade(nome, displaynome, cor, anunciar, som);
            CaixasManager.raridades.add(raridade);
        }
        log(f("&aRaridade das caixas iniciadas com sucesso"));
    }

    public void loadCaixas() {
        for (String key : Main.getConfig().getStringList("Caixas")) {
            YamlConfiguration config = Main.getCaixaFile(key);
            boolean ativado = config.getBoolean("ativado");
            if (!ativado) continue;
            boolean glow = config.getBoolean("glow");
            String nome = StringUtils.capitalize(key.replace(".yml", ""));
            String displaynome = config.getString(f("nome"));
            String perm = config.getString("permissao");
            String noperm = config.getString("permissao_mensagem");
            boolean gui = config.getBoolean("gui");
            int slot = config.getInt("slot");
            Material material = Material.valueOf(config.getString("material").toUpperCase());
            ArrayList<String> lore = new ArrayList();
            for (String linha : config.getStringList("lore")) {
                lore.add(f(linha));
            }
            ItemStack itemstack = Main.getMetodos().criarStack(material, f(displaynome), lore, glow);
            Caixa caixa = new Caixa(nome, slot, itemstack, gui, perm, noperm);
            for (String items : config.getConfigurationSection(".Itens").getKeys(false)) {
                String item = ".Itens." + items + ".";
                Material material_item = Material.valueOf(config.getString(item + "material").toUpperCase());
                int quantidade = config.getInt(item + "quantidade");
                int chance = config.getInt(item + "chance");

                Raridade ra = Main.getCaixasManager().getRaridade(config.getString(item + "raridade"));
                if (ra == null) {
                    log(f("&c[Caixas] Item '" + items + "' da caixa " + caixa.getNome() + " com raridade inválida (" + config.getString(item + "raridade") + ")"));
                    continue;
                }

                ArrayList<String> lore_item = new ArrayList();

                if (config.contains(item + "lore")) {
                    for (String linha : config.getStringList(item + "lore")) {
                        lore_item.add(f(linha));
                    }
                }

                ArrayList<String> comandos = new ArrayList();

                Tipo tipo = Tipo.ITEM;
                if (config.contains(item + "comandos")) {
                    tipo = Tipo.COMANDO;
                    for (String linha : config.getStringList(item + "comandos")) {
                        comandos.add(f(linha));
                    }
                }

                //ItemBuilder
                ItemBuilder itemBuilder = new ItemBuilder(material_item, quantidade);
                if (config.contains(item + "glow")) {
                    itemBuilder.setGlow(config.getBoolean(item + "glow"));
                }
                if (config.contains(item + "nome") && config.getString(item + "nome").length() > 1) {
                    itemBuilder.setDisplayNome(config.getString(item + "nome"));
                }
                itemBuilder.setLore(lore_item);
                if (config.contains(item + "encantamentos") && config.getString(item + "encantamentos").length() > 1) {
                    itemBuilder.addEnchantments(config.getString(item + "encantamentos"));
                }

                Item i = new Item(itemBuilder.toItemStack(), comandos, chance, tipo, ra);
                caixa.addItem(i);
            }
            CaixasManager.caixas.add(caixa);
        }
        log(f("&aCaixas iniciadas com sucesso"));
    }


}

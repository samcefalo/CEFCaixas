package me.wolfe.cefcaixas;

import me.wolfe.cefcaixas.comandos.ComandosCaixa;
import me.wolfe.cefcaixas.eventos.PlayerClick;
import me.wolfe.cefcaixas.eventos.onGanhar;
import me.wolfe.cefcaixas.funcoes.FuncoesManager;
import me.wolfe.cefcaixas.inventarios.InventarioManager;
import me.wolfe.cefcaixas.item.ItemManager;
import me.wolfe.cefcaixas.manager.CaixasManager;
import me.wolfe.cefcaixas.manager.LoadManager;
import me.wolfe.cefcaixas.metodos.Metodos;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static me.wolfe.cefcaixas.metodos.Metodos.f;
import static me.wolfe.cefcaixas.metodos.Metodos.log;

public final class CEFCaixas extends JavaPlugin {

    private Metodos metodos;
    private InventarioManager inventarioManager;
    private LoadManager loadManager;
    private CaixasManager caixasManager;
    private FuncoesManager funcoesManager;
    private ItemManager itemManager;

    @Override
    public void onEnable() {
        log(f("&aIniciando o plugin.."));
        loadConfig();
        startClasses();
        log(f("&aPlugin iniciado com sucesso.."));
    }

    @Override
    public void onDisable() {
        log(f("&cDesligando o plugin.."));

        log(f("&cPlugin desligado com sucesso.."));
    }

    void startClasses() {
        log(f("&aIniciando Classes.."));
        metodos = new Metodos(this);
        inventarioManager = new InventarioManager(this);
        caixasManager = new CaixasManager(this);
        loadManager = new LoadManager(this);
        new PlayerClick(this);
        new onGanhar(this);
        new ComandosCaixa(this);
        funcoesManager = new FuncoesManager(this);
        itemManager = new ItemManager(this);
        log(f("&aClasses iniciadas com sucesso.."));
    }

    public void loadConfig() {
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
            log(f("&aConfig criada com sucesso"));
        } else {
            log(f("&aConfig carregada com sucesso"));
        }
        setupExemplos();
    }

    public YamlConfiguration getCaixaFile(String r) {
        File fileEvento = new File(this.getDataFolder() + File.separator + "Caixas" + File.separator + r);
        return YamlConfiguration.loadConfiguration(fileEvento);
    }

    private void setupExemplos() {
        File eventosFile = new File(getDataFolder() + File.separator + "Caixas");
        if (!eventosFile.exists()) {
            eventosFile.mkdirs();
            log(f("&aPasta 'Caixas' criada com sucesso"));
        }
        if (!new File(getDataFolder() + File.separator + "Caixas" + File.separator + "basica.yml")
                .exists()) {
            saveResource("Caixas" + File.separator + "basica.yml", false);
        }

        if (!new File(getDataFolder() + File.separator + "Caixas" + File.separator + "intermediaria.yml")
                .exists()) {
            saveResource("Caixas" + File.separator + "intermediaria.yml", false);
        }
        if (!new File(getDataFolder() + File.separator + "Caixas" + File.separator + "epica.yml")
                .exists()) {
            saveResource("Caixas" + File.separator + "epica.yml", false);
        }
        if (!new File(getDataFolder() + File.separator + "Caixas" + File.separator + "suprema.yml")
                .exists()) {
            saveResource("Caixas" + File.separator + "suprema.yml", false);
        }
        if (!new File(getDataFolder() + File.separator + "Caixas" + File.separator + "premium.yml")
                .exists()) {
            saveResource("Caixas" + File.separator + "premium.yml", false);
        }
        if (!new File(getDataFolder() + File.separator + "Caixas" + File.separator + "encantamento.yml")
                .exists()) {
            saveResource("Caixas" + File.separator + "encantamento.yml", false);
        }
        log(f("&aArquivos da pasta 'caixas' carregados com sucesso"));
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public FuncoesManager getFuncoesManager() {
        return funcoesManager;
    }

    public CaixasManager getCaixasManager() {
        return caixasManager;
    }

    public Metodos getMetodos() {
        return metodos;
    }

    public InventarioManager getInventarioManager() {
        return inventarioManager;
    }

    public LoadManager getLoadManager() {
        return loadManager;
    }
}
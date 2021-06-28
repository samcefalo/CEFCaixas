package me.wolfe.cefcaixas.comandos;

import me.wolfe.cefcaixas.CEFCaixas;
import me.wolfe.cefcaixas.manager.CaixasManager;
import me.wolfe.cefcaixas.objetos.Caixa;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.wolfe.cefcaixas.metodos.Metodos.f;

public class ComandosCaixa implements CommandExecutor, TabCompleter {

    CEFCaixas Main;
    List<String> complementos = new ArrayList<>();

    public ComandosCaixa(CEFCaixas m) {
        Main = m;
        setupLista();
        m.getCommand("cefcaixa").setExecutor(this);
        m.getCommand("cefcaixa").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args[0].equalsIgnoreCase("darall")) {
            return (args.length == 2) ? StringUtil.copyPartialMatches(args[1], complementos, new ArrayList<>()) : null;
        }
        return (args.length == 3) ? StringUtil.copyPartialMatches(args[2], complementos, new ArrayList<>()) : null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cefcaixa")) {
            if (args.length >= 1) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!p.hasPermission("cef.admin")) {
                        //menu
                        p.openInventory(Main.getInventarioManager().getInventarioMain().getCaixas(p));
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1f);
                        return false;
                    }
                }
            }
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sendHelp(sender);
                    return false;
                }
                Player p = (Player) sender;
                //menu
                p.openInventory(Main.getInventarioManager().getInventarioMain().getCaixas(p));
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 0.6f, 1f);
            } else {
                if (args.length > 0) {
                    switch (args[0]) {
                        case "reload":
                            Main.reloadConfig();
                            Main.getFuncoesManager().setupFuncoes();
                            Main.getCaixasManager().reloadConfigs();
                            setupLista();
                            sender.sendMessage(f("&e[Caixas] &aReload completo.."));
                            break;
                        case "darall":
                            if (args.length != 3) {
                                sender.sendMessage(f("&cUtilize /caixa darall <caixa> <quantidade>"));
                                break;
                            }
                            Caixa caixa = Main.getCaixasManager().getCaixa(args[1]);
                            if (caixa == null) {
                                sender.sendMessage(f("&cCaixa '" + args[1] + "' não existe"));
                                break;
                            }
                            if (!isNum(args[2])) {
                                sender.sendMessage(f("&c'" + args[2] + "' precisa ser número"));
                                break;
                            }
                            int qnt = Integer.parseInt(args[2]);
                            sender.sendMessage(f("&aTodos online receberam " + qnt + " caixas " + caixa.getNome()));

                            Bukkit.getOnlinePlayers().forEach(player -> {
                                Main.getCaixasManager().darCaixa(player, caixa, qnt);
                            });

                            break;
                        case "add":
                        case "dar":
                            if (args.length != 4) {
                                sender.sendMessage(f("&cUtilize /caixa [dar] <player> <caixa> <quantidade>"));
                                break;
                            }
                            Player p1 = Bukkit.getPlayer(args[1]);
                            if (p1 == null) {
                                sender.sendMessage(f("&cPlayer '" + args[1] + "' não existe"));
                                break;
                            }
                            Caixa caixa1 = Main.getCaixasManager().getCaixa(args[2]);
                            if (caixa1 == null) {
                                sender.sendMessage(f("&cCaixa '" + args[2] + "' não existe"));
                                break;
                            }
                            if (!isNum(args[3])) {
                                sender.sendMessage(f("&c'" + args[3] + "' precisa ser número"));
                                break;
                            }
                            int qnt1 = Integer.parseInt(args[3]);
                            sender.sendMessage(f("&a" + args[1] + " recebeu " + qnt1 + " caixas " + caixa1.getNome()));
                            Main.getCaixasManager().darCaixa(p1, caixa1, qnt1);
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
                }
            }
        }
        return false;
    }

    void sendHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(f("&eCaixas -"));
        sender.sendMessage(f("&a/caixa reload"));
        sender.sendMessage(f("&a/caixa dar <player> <caixa> <quantidade>"));
        sender.sendMessage(f("&a/caixa darall <caixa> <quantidade>"));
        sender.sendMessage("");
    }

    boolean isNum(String s) {
        int n = 0;
        try {
            n = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    void setupLista() {
        complementos.clear();
        for (Caixa caixa : CaixasManager.caixas) {
            complementos.add(caixa.getNome());
        }
        Collections.sort(complementos);
    }
}

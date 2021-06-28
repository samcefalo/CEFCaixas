package me.wolfe.cefcaixas.metodos;

import me.wolfe.cefcaixas.CEFCaixas;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

import static me.wolfe.cefcaixas.metodos.NumeroRomano.toRoman;

public class Metodos {

    CEFCaixas Main;

    public Metodos(CEFCaixas main) {
        Main = main;
    }

    public static String f(String msg) {
        return msg.replace("&", "§");
    }

    public static void log(String msg) {
        Bukkit.getConsoleSender().sendMessage(f("[CEFCaixas] " + msg));
    }

    public static double random(double min, double max) {
        double random;
        Random r = new Random();
        random = min + (max - min) * r.nextDouble();
        return random;
    }

    public boolean isFull(Inventory inv) {
        for (ItemStack item : inv.getStorageContents()) {
            if (item == null) {
                return false;
            }
        }
        return true;
    }

    public ItemStack criarStack(Material id, int quantidade, String nome, List<String> lore, boolean brilhar) {
        ItemStack stack = new ItemStack(id, quantidade);
        ItemMeta stackM = stack.getItemMeta();
        stackM.setDisplayName(nome);
        stackM.setLore(lore);
        if (brilhar) {
            stackM.addEnchant(Enchantment.LUCK, 1, true);
            stackM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        stackM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(stackM);
        return stack;
    }

    public ItemStack criarStack(Material id, String nome, List<String> lore, boolean brilhar) {
        ItemStack stack = new ItemStack(id, 1);
        ItemMeta stackM = stack.getItemMeta();
        stackM.setDisplayName(nome);
        stackM.setLore(lore);
        if (brilhar) {
            stackM.addEnchant(Enchantment.LUCK, 1, true);
            stackM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        stackM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(stackM);
        return stack;
    }

    public ItemStack criarcabeca(String nome, String autor, List<String> lore) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta stackM = (SkullMeta) stack.getItemMeta();
        stackM.setOwner(autor);
        stackM.setDisplayName(nome);
        stackM.setLore(lore);
        stack.setItemMeta(stackM);
        return stack;
    }

    public ItemStack criarArmor(Material material, String nome, List<String> lore, Color color) {
        ItemStack stack = new ItemStack(material, 1);
        LeatherArmorMeta stackM = (LeatherArmorMeta) stack.getItemMeta();
        stackM.setColor(color);
        stackM.setDisplayName(nome);
        stackM.setLore(lore);
        stack.setItemMeta(stackM);
        return stack;
    }

    public void json(Player p, String text, String hover, String cmd) {
        TranslatableComponent msg = new TranslatableComponent(f(text));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(f(hover)).create()));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
        p.spigot().sendMessage(msg);
    }

    public void json(Player p, String text, String hover) {
        TranslatableComponent msg = new TranslatableComponent(f(text));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(f(hover)).create()));
        p.spigot().sendMessage(msg);
    }

    public String formatarData(Date d) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(d);
    }

    public Date formatarData(String d) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            return df.parse(d);
        } catch (ParseException e) {
            return null;
        }
    }

    public boolean SlotSobrando(Inventory inv, int slots) {
        int slot = 0;
        for (ItemStack itemStack : inv.getStorageContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                slot++;
            }
        }
        return (slot >= slots);
    }

    public String DiferencaData(Date startDate, Date endDate) {
        if (startDate == null) {
            startDate = new Date();
        }
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long dias = different / daysInMilli;
        different = different % daysInMilli;

        long horas = different / hoursInMilli;
        different = different % hoursInMilli;

        long minutos = different / minutesInMilli;
        different = different % minutesInMilli;

        long segundos = different / secondsInMilli;

        String formato = "";

        if (segundos < 0) {
            return f("&aCarregando..");
        }

        if (dias == 0) {
            if (horas == 0) {
                if (minutos == 0) {
                    if (segundos <= 0) {
                        formato = f(Main.getConfig().getString("Formato.Debug"));
                    } else {
                        formato = f(Main.getConfig().getString("Formato.Tempo_Segundo").replace("%dia%", "" + dias).replace("%hora%", "" + horas).replace("%min%", "" + minutos).replace("%seg%", "" + segundos));
                    }
                } else {
                    formato = f(Main.getConfig().getString("Formato.Tempo_Minuto").replace("%dia%", "" + dias).replace("%hora%", "" + horas).replace("%min%", "" + minutos).replace("%seg%", "" + segundos));
                }
            } else {
                formato = f(Main.getConfig().getString("Formato.Tempo_Hora").replace("%dia%", "" + dias).replace("%hora%", "" + horas).replace("%min%", "" + minutos).replace("%seg%", "" + segundos));
            }
        } else {
            formato = f(Main.getConfig().getString("Formato.Tempo_Inteiro").replace("%dia%", "" + dias).replace("%hora%", "" + horas).replace("%min%", "" + minutos).replace("%seg%", "" + segundos));
        }

        return formato;
    }

    public void fw(Location loc, Color c1, Color c2, int amount) {
        World w = loc.getWorld();
        for (int i = 0; i < amount; i++) {
            Location nl = loc;
            if (amount > 1) {
                nl = loc.add(new Vector(0, Math.random() + 0.5, 0).multiply(3));
            }
            Firework fw = (Firework) w.spawnEntity(nl, EntityType.FIREWORK);
            FireworkMeta meta = fw.getFireworkMeta();
            meta.addEffect(FireworkEffect.builder()
                    .flicker(true)
                    .trail(false)
                    .with(FireworkEffect.Type.STAR)
                    .withColor(c1)
                    .build());
            fw.setFireworkMeta(meta);
            try {
                Class<?> entityFireworkClass = getClass("net.minecraft.server.", "EntityFireworks");
                Class<?> craftFireworkClass = getClass("org.bukkit.craftbukkit.", "entity.CraftFirework");
                Object firework = craftFireworkClass.cast(fw);
                Method handle = firework.getClass().getMethod("getHandle");
                Object entityFirework = handle.invoke(firework);
                Field expectedLifespan = entityFireworkClass.getDeclaredField("expectedLifespan");
                Field ticksFlown = entityFireworkClass.getDeclaredField("ticksFlown");
                ticksFlown.setAccessible(true);
                ticksFlown.setInt(entityFirework, expectedLifespan.getInt(entityFirework));
                ticksFlown.setAccessible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private Class<?> getClass(String prefix, String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = prefix + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

    public void json(Player player, String message, ItemStack item) {

        StringJoiner hover_text = new StringJoiner("\n");
        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasDisplayName()) {
                hover_text.add(f("&a" + item.getItemMeta().getDisplayName()));
                hover_text.add("");
            } else {
                hover_text.add(f("&a" + item.getType().toString() + " " + item.getAmount() + "x"));
                hover_text.add("");
            }
            if (item.getItemMeta().hasLore()) {
                for (String lore : item.getItemMeta().getLore()) {
                    hover_text.add(f(lore));
                }
                hover_text.add("");
            }
            if (item.getItemMeta().hasEnchants() && !item.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                for (Enchantment enchantment : item.getItemMeta().getEnchants().keySet()) {
                    hover_text.add(f("&7" + enchantment.getName() + " " + toRoman(item.getItemMeta().getEnchantLevel(enchantment))));
                }
            }
        } else {
            hover_text.add(f("&a" + item.getType().toString() + " " + item.getAmount() + "x"));
        }

        TextComponent component = new TextComponent(f(message));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover_text.toString()).create()));


        player.spigot().sendMessage(component);
    }

}

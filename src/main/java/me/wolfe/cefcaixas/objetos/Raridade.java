package me.wolfe.cefcaixas.objetos;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public class Raridade {

    DyeColor cor;
    String nome, displaynome;
    boolean anunciar, som;

    public Raridade(String nome, String displaynome, DyeColor cor, boolean anunciar, boolean som) {
        this.nome = nome;
        this.displaynome = displaynome;
        this.anunciar = anunciar;
        this.cor = cor;
        this.som = som;
    }

    public boolean isSom() {
        return som;
    }

    public ChatColor getCor() {
        if (cor == DyeColor.PINK) return ChatColor.LIGHT_PURPLE;
        if (cor == DyeColor.PURPLE) return ChatColor.DARK_PURPLE;
        if (cor == DyeColor.ORANGE) return ChatColor.GOLD;
        return ChatColor.valueOf(cor.toString().toUpperCase());
    }

    public Color getColor() {
        return cor.getFireworkColor();
    }

    public String getNome() {
        return nome;
    }

    public String getDisplayNome() {
        return displaynome;
    }

    public boolean isAnunciar() {
        return anunciar;
    }

}

package me.wolfe.cefcaixas.newevents;

import me.wolfe.cefcaixas.objetos.Caixa;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AbrirCaixaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    Player p;
    Caixa caixa;
    private boolean cancelled;

    public AbrirCaixaEvent(Player p, Caixa caixa) {
        this.p = p;
        this.caixa = caixa;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public Player getPlayer() {
        return p;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

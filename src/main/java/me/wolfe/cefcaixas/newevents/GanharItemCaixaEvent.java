package me.wolfe.cefcaixas.newevents;

import me.wolfe.cefcaixas.objetos.Item;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GanharItemCaixaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    Player p;
    Item item;
    Location loc;
    private boolean cancelled;

    public GanharItemCaixaEvent(Player p, Item item, Location loc) {
        this.p = p;
        this.item = item;
        this.loc = loc;
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

    public Item getItem() {
        return item;
    }

    public Location getLoc() {
        return loc;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

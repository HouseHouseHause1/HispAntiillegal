package org.hisparquia.unbreakablepatch.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_12_R1.ItemStack;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author House 1
 * @since 5/5/22/ 4:27 PM
 * This file was created as a part of AntiDupe
 * Called when the constructor for net.minecraft.server.ItemStack is called
 */
@Getter
@RequiredArgsConstructor
public class ItemStackCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack item;
    private final ItemStackCreateReason reason;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public enum ItemStackCreateReason {
        DEFAULT,
        NBT
    }
}

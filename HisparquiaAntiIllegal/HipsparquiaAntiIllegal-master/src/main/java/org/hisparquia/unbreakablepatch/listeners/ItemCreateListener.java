package org.hisparquia.unbreakablepatch.listeners;

import org.hisparquia.unbreakablepatch.events.ItemStackCreateEvent;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

/**
 * @author House 1
 * @since 5/11/22/ 8:31 PM
 * This file was created as a part of UnbreakablePatch
 */
public class ItemCreateListener implements Listener {
    private final List<Item> gay = Arrays.asList(
            Item.getById(7), //Bedrock
            Item.getById(166), //Barrier
            Item.getById(120), // End portal frames
            Item.getById(52), //Monster spawner
            Item.getById(255), // Structure block
            Item.getById(217), //Structure void
            Item.getById(383) //Spawn egg
    );
    private final List<Item> exempt = Arrays.asList(
            Item.getById(0), //Air
            Item.getById(397), //Skull
            Item.getById(351), //Dye
            Item.getById(322), //Golden apple
            Item.getById(355), //Beds
            Item.getById(349), //Fish
            Item.getById(350), //Cooked fish
            Item.getById(425), //Banner
            Item.getById(263), //Coal
            Item.getById(358)
    );

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemCreate(ItemStackCreateEvent event) {
        ItemStack itemStack = event.getItem();
        if (exempt.contains(itemStack.getItem())) return;
        if (itemStack.getDamage() < 0 || itemStack.getDamage() > itemStack.getItem().getMaxDurability() && Item.getId(itemStack.getItem()) > 256) {
            itemStack.setDamage(0);
        }
        if (itemStack.getCount() > itemStack.getItem().getMaxStackSize()) itemStack.setCount(itemStack.getItem().getMaxStackSize());
        if (gay.contains(itemStack.getItem())) itemStack.setCount(-1);
    }
}

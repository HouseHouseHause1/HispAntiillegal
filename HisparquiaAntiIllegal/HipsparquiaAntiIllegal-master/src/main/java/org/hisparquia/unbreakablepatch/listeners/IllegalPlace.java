package org.hisparquia.unbreakablepatch.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author House 1
 * @since 5/11/22/ 9:31 PM
 * This file was created as a part of UnbreakablePatch
 */
public class IllegalPlace implements Listener {
    List<Material> illegal = Arrays.asList(
            Material.BEDROCK
    );

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (illegal.contains(event.getBlock().getType())) event.setCancelled(true);
    }
}

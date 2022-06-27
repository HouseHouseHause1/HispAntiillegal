package org.hisparquia.unbreakablepatch;

import org.hisparquia.unbreakablepatch.jagent.JavaAgentLoader;
import org.hisparquia.unbreakablepatch.listeners.IllegalPlace;
import org.hisparquia.unbreakablepatch.listeners.ItemCreateListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.management.ManagementFactory;

public final class UnbreakablePatch extends JavaPlugin {
    @Override
    public void onLoad() {
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));
        JavaAgentLoader loader = new JavaAgentLoader();
        loader.attach(pid);
        loader.detach();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ItemCreateListener(), this);
        getServer().getPluginManager().registerEvents(new IllegalPlace(), this);
    }

    @Override
    public void onDisable() {

    }
}
package org.hisparquia.unbreakablepatch.jagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class AgentMain {
    public static void premain(String agentOps, Instrumentation inst) {
    }

    public static void agentmain(String agentOps, Instrumentation inst) {
        System.out.println("Agent Started!");
        inst.addTransformer(new Transformer(), true);
        try {
            inst.retransformClasses(ItemStack.class);
            System.out.println("Re-transforming class");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String getName(Class<?> klass) {
        return klass.getName().replace(".", "/");
    }

    private static class Transformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader classLoader, String className, Class<?> klass, ProtectionDomain protectionDomain, byte[] currentBytes) throws IllegalClassFormatException {
            if (className == null) return currentBytes;
            ClassPool classPool = ClassPool.getDefault();
            try {
                if (className.equals(getName(ItemStack.class))) {
                    System.out.println("Instrumenting class " + className);
                    CtClass cc = classPool.get(ItemStack.class.getName());
                    CtConstructor defaultCon = cc.getDeclaredConstructor(new CtClass[]{classPool.get(Item.class.getName()), classPool.get(int.class.getName()), classPool.get(int.class.getName()), classPool.get(boolean.class.getName())});
                    defaultCon.insertAfter("org.bukkit.Bukkit.getServer().getPluginManager().callEvent(new org.hisparquia.unbreakablepatch.events.ItemStackCreateEvent(this, org.hisparquia.unbreakablepatch.events.ItemStackCreateEvent.ItemStackCreateReason.DEFAULT));");
                    CtConstructor nbtCon = cc.getDeclaredConstructor(new CtClass[]{classPool.get(NBTTagCompound.class.getName())});
                    nbtCon.insertAfter("org.bukkit.Bukkit.getServer().getPluginManager().callEvent(new org.hisparquia.unbreakablepatch.events.ItemStackCreateEvent(this, org.hisparquia.unbreakablepatch.events.ItemStackCreateEvent.ItemStackCreateReason.NBT));");
                    cc.detach();
                    return cc.toBytecode();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return currentBytes;
        }
    }
}
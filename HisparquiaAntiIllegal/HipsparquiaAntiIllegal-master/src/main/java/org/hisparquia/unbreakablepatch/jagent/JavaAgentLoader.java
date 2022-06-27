package org.hisparquia.unbreakablepatch.jagent;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author House 1
 * @since 4/25/22/ 6:07 PM
 * This file was created as a part of Durango
 */
public class JavaAgentLoader {

    public VirtualMachine jvm;

    private File getSelf() throws Throwable {
        return new File(JavaAgentLoader.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath());
    }

    public void attach(String pid) {
        try {
            System.out.println(new File(System.getProperty("java.home")).getAbsolutePath());
            File toolsJar = findToolsJar(new File(System.getProperty("java.home")));
            if (toolsJar != null) {
                System.out.println("Attaching to PID " + pid + " with tools jar " + toolsJar.getAbsolutePath());
            } else  {
                System.out.println("Could not find the tools jar");
                return;
            }
            if (!toolsJar.exists()) return;
            System.out.println("Found tools jar");
            URLClassLoader cl = (URLClassLoader) getClass().getClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(cl, toolsJar.toURI().toURL());
            jvm = VirtualMachine.attach(pid);
            jvm.loadAgent(getSelf().getAbsolutePath());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void detach() {
        try {
            jvm.detach();
        } catch (Throwable e) {
        }
    }
    public static File findToolsJar(File javaHome) {
        File toolsJar = new File(javaHome, "lib/tools.jar");
        if (toolsJar.exists()) {
            return toolsJar;
        }
        if (javaHome.getName().equalsIgnoreCase("jre")) {
            javaHome = javaHome.getParentFile();
            toolsJar = new File(javaHome, "lib/tools.jar");
            if (toolsJar.exists()) {
                return toolsJar;
            }
        }

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            String version = System.getProperty("java.version");
            if (javaHome.getName().matches("jre\\d+") || javaHome.getName().equals("jre" + version)) {
                javaHome = new File(javaHome.getParentFile(), "jdk" + version);
                toolsJar = new File(javaHome, "lib/tools.jar");
                if (toolsJar.exists()) {
                    return toolsJar;
                }
            }
        }
        return null;
    }
}

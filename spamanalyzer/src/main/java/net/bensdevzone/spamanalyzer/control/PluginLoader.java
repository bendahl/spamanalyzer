/* ==============================
Copyright (c) 2013 Benjamin Dahlmanns, ben@bensdevzone.net

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
============================== */
package net.bensdevzone.spamanalyzer.control;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.bensdevzone.spamanalyzer.plugin.AnalyzerPlugin;

/**
 * This class handles the loading of all currently available plugins. In order to prevent
 * the creation of multiple loader instances, this class is a singleton
 *
 * @author ben
 */
public class PluginLoader {
    private final char FILE_SEP = System.getProperty("file.separator").charAt(0);
    private final String PLUGIN_FOLDER_NAME = "plugins" + FILE_SEP;
    private HashMap<String, AnalyzerPlugin> loadedPlugins;
    private static PluginLoader instance;
    private String curDir;
    
    private PluginLoader() {
        loadedPlugins = new HashMap<>();
        curDir = System.getProperty("user.dir") + FILE_SEP;
        File pluginDir = new File(curDir + PLUGIN_FOLDER_NAME);
        
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jar");
            }
        };

        loadPlugins(pluginDir.listFiles(filter));            
    }

    /**
     * Creates a new instance of the PluginLoader if there is none and returns an existing instance
     * if present
     *
     * @return PluginLoader instance
     */
    public static PluginLoader getInstance() {
        if(instance == null) {
            synchronized (PluginLoader.class) {
                if(instance == null) {
                    instance = new PluginLoader();
                }
            }
        }

        return instance;
    }

    /**
     * Returns a map of all currently loaded plugins
     *
     * @return Hashmap containing all Plugins key=internalName, value=associated plugin
     */
    public HashMap<String, AnalyzerPlugin> getLoadedPlugins() {
        return this.loadedPlugins;
    }

    /**
     * Opens a jar file, scans all classes and tries to load all classes that implement the AnalyzerPlugin
     * interface
     *
     * @param files
     */
    private void loadPlugins (File[] files) {
        if(files == null)
            throw new  InitializationException ("No Plugins found. Initialization failed.");

        JarEntry entry = null;

        for(File file : files) {
            try {
                JarFile jar = new JarFile(file);
                URL[] urls = {new URL("jar:" + file.toURI().toURL() + "!/")};

                // It is important to set the parent ClassLoader in order to make sure that the new plugins
                // and their folders will be added to the classpath correctly
                URLClassLoader cl = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

                Enumeration e = jar.entries();
                while (e.hasMoreElements()) {
                    entry = (JarEntry) e.nextElement();

                    if (entry.getName().endsWith(".class")) {
                        Class<?> clazz = cl.loadClass(extractClassName(entry));

                        if (AnalyzerPlugin.class.isAssignableFrom(clazz)
                                && !AnalyzerPlugin.class.getName()
                                .equals(clazz.getName())) {
                            AnalyzerPlugin pl = (AnalyzerPlugin) clazz.newInstance();
                            loadedPlugins.put(pl.getInternalName(), pl);
                        }
                    }
                }
            } catch (IOException e) {
                throw new InitializationException("An error occured while trying to load the file " + file.getName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new InitializationException("Error during plugin instantiation. AnalyzerPlugin could not be created." +
                        " Cause: " + entry != null ? entry.getName() : "null");
            }
        }
    }
    
    private String extractClassName(JarEntry entry) {
        String className = entry.getName().substring(0, entry.getName().length() - ".class".length());
        return className.replace(FILE_SEP, '.');
    }
    
    
}

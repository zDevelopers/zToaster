/*
 * Copyright or © or Copr. ZLib contributors (2015)
 *
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL-B
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-B license and that you accept its terms.
 */

package fr.zcraft.ztoaster;

import fr.zcraft.zlib.components.commands.Commands;
import fr.zcraft.zlib.components.gui.Gui;
import fr.zcraft.zlib.components.i18n.I18n;
import fr.zcraft.zlib.components.scoreboard.Sidebar;
import fr.zcraft.zlib.components.scoreboard.SidebarScoreboard;
import fr.zcraft.zlib.core.ZPlugin;
import fr.zcraft.zlib.tools.PluginLogger;
import fr.zcraft.ztoaster.commands.AddCommand;
import fr.zcraft.ztoaster.commands.ListCommand;
import fr.zcraft.ztoaster.commands.OpenCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Locale;


public class Toaster extends ZPlugin implements Listener
{
    /**
     * A counter for all the toasts created (until toaster restart).
     */
    static private int toastCounter = 0;
    
    /**
     * A list of all the toasts.
     */
    static private ArrayList<Toast> toasts;

    /**
     * The screen of the toaster.
     */
    private Sidebar toasterSidebar;


    @Override
    public void onEnable()
    {
        PluginLogger.info("Setting up toaster.");
        
        toasts = new ArrayList<>();
        toastCounter = 0;
        
        loadComponents(Gui.class, Commands.class, ToasterWorker.class, SidebarScoreboard.class, I18n.class);
        
        Commands.register("toaster", AddCommand.class, OpenCommand.class, ListCommand.class);

        I18n.useDefaultPrimaryLocale();
        I18n.setFallbackLocale(Locale.US);

        getServer().getPluginManager().registerEvents(this, this);


        toasterSidebar = new ToasterSidebar();

        for (Player player : getServer().getOnlinePlayers())
            toasterSidebar.addRecipient(player);

        toasterSidebar.runAutoRefresh(true);
    }
    
    @Override
    public void onDisable()
    {
        PluginLogger.info("Unplugging toaster.");
    }


    /**
     * @return The id for a new toast.
     */
    static public int newToastId()
    {
        return toastCounter++;
    }
    
    /**
     * @return an array of all the toasts ever created (until toaster restart).
     */
    static public Toast[] getToasts()
    {
        return toasts.toArray(new Toast[toasts.size()]);
    }
    
    /**
     * Adds a new toast to the world.
     * @return the newly created toast.
     */
    static public Toast newToast()
    {
        Toast toast = new Toast();
        toasts.add(toast);
        return toast;
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent ev)
    {
        toasterSidebar.addRecipient(ev.getPlayer());
    }
}

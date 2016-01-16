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

package fr.zcraft.ztoaster.commands;

import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.worker.WorkerCallback;
import fr.zcraft.zlib.tools.PluginLogger;
import fr.zcraft.ztoaster.Toast;
import fr.zcraft.ztoaster.ToasterWorker;
import org.bukkit.entity.Player;

@CommandInfo(name = "add")
public class AddCommand extends Command
{
    @Override
    protected void run() throws CommandException
    {
        final Player player = playerSender();
        
        Toast toast = ToasterWorker.addToast(new WorkerCallback<Integer>()
        {
            @Override
            public void finished(Integer toastId)
            {
                player.sendMessage(I.t("DING ! Toast {0} is ready !", toastId));
                
            }

            @Override
            public void errored(Throwable exception)
            {
                PluginLogger.error("Error while toasting", exception);
                player.sendMessage(I.t("{ce}Oh no ! A toasted exception !"));
                player.sendMessage(I.t("{ce}See toaster logs for details."));
            }
        });
        
        player.sendMessage(I.t("Toast {0} added.", toast.getToastId()));
    }
}

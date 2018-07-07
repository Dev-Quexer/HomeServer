package me.quexer.homeserver.proxysystem.commands;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.uuid.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class UnMuteCMD extends Command {


    public UnMuteCMD() {
        super("unmute");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("ban.unmute")) {
            if (args.length == 2) {
                ProxySystem.getBanPlayerManager().getBanPlayer(UUIDFetcher.getUUID(args[0]).toString(), banPlayer -> {
                    ProxySystem.getMuteManager().isMuteed(banPlayer, muted -> {
                        if(muted) {
                            ProxySystem.getMuteManager().unMutePlayer(banPlayer, sender.getName(), args[1]);
                        } else {
                            sender.sendMessage(ProxySystem.getMutePrefix()+"§7Dieser §cSpieler §7ist nicht gemutet");
                        }
                    });
                });
            } else {
                sender.sendMessage(ProxySystem.getMutePrefix()+"§7Benutze§8: §c/unmute <Player> <Reason>");
            }
        }
    }

}

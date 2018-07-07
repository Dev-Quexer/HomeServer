package me.quexer.homeserver.proxysystem.commands;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.uuid.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class UnBanCMD extends Command {
    public UnBanCMD() {
        super("unban");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("ban.unban")) {
            if (args.length == 2) {
                ProxySystem.getBanPlayerManager().getBanPlayer(UUIDFetcher.getUUID(args[0]).toString(), banPlayer -> {
                    ProxySystem.getBanManager().isBanned(banPlayer, banned -> {
                        if(banned) {
                            ProxySystem.getBanManager().unBanPlayer(banPlayer, sender.getName(), args[1]);
                        } else {
                            sender.sendMessage(ProxySystem.getBanPrefix()+"§7Dieser §cSpieler §7ist nicht gebannt");
                        }
                    });
                });
            } else {
                sender.sendMessage(ProxySystem.getBanPrefix()+"§7Benutze§8: §c/unban <Player> <Reason>");
            }
        }
    }
}

package me.quexer.homeserver.serverapi.commands;

import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.utils.NickAPI;
import me.quexer.homeserver.serverapi.utils.enums.SoundType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NickCMD implements CommandExecutor {

    public ArrayList<Player> used = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {


        Player p = (Player) s;

        if(p.hasPermission("nick.nick")) {
            if(ServerAPI.isNickOnThisServer()) {
                if (!used.contains(p)) {
                    if (NickAPI.hasNick(p)) {
                        NickAPI.removeNick(p);
                        //Tablist.setPrefix(p);
                        used.add(p);

                        Bukkit.getScheduler().runTaskLaterAsynchronously(ServerAPI.getInstance(), () -> used.remove(p), 20 * 3);

                    } else {
                        NickAPI.setRandomNick(p);
                        //NickAPI.changeSkin(((CraftPlayer)p), p.getName());
                        //Tablist.setPrefix(p);

                        used.add(p);

                        Bukkit.getScheduler().runTaskLaterAsynchronously(ServerAPI.getInstance(), () -> used.remove(p), 20 * 3);

                    }

                } else {
                    p.sendMessage(ServerAPI.getPrefix() + "§cWarte einen Moment§8, §cbefor du diesen §eCommand §cbenutzt");
                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 2F, 0.3F);
                }
            } else {
                p.sendMessage(ServerAPI.getPrefix()+"§cAuf diesem Server ist das Nicken nicht erlaubt§8!");
                ServerAPI.getSoundManager().play(p, SoundType.BAD);
            }

        } else {
            p.sendMessage(ServerAPI.getPrefix()+"§cDazu hast du keine Rechte!");
        }




        return true;
    }


}

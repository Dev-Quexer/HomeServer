package me.quexer.homeserver.proxysystem.commands;

import me.quexer.homeserver.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReasonsCMD extends Command {


    public ReasonsCMD() {
        super("reasons");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("ban.reasons")) {
            s.sendMessage("§8§m--------------------------");
            s.sendMessage(ProxySystem.getBanPrefix()+"§7>> §eBan §7<<");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cHacking §8- §c#1");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cTrolling §8- §c#2");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cTeaming §8- §c#3");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cBugusing §8- §c#4");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cSkin §8- §c#5");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cName §8- §c#6");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cReport-Ausnutzung §8- §c#7");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cBannumgehung §8- §c#8");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cHausverbot §8- §c#9");
            s.sendMessage(ProxySystem.getBanPrefix()+"§7>> §eMute §7<<");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cBeleidigung §8- §c#10");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cRassismus §8- §c#11");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cWerbung §8- §c#12");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cSpamming §8- §c#13");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cProvokation §8- §c#14");
            s.sendMessage(ProxySystem.getBanPrefix()+"§cSchweigepflicht §8-§c#15");
            s.sendMessage("§8§m--------------------------");

        }


    }
    
}

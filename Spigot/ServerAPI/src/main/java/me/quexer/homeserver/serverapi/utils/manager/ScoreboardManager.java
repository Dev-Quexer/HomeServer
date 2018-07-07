package me.quexer.homeserver.serverapi.utils.manager;

import me.quexer.homeserver.serverapi.ServerAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class ScoreboardManager {

    public Scoreboard getScoreboard(Player player) {
        if(player.hasMetadata("scoreboard"))
            return (Scoreboard) player.getMetadata("scoreboard").get(0).value();
        Scoreboard scoreboard = ServerAPI.getInstance().getServer().getScoreboardManager().getNewScoreboard();
        ServerAPI.getInstance().setMetadata(player, "scoreboard", scoreboard);
        return scoreboard;
    }

    public void setBoard(Player player, String title, HashMap<String, Integer> sidebar) {
        Scoreboard scoreboard = getScoreboard(player);
        Objective objective = scoreboard.getObjective(player.getName());
        if(objective != null)
            objective.unregister();
         objective = scoreboard.registerNewObjective(player.getName(), "dummy");
         objective.setDisplayName(fromColoredText(title));
         objective.setDisplaySlot(DisplaySlot.SIDEBAR);
         for (String string : sidebar.keySet()) {
             objective.getScore(fromColoredText(string)).setScore(sidebar.get(string));
         }
         player.setScoreboard(scoreboard);

    }

    public void createTeam(Player player, String name, String entry, String prefix, String suffix) {
        Scoreboard scoreboard = getScoreboard(player);
        Team team = scoreboard.getTeam(name);
        if(team == null)
            team = scoreboard.registerNewTeam(name);
        team.addEntry(fromColoredText(entry));
        team.setPrefix(fromColoredText(prefix));
        team.setSuffix(fromColoredText(suffix));
    }

    private String fromColoredText(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}

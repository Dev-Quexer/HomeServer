package me.quexer.homeserver.lobby;

import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.utils.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {


    private static String prefix;
    private static Lobby instance;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    private void init() {
        setInstance(this);
        setPrefix("§8» §6§lServer §8➜ ");
        ServerAPI.setNickOnThisServer(false);
        new GameAPI("GommeHD.net", null);

        Bukkit.setDefaultGameMode(GameMode.ADVENTURE);
        for (World world : Bukkit.getWorlds()) {
            world.setAmbientSpawnLimit(0);
            world.setAnimalSpawnLimit(0);
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setPVP(false);
            world.setStorm(false);
            world.setThunderDuration(0);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("randomTickSpeed", "0");
        }

    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        Lobby.prefix = prefix;
    }

    public static Lobby getInstance() {
        return instance;
    }

    public static void setInstance(Lobby instance) {
        Lobby.instance = instance;
    }
}

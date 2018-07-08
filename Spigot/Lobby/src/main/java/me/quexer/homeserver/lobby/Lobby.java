package me.quexer.homeserver.lobby;

import de.dytanic.cloudnet.bridge.CloudServer;
import me.quexer.homeserver.lobby.chestroll.ChestRollEvents;
import me.quexer.homeserver.lobby.command.setLocCMD;
import me.quexer.homeserver.lobby.dailyreward.DailyRewardEvents;
import me.quexer.homeserver.lobby.entitys.LobbyPlayer;
import me.quexer.homeserver.lobby.listeners.Cancel;
import me.quexer.homeserver.lobby.listeners.JumpPads;
import me.quexer.homeserver.lobby.listeners.MainEvents;
import me.quexer.homeserver.lobby.manager.LobbyPlayerManager;
import me.quexer.homeserver.lobby.manager.LocationManager;
import me.quexer.homeserver.lobby.manager.PlayerManager;
import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.utils.GameAPI;
import me.quexer.homeserver.serverapi.utils.manager.ScoreboardManager;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class Lobby extends JavaPlugin {


    private static String prefix;
    private static Lobby instance;
    private static MainEvents mainEvents;
    private static DailyRewardEvents dailyRewardEvents;
    private static ChestRollEvents chestRollEvents;
    private static PlayerManager playerManager;
    private static LocationManager locationManager;
    private static LobbyPlayerManager lobbyPlayerManager;
    private static ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        new AsyncTask(() -> {
            Bukkit.getOnlinePlayers().forEach(o -> {
                LobbyPlayer lobbyPlayer = (LobbyPlayer) o.getMetadata("lobby").get(0).value();
                getLobbyPlayerManager().updateLobbyPlayer(lobbyPlayer, lobbyPlayer1 -> {
                });
            });
        });
    }

    private void init() {
        setInstance(this);
        setPrefix("§8» §3§lLobby §8➜ ");
        ServerAPI.setNickOnThisServer(false);
        new GameAPI("GommeHD.net", null);
        CloudServer.getInstance().setMaxPlayers(25);

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
        setMainEvents(new MainEvents());
        setPlayerManager(new PlayerManager());
        setLocationManager(new LocationManager());
        setLobbyPlayerManager(new LobbyPlayerManager());
        setScoreboardManager(new ScoreboardManager());
        setChestRollEvents(new ChestRollEvents());
        setDailyRewardEvents(new DailyRewardEvents());
        Bukkit.getPluginManager().registerEvents(new Cancel(), this);
        Bukkit.getPluginManager().registerEvents(new JumpPads(), this);
        Bukkit.getPluginCommand("setLoc").setExecutor(new setLocCMD());

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (ServerAPI.getLocationAPI().exist("spawn")) {
                getLocationManager().setSpawn(ServerAPI.getLocationAPI().getLocation("spawn"));
            }
            if (ServerAPI.getLocationAPI().exist("lobbyswitcher")) {
                getLocationManager().setLobbyswitcher(ServerAPI.getLocationAPI().getLocation("lobbyswitcher"));
            }
            if (ServerAPI.getLocationAPI().exist("ttt")) {
                getLocationManager().setTtt(ServerAPI.getLocationAPI().getLocation("ttt"));
            }
            if (ServerAPI.getLocationAPI().exist("qsg")) {
                getLocationManager().setQsg(ServerAPI.getLocationAPI().getLocation("qsg"));
            }
            if (ServerAPI.getLocationAPI().exist("daily")) {
                getLocationManager().setDaily(ServerAPI.getLocationAPI().getLocation("daily"));
            }
            if (ServerAPI.getLocationAPI().exist("homeunity")) {
                getLocationManager().setHomeunity(ServerAPI.getLocationAPI().getLocation("homeunity"));
            }
            if (ServerAPI.getLocationAPI().exist("chestroll")) {
                getLocationManager().setChestroll(ServerAPI.getLocationAPI().getLocation("chestroll"));
            }
            if (ServerAPI.getLocationAPI().exist("chest")) {
                getLocationManager().setChest(ServerAPI.getLocationAPI().getLocation("chest"));
            }
            getLocationManager().getChest().getBlock().setType(Material.ENDER_CHEST);
            ServerAPI.getInstance().setMetadata(getLocationManager().getChest().getBlock(), "chestroll", "Ja");
            System.out.println("Locations loaded");
        });

    }

    public static DailyRewardEvents getDailyRewardEvents() {
        return dailyRewardEvents;
    }

    public static void setDailyRewardEvents(DailyRewardEvents dailyRewardEvents) {
        Lobby.dailyRewardEvents = dailyRewardEvents;
    }

    public static ChestRollEvents getChestRollEvents() {
        return chestRollEvents;
    }

    public static void setChestRollEvents(ChestRollEvents chestRollEvents) {
        Lobby.chestRollEvents = chestRollEvents;
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public static void setScoreboardManager(ScoreboardManager scoreboardManager) {
        Lobby.scoreboardManager = scoreboardManager;
    }

    public static LobbyPlayerManager getLobbyPlayerManager() {
        return lobbyPlayerManager;
    }

    public static void setLobbyPlayerManager(LobbyPlayerManager lobbyPlayerManager) {
        Lobby.lobbyPlayerManager = lobbyPlayerManager;
    }

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static void setLocationManager(LocationManager locationManager) {
        Lobby.locationManager = locationManager;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static void setPlayerManager(PlayerManager playerManager) {
        Lobby.playerManager = playerManager;
    }

    public static MainEvents getMainEvents() {
        return mainEvents;
    }

    public static void setMainEvents(MainEvents mainEvents) {
        Lobby.mainEvents = mainEvents;
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

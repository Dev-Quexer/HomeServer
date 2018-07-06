package me.quexer.homeserver.proxysystem;

import com.google.gson.Gson;
import me.quexer.homeserver.proxysystem.commands.*;
import me.quexer.homeserver.proxysystem.listeners.BanListener;
import me.quexer.homeserver.proxysystem.listeners.MuteListener;
import me.quexer.homeserver.proxysystem.manager.BanManager;
import me.quexer.homeserver.proxysystem.manager.MuteManager;
import me.quexer.homeserver.proxysystem.utils.BanPlayerManager;
import me.quexer.homeserver.proxysystem.utils.database.MongoManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class ProxySystem extends Plugin {


    private static ProxySystem instance;
    private static String banPrefix;
    private static String mutePrefix;
    private static String prefix;
    private static MongoManager mongoManager;
    private static BanManager banManager;
    private static MuteManager muteManager;
    private static ProxyServer bungeeCord;
    private static Gson gson;
    private static BanPlayerManager banPlayerManager;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        setInstance(this);
        setBanPrefix("§8» §c§lBanSystem §8➜ ");
        setMutePrefix("§8» §5§lMuteSystem §8➜ ");
        setPrefix("§8» §6§lProxySystem §8➜ ");
        setMongoManager(new MongoManager("localhost", 27017, "HomeServer"));
        setBanManager(new BanManager());
        setGson(new Gson());
        setBanPlayerManager(new BanPlayerManager());
        setMuteManager(new MuteManager());
        setBungeeCord(getProxy());

        getBungeeCord().getPluginManager().registerCommand(this, new BanCMD());
        getBungeeCord().getPluginManager().registerCommand(this, new ReasonsCMD());
        getBungeeCord().getPluginManager().registerCommand(this, new UnBanCMD());
        getBungeeCord().getPluginManager().registerCommand(this, new UnMuteCMD());
        getBungeeCord().getPluginManager().registerCommand(this, new HelpCMD());
        getBungeeCord().getPluginManager().registerListener(this, new BanListener());
        getBungeeCord().getPluginManager().registerListener(this, new MuteListener());
    }

    public static MuteManager getMuteManager() {
        return muteManager;
    }

    public static void setMuteManager(MuteManager muteManager) {
        ProxySystem.muteManager = muteManager;
    }

    public static BanPlayerManager getBanPlayerManager() {
        return banPlayerManager;
    }

    public static void setBanPlayerManager(BanPlayerManager banPlayerManager) {
        ProxySystem.banPlayerManager = banPlayerManager;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        ProxySystem.prefix = prefix;
    }

    public static String getMutePrefix() {
        return mutePrefix;
    }

    public static void setMutePrefix(String mutePrefix) {
        ProxySystem.mutePrefix = mutePrefix;
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        ProxySystem.gson = gson;
    }

    public static ProxyServer getBungeeCord() {
        return bungeeCord;
    }

    public static void setBungeeCord(ProxyServer bungeeCord) {
        ProxySystem.bungeeCord = bungeeCord;
    }

    public static ProxySystem getInstance() {
        return instance;
    }

    public static void setInstance(ProxySystem instance) {
        ProxySystem.instance = instance;
    }

    public static String getBanPrefix() {
        return banPrefix;
    }

    public static void setBanPrefix(String banPrefix) {
        ProxySystem.banPrefix = banPrefix;
    }

    public static MongoManager getMongoManager() {
        return mongoManager;
    }

    public static void setMongoManager(MongoManager mongoManager) {
        ProxySystem.mongoManager = mongoManager;
    }

    public static BanManager getBanManager() {
        return banManager;
    }

    public static void setBanManager(BanManager banManager) {
        ProxySystem.banManager = banManager;
    }
}

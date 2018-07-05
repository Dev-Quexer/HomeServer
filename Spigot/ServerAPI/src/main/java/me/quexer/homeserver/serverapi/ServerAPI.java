package me.quexer.homeserver.serverapi;

import com.google.gson.Gson;
import de.dytanic.cloudnet.api.CloudAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.InventoryHandler;
import me.quexer.homeserver.serverapi.quickyapi.api.LocationAPI;
import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.quickyapi.database.AsyncMySQL;
import me.quexer.homeserver.serverapi.quickyapi.database.MongoManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerAPI extends JavaPlugin {

    private static ServerAPI instance;
    private static Gson gson;
    private static MongoManager mongoManager;
    private static AsyncMySQL asyncMySQL;
    private static LocationAPI locationAPI;
    private static InventoryHandler inventoryHandler;
    private static String prefix;


    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void init() {
        new AsyncTask(() -> {
        setInstance(this);
        setGson(new Gson());
        setLocationAPI(new LocationAPI());
        setMongoManager(new MongoManager("localhost", 27017, "HomeServer"));
        setInventoryHandler(new InventoryHandler());
        setPrefix("§8» §bLobby §8➜");
        System.out.println("Alle Klassen initialisiert!");
        });
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        ServerAPI.prefix = prefix;
    }

    public static ServerAPI getInstance() {
        return instance;
    }

    public static void setInstance(ServerAPI instance) {
        ServerAPI.instance = instance;
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        ServerAPI.gson = gson;
    }

    public static MongoManager getMongoManager() {
        return mongoManager;
    }

    public static void setMongoManager(MongoManager mongoManager) {
        ServerAPI.mongoManager = mongoManager;
    }

    public static AsyncMySQL getAsyncMySQL() {
        return asyncMySQL;
    }

    public static void setAsyncMySQL(AsyncMySQL asyncMySQL) {
        ServerAPI.asyncMySQL = asyncMySQL;
    }

    public static LocationAPI getLocationAPI() {
        return locationAPI;
    }

    public static void setLocationAPI(LocationAPI locationAPI) {
        ServerAPI.locationAPI = locationAPI;
    }

    public static InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }

    public static void setInventoryHandler(InventoryHandler inventoryHandler) {
        ServerAPI.inventoryHandler = inventoryHandler;
    }

}

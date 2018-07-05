package me.quexer.homeserver.serverapi.quickyapi.api.game;

import me.quexer.homeserver.serverapi.ServerAPI;
import org.bukkit.Bukkit;

public class AsyncTask {

    private Runnable run;

    public AsyncTask(Runnable run) {
        this.run = run;
        Bukkit.getScheduler().runTaskAsynchronously(ServerAPI.getPlugin(ServerAPI.class), run);
    }
}

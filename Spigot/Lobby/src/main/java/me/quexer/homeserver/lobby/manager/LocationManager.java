package me.quexer.homeserver.lobby.manager;


import org.bukkit.Location;

public class LocationManager {

    private Location spawn, daily, ttt, qsg, homeunity, lobbyswitcher, chestroll, chest;

    public Location getChestroll() {
        return chestroll;
    }

    public void setChestroll(Location chestroll) {
        this.chestroll = chestroll;
    }

    public Location getChest() {
        return chest;
    }

    public void setChest(Location chest) {
        this.chest = chest;
    }

    public Location getLobbyswitcher() {
        return lobbyswitcher;
    }

    public void setLobbyswitcher(Location lobbyswitcher) {
        this.lobbyswitcher = lobbyswitcher;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getDaily() {
        return daily;
    }

    public void setDaily(Location daily) {
        this.daily = daily;
    }

    public Location getTtt() {
        return ttt;
    }

    public void setTtt(Location ttt) {
        this.ttt = ttt;
    }

    public Location getQsg() {
        return qsg;
    }

    public void setQsg(Location qsg) {
        this.qsg = qsg;
    }

    public Location getHomeunity() {
        return homeunity;
    }

    public void setHomeunity(Location homeunity) {
        this.homeunity = homeunity;
    }
}

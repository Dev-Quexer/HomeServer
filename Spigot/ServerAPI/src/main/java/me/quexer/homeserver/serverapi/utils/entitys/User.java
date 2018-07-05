package me.quexer.homeserver.serverapi.utils.entitys;

public class User {

    private String uuid;

    private long coins;

    private int level;

    private int xp;

    private long lastPlayed;

    private long nextDailyReward;

    private boolean isNick;

    private int elo;

    public User() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public long getNextDailyReward() {
        return nextDailyReward;
    }

    public void setNextDailyReward(long nextDailyReward) {
        this.nextDailyReward = nextDailyReward;
    }

    public boolean isNick() {
        return isNick;
    }

    public void setNick(boolean nick) {
        isNick = nick;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}

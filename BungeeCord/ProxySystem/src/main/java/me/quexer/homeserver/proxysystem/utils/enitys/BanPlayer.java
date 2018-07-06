package me.quexer.homeserver.proxysystem.utils.enitys;

public class BanPlayer {

    private String uuid;

    private long banEnd;

    private long muteEnd;

    private int banPoints;

    private int mutePoints;

    private String banReason;

    private String muteReason;

    private String banFrom;

    private String muteFrom;


    public BanPlayer() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getBanEnd() {
        return banEnd;
    }

    public void setBanEnd(long banEnd) {
        this.banEnd = banEnd;
    }

    public long getMuteEnd() {
        return muteEnd;
    }

    public void setMuteEnd(long muteEnd) {
        this.muteEnd = muteEnd;
    }

    public int getBanPoints() {
        return banPoints;
    }

    public void setBanPoints(int banPoints) {
        this.banPoints = banPoints;
    }

    public int getMutePoints() {
        return mutePoints;
    }

    public void setMutePoints(int mutePoints) {
        this.mutePoints = mutePoints;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public String getMuteReason() {
        return muteReason;
    }

    public void setMuteReason(String muteReason) {
        this.muteReason = muteReason;
    }

    public String getBanFrom() {
        return banFrom;
    }

    public void setBanFrom(String banFrom) {
        this.banFrom = banFrom;
    }

    public String getMuteFrom() {
        return muteFrom;
    }

    public void setMuteFrom(String muteFrom) {
        this.muteFrom = muteFrom;
    }
}

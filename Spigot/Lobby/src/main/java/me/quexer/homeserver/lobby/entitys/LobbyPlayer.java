package me.quexer.homeserver.lobby.entitys;

import java.util.List;

public class LobbyPlayer {

    private String uuid;

    private double lastX;

    private double lastY;

    private double lastZ;

    private boolean playerHider;

    private boolean silentHub;

    public LobbyPlayer() {
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isPlayerHider() {
        return playerHider;
    }

    public void setPlayerHider(boolean playerHider) {
        this.playerHider = playerHider;
    }

    public String getUuid() {
        return uuid;
    }

    public void setLastX(double lastX) {
        this.lastX = lastX;
    }

    public void setLastY(double lastY) {
        this.lastY = lastY;
    }

    public void setLastZ(double lastZ) {
        this.lastZ = lastZ;
    }

    public double getLastX() {
        return lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public double getLastZ() {
        return lastZ;
    }

    public boolean isSilentHub() {
        return silentHub;
    }

    public void setSilentHub(boolean silentHub) {
        this.silentHub = silentHub;
    }
}

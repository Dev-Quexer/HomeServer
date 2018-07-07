package me.quexer.homeserver.lobby.entitys;

import java.util.List;

public class LobbyPlayer {
    private String uuid;

    private List<String> gadgets;

    private double lastX;

    private double lastY;

    private double lastZ;

    private String lastTrail;

    private String lastHead;

    private String lastChestplate;

    private boolean playerHider;

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

    public List<String> getGadgets() {
        return gadgets;
    }

    public void setGadgets(List<String> gadgets) {
        this.gadgets = gadgets;
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

    public String getLastTrail() {
        return lastTrail;
    }

    public void setLastTrail(String lastTrail) {
        this.lastTrail = lastTrail;
    }

    public String getLastHead() {
        return lastHead;
    }

    public void setLastHead(String lastHead) {
        this.lastHead = lastHead;
    }

    public String getLastChestplate() {
        return lastChestplate;
    }

    public void setLastChestplate(String lastChestplate) {
        this.lastChestplate = lastChestplate;
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

}

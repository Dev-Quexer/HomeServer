package me.quexer.homeserver.serverapi.utils;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerState;
import me.quexer.homeserver.serverapi.utils.enums.GameState;

public class GameAPI {

    private GameState gameState;
    private String map;
    private String playerXPlayer;

    public GameAPI(String map, String PlayerXPlayer) {
        setGameState(GameState.LOBBY);
        setMap(map);
        setPlayerXPlayer(PlayerXPlayer);
        CloudServer.getInstance().setMotdAndUpdate(map+" - "+PlayerXPlayer);
        CloudServer.getInstance().setServerState(ServerState.LOBBY);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        if(gameState == GameState.PREINGAME) {
            CloudServer.getInstance().setServerState(ServerState.INGAME);
            CloudServer.getInstance().changeToIngame();
        }
        this.gameState = gameState;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPlayerXPlayer() {
        return playerXPlayer;
    }

    public void setPlayerXPlayer(String playerXPlayer) {
        this.playerXPlayer = playerXPlayer;
    }

}

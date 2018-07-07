package me.quexer.homeserver.lobby.manager;

import com.mongodb.client.model.Filters;
import me.quexer.homeserver.lobby.entitys.LobbyPlayer;
import me.quexer.homeserver.serverapi.ServerAPI;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

public class LobbyPlayerManager {

    public LobbyPlayer getUsertFromPlayer(Player player) {
        return (LobbyPlayer) player.getMetadata("lobby").get(0).value();
    }

    public void getLobbyPlayer(Player p, Consumer<LobbyPlayer> consumer) {
        //TQT.getBackendManager().setMetadata(p, "user", user);
        if(p.hasMetadata("lobby")) {
            consumer.accept((LobbyPlayer) p.getMetadata("lobby").get(0).value());
            return;
        }

        ServerAPI.getMongoManager().getCollection("Lobby").find(Filters.eq("uuid", p.getUniqueId().toString())).first((result, t) -> {
            Document document = (Document) result;

            if(document == null) {
                LobbyPlayer lobbyPlayer = new LobbyPlayer();
                lobbyPlayer.setUuid(p.getUniqueId().toString());
                lobbyPlayer.setGadgets(new ArrayList<>());
                lobbyPlayer.setLastChestplate("NONE");
                lobbyPlayer.setLastTrail("NONE");
                lobbyPlayer.setLastHead("NONE");
                lobbyPlayer.setLastX(0);
                lobbyPlayer.setLastY(0);
                lobbyPlayer.setLastZ(0);
                lobbyPlayer.setPlayerHider(false);
                document = ServerAPI.getGson().fromJson(ServerAPI.getGson().toJson(lobbyPlayer), Document.class);

                ServerAPI.getMongoManager().getCollection("Lobby").insertOne(document, (result1, t1) ->{

                    consumer.accept(lobbyPlayer);
                    ServerAPI.getInstance().setMetadata(p, "lobby", lobbyPlayer);

                });

                return;

            } else {
                LobbyPlayer lobbyPlayer = ServerAPI.getGson().fromJson(document.toJson(), LobbyPlayer.class);
                consumer.accept(lobbyPlayer);
                ServerAPI.getInstance().setMetadata(p, "lobby", lobbyPlayer);}
        });
    }

    public void updateLobbyPlayer(LobbyPlayer lobbyPlayer, Consumer<LobbyPlayer> consumer) {

        Document document = ServerAPI.getGson().fromJson(ServerAPI.getGson().toJson(lobbyPlayer), Document.class);

        ServerAPI.getMongoManager().getCollection("Lobby")
                .replaceOne(Filters.eq("uuid", lobbyPlayer.getUuid()), document, (result, t) -> {

                    Player p = Bukkit.getPlayer(UUID.fromString(lobbyPlayer.getUuid()));
                    if(p != null && p.isOnline()) {
                        ServerAPI.getInstance().setMetadata(p, "lobby", lobbyPlayer);
                    }

                    consumer.accept(lobbyPlayer);
                });
    }

}

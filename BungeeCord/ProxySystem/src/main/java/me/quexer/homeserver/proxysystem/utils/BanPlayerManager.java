package me.quexer.homeserver.proxysystem.utils;

import com.mongodb.client.model.Filters;
import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.enitys.BanPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.Document;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class BanPlayerManager {

    public static HashMap<String, BanPlayer> banPlayerHashMap = new HashMap<>();

    public void getBanPlayer(String uuid, Consumer<BanPlayer> consumer) {
        if(ProxySystem.getBungeeCord().getPlayer(UUID.fromString(uuid)) != null && banPlayerHashMap.containsKey(uuid)) {
            consumer.accept(banPlayerHashMap.get(uuid));
            return;
        }

        ProxySystem.getMongoManager().getCollection("Ban").find(Filters.eq("uuid", uuid)).first((document, throwable) -> {
            if(document == null) {
               BanPlayer banPlayer = new BanPlayer();
               banPlayer.setUuid(uuid);
               banPlayer.setBanEnd(-1);
               banPlayer.setBanFrom("NONE");
               banPlayer.setBanPoints(0);
               banPlayer.setBanReason("NONE");
               banPlayer.setMuteEnd(-1);
               banPlayer.setMuteFrom("NONE");
               banPlayer.setMutePoints(0);
               banPlayer.setMuteReason("NONE");

                consumer.accept(banPlayer);
                banPlayerHashMap.put(uuid, banPlayer);

                document = document = ProxySystem.getGson().fromJson(ProxySystem.getGson().toJson(banPlayer), Document.class);

                ProxySystem.getMongoManager().getCollection("Ban").insertOne(document, (aVoid, throwable1) -> {});

                return;
            } else {
                BanPlayer banPlayer = ProxySystem.getGson().fromJson(document.toJson(), BanPlayer.class);
                consumer.accept(banPlayer);
                banPlayerHashMap.put(uuid, banPlayer);
                return;
            }
        });
    }

    public void updateUser(BanPlayer banPlayer, Consumer<BanPlayer> consumer) {

        Document document = ProxySystem.getGson().fromJson(ProxySystem.getGson().toJson(banPlayer), Document.class);

        ProxySystem.getMongoManager().getCollection("Ban")
                .replaceOne(Filters.eq("uuid", banPlayer.getUuid()), document, (result, t) -> {

                    ProxiedPlayer player = ProxySystem.getBungeeCord().getPlayer(UUID.fromString(banPlayer.getUuid()));
                    if(player != null && player.isConnected()) {
                        banPlayerHashMap.put(banPlayer.getUuid(), banPlayer);
                    }

                    consumer.accept(banPlayer);
                });
    }


}

package me.quexer.homeserver.serverapi.utils;

import com.mongodb.client.model.Filters;
import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.utils.FireworkBuilder;
import me.quexer.homeserver.serverapi.quickyapi.utils.TitleBuilder;
import me.quexer.homeserver.serverapi.utils.entitys.User;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class UserManager {

    public void getUser(OfflinePlayer player, Consumer<User> consumer) {
        if(player.isOnline() == player.getPlayer().hasMetadata("user")) {
            consumer.accept((User) player.getPlayer().getMetadata("user").get(0).value());
            return;
        }

        ServerAPI.getMongoManager().getCollection("Users").find(Filters.eq("uuid")).first((document, throwable) -> {
            if(document == null) {
                User user = new User();
                user.setCoins(1000);
                user.setElo(100);
                user.setLastPlayed(System.currentTimeMillis());
                user.setLevel(1);
                user.setNick(false);
                user.setNextDailyReward(System.currentTimeMillis());
                user.setUuid(player.getUniqueId().toString());
                user.setXp(0);

                consumer.accept(user);
                ServerAPI.getInstance().setMetadata(player.getPlayer(), "user", user);

                ServerAPI.getMongoManager().getCollection("Users").insertOne(document, (aVoid, throwable1) -> {
                    player.getPlayer().sendMessage(ServerAPI.getPrefix()+"§7Du wurdest §aerfolgreich §7in der Datenbank erstellt§8.");
                });

                return;
            } else {
                User user = ServerAPI.getGson().fromJson(document.toJson(), User.class);
                consumer.accept(user);
                ServerAPI.getInstance().setMetadata(player.getPlayer(), "user", user);
                return;
            }
        });
    }

    public void updateUser(User user, Consumer<User> consumer) {

        Document document = ServerAPI.getGson().fromJson(ServerAPI.getGson().toJson(user), Document.class);

        ServerAPI.getMongoManager().getCollection("Users")
                .replaceOne(Filters.eq("uuid", user.getUuid()), document, (result, t) -> {

                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(user.getUuid()));
                    if(player != null && player.isOnline()) {
                        ServerAPI.getInstance().setMetadata(player.getPlayer(), "user", user);
                    }

                    consumer.accept(user);
                });
    }

    public void addXp(User user, int xp) {
        user.setXp(user.getXp()+xp);
        while (user.getXp() >= 5000) {
            user.setLevel(user.getLevel()+1);
            user.setXp(user.getXp()-5000);
            long newCoins = 1850+new Random().nextInt(500);
            user.setCoins(user.getCoins()+newCoins);
            Player player = Bukkit.getPlayer(UUID.fromString(user.getUuid()));
            if(player != null) {
                player.sendMessage("");
                player.sendMessage(ServerAPI.getPrefix() + "§7Du hast §e" + newCoins + " §6Coins §7erhalten");
                player.sendMessage(ServerAPI.getPrefix()+"§7Du hast das Level §e" + user.getLevel() + " §8[§a" + user.getXp() + "§8/§a5000§8]");
                player.sendMessage("");
                new FireworkBuilder(player.getLocation(), FireworkEffect.Type.BALL, Color.YELLOW, 100);
                TitleBuilder.sendTitle(player, 10, 80, 40, "§b§bLEVEL UP§8!", "§7Du bist jetzt Level §b" + user.getLevel());
            }
        }
    }

}

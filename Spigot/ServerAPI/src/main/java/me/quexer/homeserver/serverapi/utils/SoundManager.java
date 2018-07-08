package me.quexer.homeserver.serverapi.utils;

import me.quexer.homeserver.serverapi.quickyapi.api.game.AsyncTask;
import me.quexer.homeserver.serverapi.utils.enums.SoundType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {

    public void play(Player p, SoundType soundType) {
        new AsyncTask(() -> {
           switch (soundType) {
               case BAD:
                   p.playSound(p.getLocation(), Sound.NOTE_BASS, 0.3F, 10);
                   break;
               case NORMAL:
                   p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 0.3F, 0.3F);
                   break;
               case GOOD:
                   p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                   break;

           }
        });
    }

}

package me.quexer.homeserver.serverapi.quickyapi.api.game;

import me.quexer.homeserver.serverapi.ServerAPI;
import me.quexer.homeserver.serverapi.quickyapi.utils.TitleBuilder;
import org.bukkit.Bukkit;

public class Countdown {

    private int high;
    private int sched;
    private Runnable onFinish;
    private boolean silent;
    private DisplayType displayType;
    private String msg;


    public Countdown(int high, Runnable finish, boolean silent, DisplayType displayType, String msg){
        setHigh(high);
        setOnFinish(finish);
        setSilent(silent);
        setDisplayType(displayType);
        setMsg(msg);

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(ServerAPI.getInstance(), () -> {

            if(high == 0) {
                finish.run();
            } else {
                if(!silent) {
                    switch (displayType) {
                        case CHAT:
                            Bukkit.getOnlinePlayers().forEach(o -> {
                                o.sendMessage(msg);
                            });
                            break;
                        case TITLE:
                            Bukkit.getOnlinePlayers().forEach(o -> {
                                TitleBuilder.sendTitle(o, 0, 20, 60, "§a", "§e"+high);
                            });
                            break;
                        case ACTIONBAR:
                            Bukkit.getOnlinePlayers().forEach(o -> {
                                TitleBuilder.sendActionBar(o, "§7Countdown §8 §e"+high);
                            });
                            break;
                    }
                }
            }

        }, 1, 20);


    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public void setDisplayType(DisplayType displayType) {
        this.displayType = displayType;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getSched() {
        return sched;
    }

    public void setSched(int sched) {
        this.sched = sched;
    }

    public Runnable getOnFinish() {
        return onFinish;
    }

    public void setOnFinish(Runnable onFinish) {
        this.onFinish = onFinish;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }
}

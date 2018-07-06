package me.quexer.homeserver.proxysystem.commands;

import me.quexer.homeserver.proxysystem.ProxySystem;
import me.quexer.homeserver.proxysystem.utils.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BanCMD extends Command {


    public BanCMD() {
        super("ban", "ban.mute", "punish", "pun");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        //pun [Player] [Reason]
        if (s.hasPermission("ban.mute")) {
            if (args.length == 2) {


                        ProxySystem.getBanPlayerManager().getBanPlayer(UUIDFetcher.getUUID(args[0]).toString(), banPlayer -> {
                            ProxySystem.getBanManager().isBanned(banPlayer, banned -> {
                                if (!banned) {

                            String id = args[1];
                            String name = args[0];
                            int BannPoints = banPlayer.getBanPoints();

                            long dauer = 1;
                            if (BannPoints >= 8) {
                                dauer = -1;
                            } else if (BannPoints >= 6) {
                                dauer = dauer * 3;
                            } else if (BannPoints >= 5) {
                                dauer = dauer * 2;
                            }

                            long finalDauer = dauer;
                            System.out.println(dauer);
                            ProxySystem.getBungeeCord().getScheduler().runAsync(ProxySystem.getInstance(), () -> {
                                switch (id) {


                                    case "1":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Hacking", s.getName(), (finalDauer == -1) ? -1 : 24 * 30 * finalDauer);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;

                                    case "2":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Trolling", s.getName(), (finalDauer == -1) ? -1 : 24 * 4 * finalDauer);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;
                                    case "3":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Teaming", s.getName(), (finalDauer == -1) ? -1 : 24 * 4 * finalDauer);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;

                                    case "4":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Bugusing", s.getName(), (finalDauer == -1) ? -1 : 24 * 7 * finalDauer);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;

                                    case "5":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Skin", s.getName(), (finalDauer == -1) ? -1 : 24 * 14 * finalDauer);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;

                                    case "6":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Name", s.getName(), (finalDauer == -1) ? -1 : 24 * 30 * finalDauer);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;

                                    case "7":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Report-Ausnutzung", s.getName(), (finalDauer == -1) ? -1 : 24 * 2 * finalDauer);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;

                                    case "8":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Bannumgehung", s.getName(), -2);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;

                                    case "9":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getBanManager().banPlayer(banPlayer, "Hausverbot", s.getName(), -2);
                                        } else {
                                            s.sendMessage(ProxySystem.getBanPrefix() + "§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;




                                   case "10":
                                        ProxySystem.getMuteManager().MutePlayer(banPlayer, "Beleidigung", s.getName(), (finalDauer == -1)    ? -1 :  24 * 3 * finalDauer    );
                                        break;

                                    case "11":
                                        ProxySystem.getMuteManager().MutePlayer(banPlayer, "Rassismus", s.getName(), (finalDauer == -1)    ? -1 :  24 * 7 * finalDauer    );
                                        break;

                                    case "12":
                                        ProxySystem.getMuteManager().MutePlayer(banPlayer, "Werbung", s.getName(), (finalDauer == -1)    ? -1 :  24 * 2 * finalDauer    );
                                        break;

                                    case "13":
                                        ProxySystem.getMuteManager().MutePlayer(banPlayer, "Spamming", s.getName(), (finalDauer == -1)    ? -1 :  12 * finalDauer    );
                                        break;

                                    case "14":
                                        ProxySystem.getMuteManager().MutePlayer(banPlayer, "Provokation", s.getName(), (finalDauer == -1)    ? -1 :  12 * finalDauer    );
                                        break;

                                    case "15":
                                        if (s.hasPermission("ban.banban")) {
                                            ProxySystem.getMuteManager().MutePlayer(banPlayer, "Schweigepfilcht", s.getName(), -1);
                                        } else {
                                            s.sendMessage(ProxySystem.getMutePrefix()+"§cFür diesen Grund darfst du nicht bannen§8!");
                                        }
                                        break;


                                }
                            });
                        }
                            });
                    });
                } else {
                    s.sendMessage(ProxySystem.getBanPrefix() + "§7Benutze§8: §c/ban <Spieler> <Grund>");

                    TextComponent text = new TextComponent();
                    text.setText(ProxySystem.getBanPrefix() + "§7Für eine Liste mit Gründen §8["+ChatColor.GREEN +"Klicke hier§8]");
                    text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reasons"));
                    s.sendMessage(text);

                }

            } else {
                s.sendMessage(ProxySystem.getBanPrefix() + "§cDazu hast du keine Rechte!");
            }


        }
}

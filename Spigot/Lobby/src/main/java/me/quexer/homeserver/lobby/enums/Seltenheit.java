package me.quexer.homeserver.lobby.enums;

public enum Seltenheit {

    LEGENDARY("§6§lLEGENDARY"),
    ULTIMATE("§9§lULTIMATE"),
    EPIC("§5§lEPIC"),
    RARE("§b§lRARE"),
    COMMON("§a§lCOMMON");

    private String name;

    Seltenheit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

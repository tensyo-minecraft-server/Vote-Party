package moe.nmkmn.vote_party.utilts;

import java.util.List;

public class Reward {
    private final int number;
    private final String rarity;
    private final String message;
    private final List<String> command;

    public Reward(int number, String rarity, String message, List<String> command) {
        this.number = number;
        this.rarity = rarity;
        this.message = message;
        this.command = command;
    }

    public int getNumber() {
        return number;
    }

    public String getRarity() {
        return rarity;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getCommand() {
        return command;
    }
}
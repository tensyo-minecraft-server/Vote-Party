package moe.nmkmn.vote_party.utilts;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class RewardManager {
    private final List<Reward> rewards;

    private RewardManager(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public static RewardManager loadFromYaml(FileConfiguration config) {
        List<Reward> rewards = new ArrayList<>();

        ConfigurationSection rewardsSection = config.getConfigurationSection("Rewards");
        if (rewardsSection != null) {
            for (String key : rewardsSection.getKeys(false)) {
                ConfigurationSection rewardInfo = rewardsSection.getConfigurationSection(key);
                if (rewardInfo != null) {
                    Map<String, Object> rewardData = rewardInfo.getValues(false);

                    String rarity = (String) rewardData.get("rarity");
                    String message = (String) rewardData.get("message");
                    @SuppressWarnings("unchecked")
                    List<String> command = (List<String>) rewardData.get("command");

                    if (key.contains("-")) {
                        Map<String, Integer> range = parseRangeKey(key);
                        int start = range.get("start");
                        int end = range.get("end");

                        for (int number = start; number <= end; number++) {
                            rewards.add(new Reward(number, rarity, message, command));
                        }
                    } else {
                        int number = extractNumberFromKey(key);
                        rewards.add(new Reward(number, rarity, message, command));
                    }
                }
            }
        }

        return new RewardManager(rewards);
    }

    public Reward getReward(int number) {
        if (rewards.isEmpty()) {
            return null;
        }

        return rewards.get(number);
    }

    public Reward getRandomReward() {
        if (rewards.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * rewards.size());
        return rewards.get(randomIndex);
    }

    private static Map<String, Integer> parseRangeKey(String key) {
        Map<String, Integer> result = new HashMap<>();
        String[] parts = key.split("-");

        if (parts.length == 1) {
            int singleValue = extractNumberFromKey(parts[0]);
            result.put("start", singleValue);
            result.put("end", singleValue);
        } else if (parts.length == 2) {
            int startValue = extractNumberFromKey(parts[0]);
            int endValue = extractNumberFromKey(parts[1]);
            result.put("start", startValue);
            result.put("end", endValue);
        } else {
            throw new IllegalArgumentException("Invalid range key format: " + key);
        }

        return result;
    }

    private static int extractNumberFromKey(String key) {
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in key: " + key);
        }
    }
}

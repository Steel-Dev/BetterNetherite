package com.github.steeldev.betternetherite.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
    private static final String PREFIX = "&7[&6Better&cNetherite&7] ";

    public static Random rand = new Random();

    public static String colorize(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            final net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = string.substring(0, matcher.start());
            final String after = string.substring(matcher.end());
            string = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean chanceOf(int chance) {
        return rand.nextInt(100) < chance;
    }

    public static boolean chanceOf(float chance) {
        return rand.nextFloat() < chance;
    }

    // Just to make, for example; 'DIAMOND_SWORD' look nicer in a string
    //  it'll look like 'Diamond Sword' instead. This is mainly used
    //  for printing an item in chat, and have it not look so
    //  ugly and such for users.
    public static String formalizedString(String string) {
        String[] itemSplit = string.toLowerCase().split("_");
        StringBuilder finalIt = new StringBuilder();
        for (int i = 0; i < itemSplit.length; i++) {
            finalIt.append(itemSplit[i].substring(0, 1).toUpperCase() + itemSplit[i].substring(1));
            if (i < itemSplit.length - 1)
                finalIt.append(" ");
        }
        return finalIt.toString();
    }

    public static void log(String log) {
        Bukkit.getConsoleSender().sendMessage(colorize(PREFIX + log));
    }
}

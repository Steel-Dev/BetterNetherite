package com.github.steeldev.betternetherite.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class Message {
    // Messages

    public static final Message MONSTRORVM_FOUND = get("&aFound &2Monstrorvm %s&a! Custom mobs and items enabled!");
    public static final Message MONSTRORVM_FOUND_DISABLED = get("&cFound &2Monstrorvm %s, but its disabled! Custom mobs and items disabled!");
    public static final Message MONSTRORVM_NOT_FOUND = get("&cCould not find &2Monstrorvm &con the server! Custom mobs and items disabled!");

    public static final Message PLUGIN_ENABLED = get("&aSuccessfully enabled &2%s &ain &e%s Seconds&a.");
    public static final Message PLUGIN_DISABLED = get("&cSuccessfully disabled!");

    public static final Message PLUGIN_CHECKING_FOR_UPDATE = get("&e&oChecking for a new version...");
    public static final Message PLUGIN_ON_LATEST = get("&2&oYou are on the latest version! &7&o(%s)");
    public static final Message PLUGIN_ON_IN_DEV_PREVIEW = get("&e&oYou are on an in-dev preview version! &7&o(%s)");
    public static final Message PLUGIN_NEW_VERSION_AVAILABLE_CONSOLE = get("&a&oA new version is available! &7&o(Current: %s, Latest: %s) &a&oYou can download the latest version here: &e&o%s");
    public static final Message PLUGIN_NEW_VERSION_AVAILABLE_CHAT = get("&a&oA new version is available! &7&o(Current: %s, Latest: %s)");
    public static final Message PLUGIN_NEW_VERSION_AVAILABLE_CHAT_CLICK = get("&6&lClick here to update");
    public static final Message PLUGIN_UPDATE_CHECK_FAILED = get("&4Failed to check for updates: &c%s");

    public static final Message PLUGIN_RELOADED = get("&aSuccessfully reloaded all configurations! You may need to restart for some changes to take full effect.");

    public static final Message SERVER_USES_RESOURCE_PACK = get("&2This server has custom items, and custom textures. Which require a resource pack to have the best experience. You have been sent it.");
    public static final Message RESOURCE_PACK_DECLINED = get("&2You have denied the custom resource pack. Please accept it for the best experience! If you did not deny it, please ensure Resource Packs are set to either Enabled or Prompt in the servers settings in your server list.");
    public static final Message RESOURCE_PACK_FAILED = get("&2The custom resource pack has failed to download!");
    public static final Message RESOURCE_PACK_ACCEPTED = get("&2You have accepted the custom resource pack! enjoy!");
    public static final Message RESOURCE_PACK_LOADED = get("&2Custom Resource pack successfully loaded.");

    public static final Message RESOURCE_PACK_DECLINED_KICKED = get("&2You have denied the custom resource pack, this server has a forced resource pack, please accept it for the best experience! If you did not deny it, please ensure Resource Packs are set to either Enabled or Prompt in the servers settings in your server list.");
    public static final Message RESOURCE_PACK_FAILED_KICKED = get("&2The custom resource pack has failed to download! This server has a forced resource pack to ensure the best experience. Please try again!");

    public static final Message LOADING_NBT_API = get("&aLoading NBT-API...");
    public static final Message NBT_API_LOADED = get("&aSuccessfully loaded NBT-API!");

    public static final Message STARTING_METRICS = get("&7Starting Metrics. Opt-out using the global bStats config.");

    public static final Message COMPARING_RESOURCE_PACK = get("&2Comparing most recent Resource Pack URL with Config URl..");
    public static final Message RESOURCE_PACK_LINKS_DONT_MATCH = get("&2Current Resource Pack config URL and Latest do not match. Updating it!");
    public static final Message RESOURCE_PACK_LINKS_MATCH = get("&2Current Resource Pack config URL and Latest match!");


    public static final Message SMITHING_TABLE_DISABLED = get("&cSmithing Table upgrading is disabled.");
    public static final Message SMITHING_NOT_ENOUGH_MATS_UPGRADE = get("&cYou require &ex%s &6%s &cin the second slot to upgrade your &6%s&c!");
    public static final Message SMITHING_UPGRADE_SUCCESS = get("&aYou have successfully upgraded your &6%s &ato &e%s&a for &6x%s &e%s(s)&a!");

    public static final Message UPGRADE_PACK_UPGRADE_SUCCESS = get("&aYou have successfully upgraded your &6%s &ato &e%s&a using an Upgrade Pack!");

    public static final Message X_INFLICTED_X_WITH_X = get("&6%s &cinflicted &e%s &cwith &4%s&c!");

    public static final Message SHRINE_INVALID_ITEM = get("&cYour current held item cannot be used in this &6%s&c.");
    public static final Message SHRINE_USED_POTION = get("&aYou have used a &6%s &aand activated &e%s&a. You are now &b%s&a.");
    public static final Message SHRINE_USED_ITEM = get("&aYou have used a &6%s &aand activated &e%s&a. Your current held item is now &b%s&a.");
    public static final Message SHRINE_ITEM_ALREADY_EFFECTED = get("&cYour current held item has already been effected with &e%s&c!");
    public static final Message SHRINE_CANT_USE_ITEM_NOT_DAMAGED = get("&cYour current held item is already on full durability and cannot be charmed with &e%s&c!");
    public static final Message SHRINE_BUILT_INCORRECTLY = get("&cThis &6%s &chas been built incorrectly, it will not function!");
    public static final Message SHRINE_CHARGES_MUST_BE_ON_CHAINS = get("&cThis &6%s &chas been built correctly, but the charges must be placed upon chains.");
    public static final Message SHRINE_NO_CHARGES = get("&cThis &6%s &cis out of charges! Recharge it by replacing the &e%s&c on the chains.");
    public static final Message SHRINE_CHARGES_REMAINING = get("&e&o%s &2Charges remain..");
    public static final Message SHRINE_CHARGES_LOW = get("&cThis shrine has very little charges left, be careful, it has become unstable and has a chance to explode! Recharge it by replacing the &e%s&c on the chains.");
    public static final Message SHRINE_CANT_USE_IN_WORLD = get("&cThis &6%s &ccannot be used in this world!");

    // Message code
    private final String message;

    public Message(String message) {
        this.message = message;
    }

    private static Message get(String message) {
        return new Message(message);
    }

    public void sendActionBar(@Nullable CommandSender receiver, Object... params) {
        if (!(receiver instanceof Player)) return;
        Util.sendActionBar((Player) receiver, message, params);
    }

    public void sendTitle(String title, @Nullable CommandSender receiver, Object... params) {
        if (!(receiver instanceof Player)) return;
        Util.sendActionBar((Player) receiver, message, params);
    }

    public void broadcast(boolean withPrefix, Object... params) {
        Util.broadcast((withPrefix) ? Util.main.config.PREFIX + message : message, params);
    }

    public void send(@Nullable CommandSender receiver, boolean withPrefix, Object... params) {
        String finalMsg;
        if (withPrefix) finalMsg = Util.main.config.PREFIX + message;
        else finalMsg = message;
        Util.sendMessage(receiver, finalMsg, params);
    }

    public void log(Object... params) {
        Util.log(message, params);
    }

    public String toString() {
        return Util.colorize(this.message);
    }
}


package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.Message;

import java.io.IOException;

public class BNResourcePackManager {
    static final String latestResourcePackURL = "https://www.dropbox.com/s/27b7eaufqh3rxg2/BetterNetherite-ResourcePack-v2.zip?dl=1";
    static BetterNetherite main = BetterNetherite.getInstance();

    public static void checkResourcePack() throws IOException {
        if (main.config.RESOURCE_PACK_ENABLED &&
                main.config.RESOURCE_PACK_AUTO_UPDATE) {
            Message.COMPARING_RESOURCE_PACK.log();
            if (!main.config.RESOURCE_PACK_URL.equals(latestResourcePackURL)) {
                Message.RESOURCE_PACK_LINKS_DONT_MATCH.log();
                main.config.setString("ResourcePack.URL", latestResourcePackURL);
                main.loadConfigurations();
                return;
            }
            Message.RESOURCE_PACK_LINKS_MATCH.log();
        }
    }
}

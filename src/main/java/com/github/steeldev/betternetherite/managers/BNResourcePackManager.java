package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;

import java.io.IOException;

public class BNResourcePackManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    static final String latestResourcePackURL = "https://www.dropbox.com/s/jdpz4bwo89zyuoo/BetterNetherite-ResourcePack-v1.zip?dl=1";

    public static void checkResourcePack() throws IOException {
        if(BetterConfig.RESOURCE_PACK_ENABLED &&
                BetterConfig.RESOURCE_PACK_AUTO_UPDATE){
            main.getLogger().info("&2Comparing most recent Resource Pack URL with Config URl..");
            if(!BetterConfig.RESOURCE_PACK_URL.equals(latestResourcePackURL)) {
                main.getLogger().info("&2Current Resource Pack config URL and Latest do not match. Updating it!");
                BetterConfig.setString("ResourcePack.URL", latestResourcePackURL);
                main.loadCustomConfigs();
                main.getLogger().info("&2Resource Pack config URL successfully updated!");
                return;
            }
            main.getLogger().info("&2Current Resource Pack config URL and Latest match!");
        }
    }
}

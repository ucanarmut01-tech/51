package com.girismod;

import org.bukkit.plugin.java.JavaPlugin;

public final class GirisWebhookPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();

		String webhookUrl = getConfig().getString("webhook-url", "");
		if (webhookUrl.isBlank() || webhookUrl.contains("YOUR_WEBHOOK")) {
			getLogger().warning("webhook-url ayarlanmamış! plugins/GirisWebhook/config.yml dosyasını kontrol edin.");
		}

		getServer().getPluginManager().registerEvents(new ChatCommandListener(this), this);

		getLogger().info("GirisWebhook etkinleştirildi. Yeni bir /giriş komutu KAYDETMİYOR, sadece dinliyor.");
	}
}

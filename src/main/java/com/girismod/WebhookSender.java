package com.girismod;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;

public final class WebhookSender {

	private static final HttpClient CLIENT = HttpClient.newBuilder()
		.connectTimeout(Duration.ofSeconds(10))
		.build();

	private WebhookSender() {
	}

	public static void send(GirisWebhookPlugin plugin, String playerName, String rawText) {
		String webhookUrl = plugin.getConfig().getString("webhook-url", "");
		if (webhookUrl.isBlank() || webhookUrl.contains("YOUR_WEBHOOK")) {
			return;
		}

		String content = "**" + playerName + "**: " + rawText;
		if (content.length() > 2000) {
			content = content.substring(0, 2000);
		}

		String json = "{\"content\":\"" + escapeJson(content) + "\"}";

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(webhookUrl))
			.header("Content-Type", "application/json")
			.timeout(Duration.ofSeconds(10))
			.POST(HttpRequest.BodyPublishers.ofString(json))
			.build();

		CLIENT.sendAsync(request, HttpResponse.BodyHandlers.discarding())
			.whenComplete((response, error) -> {
				if (error != null) {
					plugin.getLogger().log(Level.WARNING, "Webhook gönderilemedi.", error);
				} else if (response.statusCode() >= 300) {
					plugin.getLogger().warning("Webhook " + response.statusCode() + " döndürdü.");
				}
			});
	}

	private static String escapeJson(String text) {
		StringBuilder sb = new StringBuilder();
		for (char c : text.toCharArray()) {
			switch (c) {
				case '"' -> sb.append("\\\"");
				case '\\' -> sb.append("\\\\");
				case '\n' -> sb.append("\\n");
				case '\r' -> sb.append("\\r");
				case '\t' -> sb.append("\\t");
				default -> {
					if (c < 0x20) {
						sb.append(String.format("\\u%04x", (int) c));
					} else {
						sb.append(c);
					}
				}
			}
		}
		return sb.toString();
	}
}

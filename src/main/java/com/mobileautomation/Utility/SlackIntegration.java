package com.mobileautomation.Utility;

import java.io.IOException;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

public class SlackIntegration {
	// Configure your Slack webhook URL here
	String SlackWebHook = "YOUR_SLACK_WEBHOOK_URL";
	String channelName = "automation-reports";
	String botUserOAuthAccessToken = "YOUR_BOT_TOKEN";

	public void sendMessagetoSlack(String message) throws IOException {
		if (SlackWebHook != null && !SlackWebHook.equals("YOUR_SLACK_WEBHOOK_URL")) {
			Payload payload = Payload.builder().text(message).build();
			Slack.getInstance().send(SlackWebHook, payload);
		}
	}

	public String SlackMessagetosend(int totaltest, int passed, int failed, int skipped) {
		String message = "Mobile Automation Test Results \n" + "============================= \n"
				+ "Please find below the automation execution result:\n" + "Total TestCases: " + totaltest + "\n"
				+ "Passed: " + passed + "\n" + "Failed: " + failed + "\n" + "Skipped: " + skipped + "\n";
		return message;
	}
}


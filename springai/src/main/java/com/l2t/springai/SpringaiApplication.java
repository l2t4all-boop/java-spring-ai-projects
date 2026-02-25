package com.l2t.springai;

import com.l2t.springai.config.ChatClientFactory;
import com.l2t.springai.config.LlmProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.chat.client.autoconfigure.ChatClientAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { ChatClientAutoConfiguration.class })
public class SpringaiApplication implements CommandLineRunner {

	@Autowired
	private ChatClientFactory chatClientFactory;
	public static void main(String[] args) {
		SpringApplication.run(SpringaiApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(SpringaiApplication.class);

	private static final String[] API_KEY_ENV_VARS = {
			"ANTHROPIC_API_KEY", "OPENAI_API_KEY", "GOOGLE_AI_API_KEY"
	};

	@Override
	public void run(String... args) throws Exception {
		for (String envVar : API_KEY_ENV_VARS) {
			String value = System.getenv(envVar);
			log.info("ENV {} = {}", envVar, maskKey(value));
		}

		for (LlmProvider llmProvider : LlmProvider.values()) {
			chatClientFactory.logProviderDetails(llmProvider);
		}
	}

	private static String maskKey(String key) {
		if (key == null || key.isBlank()) return "<NOT SET>";
		if (key.length() <= 8) return "****";
		return key.substring(0, 4) + "****" + key.substring(key.length() - 4);
	}
}

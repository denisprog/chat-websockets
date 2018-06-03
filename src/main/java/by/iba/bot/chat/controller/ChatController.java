package by.iba.bot.chat.controller;

import by.iba.bot.chat.Constants;
import by.iba.bot.chat.component.MessageManager;
import by.iba.bot.chat.domain.BotRequest;
import by.iba.bot.chat.domain.BotResponse;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {
	
	@Autowired
	private MessageManager messageManager;
	
	@MessageMapping("/message")
	@SendToUser
	public BotResponse getMessages(BotRequest message) {
		System.out.println(message);
		BotResponse response = new BotResponse();

		response.setMessageRequest(message);
		response.setFrom("Bot");
		response.setResponseMessage("Machine response");
		return response;
	}
	
	@SubscribeMapping("/user/queue/message")
	public BotResponse subscribed() {
		String message = messageManager.getValueByKey(Constants.RedisStorage.WELCOME_ID);
		BotResponse response = new BotResponse();
		response.setFrom("Bot");
		response.setResponseMessage(message);
		return response;
	}
	
}
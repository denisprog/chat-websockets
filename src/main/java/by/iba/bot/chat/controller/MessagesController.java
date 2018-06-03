package by.iba.bot.chat.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import by.iba.bot.chat.component.MessageManager;
import by.iba.bot.chat.domain.MessageContent;

@RestController()
public class MessagesController {
	
	@Autowired
	private MessageManager messageManager;
	
	
	@RequestMapping(value= "/admin/getMessages", method=RequestMethod.GET)
	public List<MessageContent> getMessages() {
		return messageManager.getMessages();
	}
	
	@RequestMapping(value= "/admin/updateMessages", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<MessageContent> updateMessages(@RequestBody List<MessageContent> messages) {
		return messageManager.updateMessages(messages);
	}
	
	
}
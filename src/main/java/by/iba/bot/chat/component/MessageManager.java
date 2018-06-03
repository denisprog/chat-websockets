package by.iba.bot.chat.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import by.iba.bot.chat.Constants;
import by.iba.bot.chat.dao.MessageDAO;
import by.iba.bot.chat.domain.MessageContent;

@Service
public class MessageManager {
	
	@Autowired
	private MessageDAO messageDAO;
	
	public String getValueByKey(String messageKey) {
		return messageDAO.getValueByKey(messageKey);
	}
	
	public List<MessageContent> getMessages() {
		//Get welcome message
		List<MessageContent> messages = new ArrayList<>();
		String message = this.getValueByKey(Constants.RedisStorage.WELCOME_ID);
		MessageContent welcomeMessageContent = new MessageContent(Constants.RedisStorage.WELCOME_ID, Constants.RedisStorage.WELCOME_NAME, message);
		messages.add(welcomeMessageContent);
		
		//Get all other messages
		Set<String> ids = this.lineElementsToSet(messageDAO.getValueByKey(Constants.RedisStorage.MESSAGE_IDS));
		Map<String, String> messageValues = this.getMessageValues(ids);
		Map<String, String> textValues = this.getTextValues(ids);
		messages.addAll(this.combineMessagesAndTexts(messageValues, textValues));
		return messages;
	}
	
	public List<MessageContent> updateMessages(List<MessageContent> messages) {
		Set<String> ids = this.lineElementsToSet(messageDAO.getValueByKey(Constants.RedisStorage.MESSAGE_IDS));
		Map<String, String> textValues = this.getTextValues(ids);
		Map<String, String> redisKeyTextValues = new HashMap<>();
		messages.forEach(mesContent->{
			if(textValues.get(mesContent.getId())!=null) {
				redisKeyTextValues.put(this.createRedisTextKey(mesContent.getId()), mesContent.getMessageText());
			}else if(Constants.RedisStorage.WELCOME_ID.equals(mesContent.getId())) {
				redisKeyTextValues.put(Constants.RedisStorage.WELCOME_ID, mesContent.getMessageText());
			}
		});		
		messageDAO.updateValuesByKeys(redisKeyTextValues);
		return this.getMessages();
	}
	
	
	private Set<String> lineElementsToSet(String idsString){
		Set<String> ids = new HashSet<>((Arrays.asList(idsString.split(","))));
		ids = ids.stream().map(item -> item.trim()).collect(Collectors.toSet());
		return ids;
	}
	
	private Map<String, String>getMessageValues(Set<String> keys){
		Map<String, String> messageKeyValues = new HashMap<>();
		keys.forEach(id ->	{
				messageKeyValues.put(id, this.messageDAO.getValueByKey(this.createRedisNameKey(id)));});
		
		return messageKeyValues;
	}
	
	private Map<String, String>getTextValues(Set<String> keys){
		Map<String, String> messageKeyValues = new HashMap<>();
		keys.forEach(id ->	{
				messageKeyValues.put(id, this.messageDAO.getValueByKey(this.createRedisTextKey(id)));});
		
		return messageKeyValues;
	}
	
	private List<MessageContent> combineMessagesAndTexts(Map<String, String> messages, Map<String, String> texts){
		List<MessageContent> messageContents = new ArrayList<>();
		messages.forEach((key, value)->{
			MessageContent mesContent = new MessageContent();
			mesContent.setId(key);
			mesContent.setMessageName(value);
			mesContent.setMessageText(texts.get(key));
			messageContents.add(mesContent);
		});
		return messageContents;
	}
	
	private String createRedisNameKey(String id) {
		return "Message:"+id+":name";
	}
	
	private String createRedisTextKey(String id) {
		return "Message:"+id+":text";
	}
}
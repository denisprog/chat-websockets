package by.iba.bot.chat.dao;

import java.util.Map;

public interface MessageDAO {
	
	public String getValueByKey(String key);
	/**
	 * 
	 * @param messages
	 * String - key that is used for update, MessageContent - data that is used for update
	 */
	public void updateValuesByKeys(Map<String, String> messageTexts);


}

package by.iba.bot.chat.domain;

public class MessageContent {
	
	private String id;
	private String messageName;
	private String messageText;
	
	
	
	public MessageContent() {
		super();
	}



	public MessageContent(String id, String messageName, String messageText) {
		super();
		this.id = id;
		this.messageName = messageName;
		this.messageText = messageText;
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	

}

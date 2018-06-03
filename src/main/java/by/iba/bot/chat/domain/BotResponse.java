package by.iba.bot.chat.domain;

public class BotResponse {

    private BotRequest messageRequest;
    private String responseMessage;
    private String from;

    public BotRequest getMessageRequest() {
        return messageRequest;
    }

    public void setMessageRequest(BotRequest messageRequest) {
        this.messageRequest = messageRequest;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "messageRequest=" + messageRequest +
                ", responseMessage='" + responseMessage + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}

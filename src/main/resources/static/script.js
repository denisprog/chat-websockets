function connect() {
	var socket = new SockJS('/chat-messaging');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log("connected: " + frame);
		
        stompClient.subscribe('/user/queue/message', function(response) {
        			console.log("response.body= " + response.body);
            		var data = JSON.parse(response.body);
            		if(data.messageRequest!=null){
            			draw("right", data.messageRequest.message);
            		}
                	draw("left", data.responseMessage);
                	scrollChatToBottom();
        });
	});
}

function scrollChatToBottom(){
	$(".messages").animate({ scrollTop: $('.messages').prop("scrollHeight")}, 1000);
}

function disconnect(){
	stompClient.disconnect();
}
function sendMessage(){
	stompClient.send("/message", {}, JSON.stringify({'message': $("#message_input_value").val()}));
    $("#message_input_value").val('');
}
function draw(side, text) {
	console.log("drawing...");
    var $message;
    $message = $($('.message_template').clone().html());
    $message.addClass(side).find('.text').html(text);
    $('.messages').append($message);
    return setTimeout(function () {
        return $message.addClass('appeared');
    }, 0);

}

$( document ).ready(function() {
    connect();
});
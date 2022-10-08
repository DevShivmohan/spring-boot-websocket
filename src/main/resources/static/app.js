var ws;
var isConnected=false;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	isConnected=connected;
}

function connect() {
	ws = new WebSocket('ws://192.168.142.62:8080/user');
	ws.onmessage = function(data) {
	    console.log(data.data);
		showMessage(data.data);
	}
	setConnected(true);
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	setConnected(false);
	console.log("Websocket is in disconnected state");
	isConnected=false;
}

function sendData() {
	var data = JSON.stringify({
		'message' : $("#user").val(),
		'channel':'user'
	})
	if(isConnected)
	    ws.send(data);
	else
	    alert("Please connect first");
}

function showMessage(message) {
	$("#helloworldmessage").append("<tr><td> " + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendData();
	});
});

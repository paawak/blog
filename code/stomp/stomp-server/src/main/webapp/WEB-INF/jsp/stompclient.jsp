<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	function connect() {
		console.log("trying to connect to websocket...");
		var webScocketClient = new WebSocket(
				"ws://localhost:8080/stomp-server/streaming-bank-details-plain");
		webScocketClient.onopen = function(event) {
			console.log("connection to websocket opened");
			webScocketClient
					.send("MARITAL_STATUS");
		};
		webScocketClient.onmessage = onMessage;
	}
	function onMessage(evt) {
		console.log("11111");
		console.log(evt.data);
	}
	window.addEventListener("load", connect, false);
</script>
</head>
<body>Hello!
</body>
</html>
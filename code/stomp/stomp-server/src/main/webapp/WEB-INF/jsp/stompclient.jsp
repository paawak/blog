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
			webScocketClient.send("MARITAL_STATUS");
		};
		webScocketClient.onmessage = onMessage;
	}
	function onMessage(evt) {
		var bankDetail = JSON.parse(evt.data);
		var table = document.getElementById("bankDetailsTable");

		// Create an empty <tr> element and add it to the 1st position of the table:
		var row = table.insertRow(1);

		// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);

		// Add some text to the new cells:
		cell1.innerHTML = bankDetail.id;
		cell2.innerHTML = bankDetail.job;
		cell3.innerHTML = bankDetail.age;
	}
	window.addEventListener("load", connect, false);
</script>
</head>
<body>
	<h1>Bank Details</h1>
	<div>
		<table border="1" id="bankDetailsTable">
			<thead>
				<tr>
					<th>ID</th>
					<th>Job</th>
					<th>Age</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
</html>
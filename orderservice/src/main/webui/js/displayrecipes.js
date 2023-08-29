var source = new EventSource(
  "http://localhost:8081/product-service/recipes",
);
source.addEventListener("message", function(event) {
  var eventData = event.data;
  var resultDiv = document.getElementById("eventTextArea");
  resultDiv.innerHTML += eventData + "\n";
});

<script type="text/javascript">
	$('#depositMoney').click(function(event){
   		event.preventDefault(); 
	    $.get("/account/add?amount=200", function(response) {
	   		if (response.status > 299) {
	   			alert("Something went wrong!");
	   		} else {
	   			var currentBalance = $("#currentBalance");
	   			currentBalance.html(Number(currentBalance.html())+200);
	   		}
   });
});

</script>
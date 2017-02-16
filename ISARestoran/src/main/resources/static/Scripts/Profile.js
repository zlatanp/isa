function izlogujSe() {
	var x = document.cookie;
	var delovi = x.split("=");
	var emailUlogovanog = delovi[1];

	if (emailUlogovanog.search("@") != -1) {
		document.cookie = "emailValue=" + "";
		window.location.href = "index.html";
	}
}

$(document).ready(function(){
	$divsForHide = $(".formWrapper");
	$divsForHide.hide();
	
	$(".OpenNewRest").on("click", function(){
		$divsForHide.hide();
		$("#NewRest").show();
	});

	$(".OpenNewManForRest").on("click", function(){
		$divsForHide.hide();
		$("#NewManForRest").show();
	});

	$(".OpenNewSystemMan").on("click", function(){
		$divsForHide.hide();
		$("#NewSystemMan").show();
	});
	
});




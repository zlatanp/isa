
var ipadresa;
var grad;
var restoranIDE;
var karticeJela;
var karticaPica;

	function onload(){
		var x = document.cookie;
		var delovi = x.split("=");
		var emailUlogovanog = delovi[1];
		
		if(emailUlogovanog.search("@")==-1){
			$('.navbar').hide();
			$('#glavniDiv').hide();
    	}
		
		if(emailUlogovanog.search("@")!=-1){
	     	$.ajax({ 
	             type: 'GET',
	             url: 'profile/dajNotifikaciju',
	             dataType: 'json',
	             data: {mojEmail : emailUlogovanog},
	     		 success: function(data){
	             			console.log(data);
	             			if(data == true)
	             			$('#notifikacija').append('<a onclick="notifikacija()" href="#"><img src="html/css/notif.png" style="width:24px;height:24px;" /> Zahtev za prijateljstvo </a>');
	            		}
	     	});
	     }
		
		
		$.getJSON('//api.ipify.org?format=jsonp&callback=?', function(data) {ipadresa = data.ip;
		$.getJSON('//freegeoip.net/json/'+ipadresa, function(data) {grad = data.city;});
		});
			


		
	}
	
	function profilKorisnika(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		var ime;
		var prezime;
		
		dajSliku();
		
		if(email.search("@")!=-1){
	     	$.ajax({ 
	             type: 'GET',
	             url: 'profile/gost',
	             dataType: 'json',
	             data: {email : email},
	     		 success: function(data){
	             			console.log(data);
	             			document.getElementById("helloHeder").innerHTML = data.ime+" "+data.prezime;
	             			ime = data.ime;
	             			prezime = data.prezime;
	            		}
	     	});
	     	$('#glavniDiv').show();
	     	$('#leviDiv').show();
	     	$('#desniDiv').hide();
	     }
	}
	
	function basicDetails(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		$.ajax({ 
            type: 'GET',
            url: 'profile/gost',
            dataType: 'json',
            data: {email : email},
    		 success: function(data){
            			console.log(data);
            			$('#desniDiv').html('<h3><i>Osnovni podaci</i></h3><br><table id="tabela" border="1" ><tr><td>Ime: </td><td><input style="border:none" readonly type="text" id="FirstName" value="'+data.ime+'"></td></tr><tr><td>Prezime: </td><td><input readonly style="border:none" type="text" id="LastName" value="'+data.prezime+'"></td></tr><tr><td>Email adresa: </td><td><input readonly style="border:none" type="text" id="Email" value="'+data.email+'"></td></tr></table><br><div id="zaUpload"></div><br></br><button id="editDetails" onclick="editDetails()">Izmeni osnovne podatke</button><button id="saveDetails" onclick="return saveDetails()" style = "display: none">Sačuvaj</button>&nbsp;<button id="cancelDetails" onclick="basicDetails()" style = "display: none">Odustani od izmena</button><br></br>');
            			$('#desniDiv').show();	
    		 }
    	});
		
		
	}
	
	function editDetails(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		
		
		
		$('#editDetails').hide();
		$('#saveDetails').show();
		$('#cancelDetails').show();
		$('#tabela').append('<tr><td>Nova šifra: </td><td><input style="border:none" type="password" id="password"></td></tr><tr><td>Ponovljena nova šifra: </td><td><input style="border:none" type="password" id="password1"></td></tr>');
		$('#zaUpload').html('<form method="POST" action="picture/upload/'+email+'" enctype="multipart/form-data">Dodaj novu profilnu sliku: <br><input type="file" id="file" name="file"><input type="hidden" name="email" value="'+email+'"><br><input onclick="return proveraVelicine()" type="submit" value="Učitaj"></form>');
		
		$('#FirstName').attr('readonly', false);
		$('#LastName').attr('readonly', false);
		
	}
	
	function proveraVelicine(){
		 if (!window.FileReader) {
		        alert("Pretraživač ne omogućava učitavanje slika!");
		        return false;
		    }
		 
		 input = document.getElementById('file');
		 file = input.files[0];
		 
		 if(!file){
		       alert("Morate izabrati sliku pre pritiskanja 'Učitaj'");
		       return false;
		 }else if(file.size>999998){
			 alert("Maksimalna veličina slike mora biti manja od 1MB!");
			 return false;
		 }else{
			 return true;
		 }
		 
	}
		 

	
	function saveDetails(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var i = document.getElementById('FirstName');
		var ime = i.value;
		var j = document.getElementById('LastName');
		var prezime = j.value;
		var m = document.getElementById('Email');
		var email = m.value;
		var k = document.getElementById('password');
		var password = k.value;
		var l = document.getElementById('password1');
		var password1 = l.value;
		
		if(ime == "" || prezime == "" || email == "" || password == "" || password1 == ""){
			alert("Morate uneti sva polja!");
    		return false;
		}else{
			if(password.length <8){
        		alert("Šifra mora biti duža od 8 karaktera!")
        		return false;
        	}
        	if(password.length > 30){
        		alert("Šifra mora da bude između 8-30 karaktera!")
        		return false;
        	}
        	
        	if(password != password1){
        		alert("Nova šifra i ponovljena se ne podudaraju!")
        		return false;
        	}
        	
        	$.ajax({ 
                type: 'GET',
                url: 'profile/edit/',
                dataType: 'json',
                data: {ime : ime, prezime : prezime, email : email, password : password, password1 : password1},
        		 success: function(data){
                			console.log(data);
                			location.reload();
                			return true;
        		 }
        	});
        	
        	return true;
        	
		}
		
		
		
	}
	
	function dajSliku(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		
		 $.ajax({
			    url :'picture/dajSliku/'+email,
			    type: "GET",
			    contentType: "image/jpg",
			    dataType: "text",
			    success: function(data) { 
			            $("#mestoZaSliku").html('<img src="data:image/jpg;base64,' + data + '" style="width:260px;height:260px;" />');
			        }
			    });
			}
	
	function friends(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		$.ajax({ 
	        type: 'GET',
	        url: 'profile/svimojiprijateljibisurirasuti',
	        dataType: 'json',
	        data: {email : email},
			 success: function(data){
	        			console.log(data);
	        			$('#desniDiv').html('<h3><i>Prijatelji</i></h3><br><div><input name="staTrazim" type="text" placeholder="Traži prijatelje"><input type="button" onclick="return dajPrijateljeTrazi()" value="Traži"><br><br><table id="skrolable"></table></div>');
	        			$('#desniDiv').show();
	        			if(data.length>0){
	            			for(var i=0;i<data.length;i++){
	            				$('#skrolable').append('<tr><td><img src="data:image/jpg;base64,' + data[i].slika + '" style="width:100px;height:100px;" /></td><td><h3>'+data[i].ime+'&nbsp;'+ data[i].prezime+'</h3></td><td><input type="button" id='+i+' onclick="return ukloniPrijatelja(\''+data[i].email+'\',\''+i+'\')" value="Obriši prijatelja" /></td></tr><tr><td><br><br></td></tr>');
	            			}	
	            			}else{
	            				$('#skrolable').html('<h3>Ništa nije pronađeno</h3>');
	                    		
	            			}
			 }
		});
	}

	function dajPrijateljeTrazi(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		
		var polje = document.getElementsByName('staTrazim');
		var kogaTrazim = polje[0].value;
		
		$('#skrolable').html('');
		
		if(kogaTrazim == ""){
			alert("Morate uneti nešto u pretragu!");
		}
		$.ajax({ 
	        type: 'GET',
	        url: 'profile/trazi',
	        dataType: 'json',
	        data: {kogaTrazim : kogaTrazim, email : email },
			 success: function(data){
	        			console.log(data);
	        			if(data.length>0){
	        			for(var i=0;i<data.length;i++){
	        				$('#skrolable').append('<tr><td><img src="data:image/jpg;base64,' + data[i].slika + '" style="width:100px;height:100px;" /></td><td><h3>'+data[i].ime+'&nbsp;'+ data[i].prezime+'</h3></td><td><input type="button" id='+i+' onclick="return dodajPrijatelja(\''+data[i].email+'\',\''+i+'\')" value="Dodaj prijatelja" /></td></tr><tr><td><br><br></td></tr>');
	        			}	
	        			}else{
	        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
	                		
	        			}
			 }
		});
		
	}

	function dodajPrijatelja(email,broj){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var JAemail = delovi[1];
		
		
		$.ajax({ 
	        type: 'GET',
	        url: 'profile/dodajPrijatelja',
	        dataType: 'json',
	        data: {mojEmail : JAemail, prijateljevEmail : email},
	        complete: function(data){
	        			console.log(data);
	        			alert("Uspešno dodat prijatelj!");
	        			$('#'+broj+'').attr("disabled", true);
			 }
		});
	}

	function notifikacija(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		
		$('#desniDiv').html("");
		
		$.ajax({ 
	        type: 'GET',
	        url: 'profile/dajLjude',
	        dataType: 'json',
	        data: {email : email},
			 success: function(data){
	        			console.log(data);
	        			if(data.length>0){
	        				$('#glavniDiv').show();
	        				$('#leviDiv').hide();
	        				document.getElementById("helloHeder").innerHTML = "";
	            			for(var i=0;i<data.length;i++){
	            				$('#desniDiv').append('<tr><td><img src="data:image/jpg;base64,' + data[i].slika + '" style="width:100px;height:100px;" /></td><td><h3>'+data[i].ime+'&nbsp;'+ data[i].prezime+'&nbsp;&nbsp;</h3></td><td><input type="button" onclick="return prihvatiPrijatelja(\''+data[i].email+'\')" value="Prihvati prijatelja" />&nbsp;</td><td><input type="button" onclick="return odbaciPrijatelja(\''+data[i].email+'\')" value="Odbaci" /></td></tr><tr><td><br><br></td></tr>');
	            				$('#desniDiv').show();
	            			}	
	            			}else{
	            				$('#desniDiv').html('<h3>Ništa nije pronađeno!</h3>');
	                    		
	            			}	
			 }
		});
	}

	function odbaciPrijatelja(emailKogPrijatelja){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];
		
		$.ajax({ 
	        type: 'GET',
	        url: 'profile/odbaciPrijatelja',
	        dataType: 'json',
	        data: {email : email, kogaOdbacujem : emailKogPrijatelja},
	        complete: function(data){
	        			console.log(data);
	        			alert("Zahtev za prijateljstvo odbijen!");	
	        			location.reload();
			 }
		});
	}

	function  prihvatiPrijatelja(email) {
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var mojEmail = delovi[1];
		
		$.ajax({ 
	        type: 'GET',
	        url: 'profile/prihvatiPrijatelja',
	        dataType: 'json',
	        data: {mojEmail : mojEmail, kogaDodajem : email},
	        complete: function(data){
	        			console.log(data);
	        			alert("Zahtev za prijateljstvo prihvaćen!");	
	        			location.reload();
			 }
		});
	}

	function ukloniPrijatelja(email,broj){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		
		var x = document.cookie;
		var delovi = x.split("=");
		var Jemail = delovi[1];
		
		$.ajax({ 
	        type: 'GET',
	        url: 'profile/odbaciPrijatelja',
	        dataType: 'json',
	        data: {email : Jemail, kogaOdbacujem : email},
	        complete: function(data){
	        			console.log(data);
	        			alert("Prijatelj uspešno uklonjen!");	
	        			$('#'+broj+'').attr("disabled", true);
			 }
		});
		
	}

	function pocetnaFrends(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];

	$.ajax({ 
	    type: 'GET',
	    url: 'profile/svimojiprijateljibisurirasuti',
	    dataType: 'json',
	    data: {email : email},
		 success: function(data){
	    			console.log(data);
	    			$('#desniDiv').html('<h3><i>Prijatelji</i></h3><br><div><input name="staTrazim" type="text" placeholder="Traži prijatelje"><input type="button" onclick="return dajPrijateljeTrazi()" value="Traži"><br><br><a onclick="sortPoImenu()" href="#" >Sortiraj po imenu &nbsp;</a><a href="#" onclick="sortPoPrezimenu()" > Sortiraj po prezimenu</a><br><br><table  id="skrolable"></table></div>');
	    			$('#desniDiv').show();
	    			$('#glavniDiv').show();
					$('#leviDiv').hide();
					document.getElementById("helloHeder").innerHTML = "";
	    			if(data.length>0){
	    				document.getElementById("helloHeder").innerHTML = "";
	        			for(var i=0;i<data.length;i++){
	        				$('#skrolable').append('<tr><td><img src="data:image/jpg;base64,' + data[i].slika + '" style="width:100px;height:100px;" /></td><td><h3>'+data[i].ime+'&nbsp;'+ data[i].prezime+'</h3></td><td><input type="button" id='+i+' onclick="return ukloniPrijatelja(\''+data[i].email+'\',\''+i+'\')" value="Ukloni prijatelja" /></td></tr><tr><td><br><br></td></tr>');
	        			}	
	        			}else{
	        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
	                		
	        			}
		 }
	});
	}

	function sortPoImenu(){
		document.getElementById('restoranProfil').style.display = 'none';
		document.getElementById('glavniDiv').style.display = 'block';
		var x = document.cookie;
		var delovi = x.split("=");
		var email = delovi[1];

		$.ajax({ 
		    type: 'GET',
		    url: 'profile/sortPoImenu',
		    dataType: 'json',
		    data: {email : email},
			 success: function(data){
		    			console.log(data);
		    			$('#desniDiv').html('<h3><i>Prijatelji</i></h3><br><div><input name="staTrazim" type="text" placeholder="Traži prijatelje"><input type="button" onclick="return dajPrijateljeTrazi()" value="Traži"><br><br><a onclick="sortPoImenu()" href="#" >Sortiraj po imenu &nbsp;</a><a href="#" onclick="sortPoPrezimenu()" > Sortiraj po prezimenu</a><br><br><table  id="skrolable"></table></div>');
		    			$('#desniDiv').show();
		    			if(data.length>0){
		    				$('#glavniDiv').show();
		    				$('#leviDiv').hide();
		    				document.getElementById("helloHeder").innerHTML = "";
		        			for(var i=0;i<data.length;i++){
		        				$('#skrolable').append('<tr><td><img src="data:image/jpg;base64,' + data[i].slika + '" style="width:100px;height:100px;" /></td><td><h3>'+data[i].ime+'&nbsp;'+ data[i].prezime+'</h3></td><td><input type="button" id='+i+' onclick="return ukloniPrijatelja(\''+data[i].email+'\',\''+i+'\')" value="Ukloni prijatelja" /></td></tr><tr><td><br><br></td></tr>');
		        			}	
		        			}else{
		        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
		                		
		        			}
			 }
		});
		}
		
		function sortPoPrezimenu(){
			document.getElementById('restoranProfil').style.display = 'none';
			document.getElementById('glavniDiv').style.display = 'block';
			var x = document.cookie;
			var delovi = x.split("=");
			var email = delovi[1];

			$.ajax({ 
			    type: 'GET',
			    url: 'profile/sortPoPrezimenu',
			    dataType: 'json',
			    data: {email : email},
				 success: function(data){
			    			console.log(data);
			    			$('#desniDiv').html('<h3><i>Prijatelji</i></h3><br><div><input name="staTrazim" type="text" placeholder="Traži prijatelje"><input type="button" onclick="return dajPrijateljeTrazi()" value="Traži"><br><br><a onclick="sortPoImenu()" href="#" >Sortiraj po imenu &nbsp;</a><a href="#" onclick="sortPoPrezimenu()" > Sortiraj po prezimenu</a><br><br><table  id="skrolable"></table></div>');
			    			$('#desniDiv').show();
			    			if(data.length>0){
			    				$('#glavniDiv').show();
			    				$('#leviDiv').hide();
			    				document.getElementById("helloHeder").innerHTML = "";
			        			for(var i=0;i<data.length;i++){
			        				$('#skrolable').append('<tr><td><img src="data:image/jpg;base64,' + data[i].slika + '" style="width:100px;height:100px;" /></td><td><h3>'+data[i].ime+'&nbsp;'+ data[i].prezime+'</h3></td><td><input type="button" id='+i+' onclick="return ukloniPrijatelja(\''+data[i].email+'\',\''+i+'\')" value="Ukloni prijatelja" /></td></tr><tr><td><br><br></td></tr>');
			        			}	
			        			}else{
			        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
			                		
			        			}
				 }
			});
		}

		function sviRestorani(){
			document.getElementById('restoranProfil').style.display = 'none';
			document.getElementById('glavniDiv').style.display = 'block';
			var cars = new Array();
			var imaga = false;
			$.ajax({ 
			    type: 'GET',
			    url: 'restoran/dobaviRestorane',
			    dataType: 'json',
				 success: function(data){
			    			console.log(data);
			    			$('#desniDiv').html('<h3><i>Svi restorani</i></h3><br><div><input name="staTrazimR" type="text" placeholder="Traži restoran"><input type="button" onclick="return dajRestoraneTrazi()" value="Traži"><br><br><p>Traži po vrsti: <select onchange="traziPoVrsti()" id="vrstaRestorana"></select></p><a onclick="sortPoImenuRestorane()" href="#" >Sortiraj po imenu &nbsp;</a><br><br><a onclick="sortPoUdaljenosti()" href="#" >Sortiraj udaljenosti &nbsp;</a><br><br><table  id="skrolable"></table></div>');
			    			$('#desniDiv').show();
			    			if(data.length>0){
			    				$('#glavniDiv').show();
			    				$('#leviDiv').hide();
			    				
			    				
			    				document.getElementById("helloHeder").innerHTML = "";
			        			for(var i=0;i<data.length;i++){
			        				$('#skrolable').append('<tr><td><img id="slikaRestImg" src="/slike/restorani/restoran.jpg" style="width:250px;height:100"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><h3><a onclick="dajStranicuRestorana(\''+data[i].id+'\')" href="#">'+data[i].naziv+'</a></h3><h6>'+ data[i].opis+'</h6></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id='+i+' onclick="return rezervisemRestoran(\''+data[i].id+'\',\''+i+'\')" value="Rezerviši" /></td></tr><tr><td><br><br></td></tr>');
			        			
			        				if(cars.length < 1){
				    					cars.push(String(data[0].tip));
				    					
				    				}else{
			        				
			        						for(var w =0;w<cars.length;w++){
			        							if(cars[w] === String(data[i].tip)){
			        								imaga = true;
			        							}
			        						}
			        						
			        						if(imaga === false){
			        							imaga = false;
			        							cars.push(String(data[i].tip));
			        						}
				    				}
			        				console.log(data[i].slika);
			        				var putanjaSlike = "../../../slike/restorani/"+data[i].slika;
				    				$("#slikaRestImg").attr("src", putanjaSlike);
			        			}	
			        			
			        			}else{
			        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
			                		
			        			}
			    			$('#vrstaRestorana').append($("<option></option>").attr("value","izaberi").text("Izaberi tip za selekciju po vrsti"));
			    			for(var p=0;p<cars.length;p++){
	       					 $('#vrstaRestorana').append($("<option></option>").attr("value",cars[p]).text(cars[p])); 
	       					
	       				}
				 }
			});
		}

		var valuta = null;
		
		function dajStranicuRestorana(idRestorana){
			document.getElementById('restoranProfil').style.display = 'block';
			document.getElementById('glavniDiv').style.display = 'none';
			restoranIDE = idRestorana;
			$.ajax({	     
				type: 'GET',
				url: 'restoran/dajRestoranSaId', 
				dataType: 'JSON',
		        data: {id : idRestorana},
		        success: function(data){
		 			console.log(data);
		 			if(data != null){
		 				$("#pogledajJelovnik").html('');
		 				$("#pogledajPicovnik").html('');
		 				$("#pogledajRezervisi").html('');
		 				upisiPodatke(data);
		 				$("#logo").text(data.naziv);
		 				$("#opis").text(data.opis);
		 				$("#adrTD").html(data.adresa);
		 				$("#gradTD").html(data.grad);
		 				$("#telTD").html(data.telefon);
		 				$("#emailTD").html(data.email);
		 				$("#tipTD").html(data.tip);
		 				$("#radnoVremeTD").text(data.vremeOD + ' -- ' + data.vremeDO);
		 				var putanjaSlike = "../../../slike/restorani/"+data.slika;
		 				$("#slikaRestImg").attr("src", putanjaSlike);
		 				//console.data
		 				setMap(data.adresa + ', ' + data.grad + ', Srbija');
		 				$("#pogledajJelovnik").append('<button class="btn btn-success col-lg-offset-5" onclick="pogledajJelovnik(\''+data.id+'\')">Pogledaj jelovnik</button>');
		 				$("#pogledajPicovnik").append('<button class="btn btn-success col-lg-offset-5" onclick="pogledajPicovnik(\''+data.id+'\')">Pogledaj kartu pića</button>');
		 				$("#pogledajRezervisi").append('<button class="btn btn-success col-lg-offset-5" onclick="rezervisemRestoran(\''+data.id+'\')">Rezerviši</button>');
		 				if(data.valuta=="DOLAR")
							valuta = "$";
						if(data.valuta=="DINAR")
							valuta = "RSD";
						if(data.valuta=="EVRO")
							valuta = "€";
		 			}
		        }
			});
		}

		
		function upisiPodatke(data) {
			$("#nazivRestorana").text(data.naziv);
			$("#nazivRest").val(data.naziv);
			$("#opisRest").val(data.opis);
			$("#telRest").val(data.telefon);
			$("#emailRest").val(data.email);
			$("#adrRest").val(data.adresa);
			$("#gradRest").val(data.grad);
			$("#tipRest").val(data.tip);
			$("#vremeODRest").val(data.vremeOD);
			$("#vremeDORest").val(data.vremeDO);
			$("#idRestorana").text(data.id);
			$("#valutaRestorana").text(data.valuta);
		}
		
		function setMap(address){
			var geocoder = new google.maps.Geocoder();

			geocoder.geocode({'address': address}, function(results,status){
			
			if (status == google.maps.GeocoderStatus.OK){
				var location = results[0].geometry.location;
				var map_options = {
						center :  location,
						zoom : 14
				}
				
				var addressMap = new google.maps.Map(document.getElementById('dajmapumesto'),map_options);
				
				var marker = new google.maps.Marker({
					position: location,
					map: addressMap
				})
			}
		});
		}
		
		function pogledajJelovnik(id){
			document.getElementById('jelaipica').style.display = 'block';
			document.getElementById('picaijela').style.display = 'none';
			getJela(id);
		}
		
		function getJela(idRestorana){
			$.ajax({
				type: 'GET',
				url: 'restoran/getJela/' + idRestorana,
				dataType: 'JSON',
				success: function(retVal){
					if(retVal == null){
						window.location.replace("notFound.html");
						return;
					}else {
						prikaziJela(retVal);
					}
				},
				error: function(){
					window.location.replace("notFound.html");
				}		
			});
		}

		function prikaziJela(jela){
			var divJela = $("#cards_jela");
			karticeJela = 0;
			divJela.empty();
			var speed = 0;
			$.each(jela, function(index, k){
				var jeloCard = napraviKarticuZaJelo(k);
				var novi_div = "<div></div>";
				divJela.append(novi_div);
				var a = divJela.children().last();
				a.hide();
				a.append(jeloCard);
				a.delay(speed).fadeIn('fast');
				speed+=200;
			});
		}

		function napraviKarticuZaJelo(jelo){
			var html = "";
			if (karticeJela % 3 == 0){
				if (karticeJela != 0){
					html += '</div>';
				}
				html += '<div class="row-content">';
			}
			karticeJela++;
			html += '<div class="col-md-4">' + 
						'<div class="card text-center my-card" style="margin-top:2%;border-style:solid;border-width:2px;background-color:#ccff99">' +
							'<div class="card-block">' +
								'<h3 class="card-title">' + jelo.naziv + '</h3>' +				
							'</div>'+
							'<img src="../../../slike/jela/' + jelo.slika + '"width="180" height="180" >' +
							'<div class="card-block">'+
								jelo.opis +
							'</div>'+
							'<div class="card-block">'+
								'<div class="zvezdiceJelo pull-right">'+
									'<p>' + convertScoreToStars(jelo.ocena) + '</p>' +
								'</div>'+
								'<h5 style="text-align:left;">'+
									jelo.klasaJela +
								'</h5>'+
								'<br>'+
								'<div class="reviewsJelo pull-right">'+
									jelo.brojOcena + ' reviews' +
								'</div>'+
								'<br>'+
								'<h4 class="pull-right" style="text-align:left;">'+
									valuta + ' ' + jelo.cena +
								'</h4>'+
								'<br>'+ '<br>'+		
							'</div>' +
						'</div>'+
					'</div>';
			
			return html;		
		}
		
		function convertScoreToStars(score){
			if (score < 0 || score > 5)
				return "";
			var text = '';
			var rounded = Math.round(score*2)/2;
			for (i = 1; i <= 5; i++){
				rounded -= 1;
				if (rounded == -0.5)
					text += '<i class="icon-star-half-full"></i>';
				else if (rounded >= 0)
					text += '<i class="icon-star"></i>';
				else if (rounded < 0.5)
					text += '<i class="icon-star-empty"></i>';
			}
			return text;
		}
		
		$(document).on("click", "#btnTraziJela", function(e){
			e.preventDefault();
			var zaTrazenje = new Object();
			zaTrazenje.val1 = $("#imeZaPretragu").val();
			zaTrazenje.val2 = "";
			console.log(zaTrazenje.val1);
			if (/\S/.test(zaTrazenje.val1) || /\S/.test(zaTrazenje.val2)){
				$.ajax({
					url: 'restoran/traziJelo/' + restoranIDE,
					type: 'POST',
					contentType: 'application/json',
					dataType: 'json',
					data: JSON.stringify(zaTrazenje),
					success: function(retVal){
						prikaziJela(retVal);
					}
				});		
			}
		});
		
		function pogledajPicovnik(id){
			document.getElementById('jelaipica').style.display = 'none';
			document.getElementById('picaijela').style.display = 'block';
			//alert("gledaj picovnik + "+id);
			getPica(restoranIDE);
		}
		
		$(document).on("click", "#btnTraziPica", function(e){
			e.preventDefault();
			var zaTrazenje = new Object();
			zaTrazenje.val1 = $("#piceImeZaPretragu").val();
			zaTrazenje.val2 = "";
			console.log(zaTrazenje.val1);
			if (/\S/.test(zaTrazenje.val1) || /\S/.test(zaTrazenje.val2)){
				$.ajax({
					url: 'restoran/traziPice/' + restoranIDE,
					type: 'POST',
					contentType: 'application/json',
					dataType: 'json',
					data: JSON.stringify(zaTrazenje),
					success: function(retVal){
						prikaziPica(retVal);
					}
				});		
			}
		});
		
		function getPica(idRestorana){
			$.ajax({
				type: 'GET',
				url: 'restoran/getPica/' + idRestorana,
				dataType: 'JSON',
				success: function(retVal){
					if(retVal == null){
						window.location.replace("notFound.html");
						return;
					}else {
						console.log(retVal);
						prikaziPica(retVal);
					}
				},
				error: function(){
					window.location.replace("notFound.html");
				}		
			});
		}
		
		
		function prikaziPica(pica){
			var divPica = $("#cards_pica");
			karticaPica = 0;
			divPica.empty();
			var speed = 0;
			$.each(pica, function(index, k){
				console.log(k.id);
				var piceCard = napraviKarticuZaPice(k);
				var novi_div = "<div></div>";
				divPica.append(novi_div);
				var a = divPica.children().last();
				a.hide();
				a.append(piceCard);
				a.delay(speed).fadeIn('fast');
				speed+=200;
			});
		}
		
		function napraviKarticuZaPice(pice){
			var html = "";
			if (karticaPica % 3 == 0){
				if (karticaPica != 0){
					html += '</div>';
				}
				html += '<div class="row-content">';
			}
			karticaPica++;
			html += '<div class="col-md-4">' + 
						'<div class="card text-center my-card" style="margin-top:2%;border-style:solid;border-width:2px;background-color:#ccff99">' +
							'<div class="card-block">' +
								'<h3 class="card-title">' + pice.naziv + '</h3>' +				
							'</div>'+
							'<img src="../../../slike/pica/' + pice.slika + '"width="180" height="180" >' +
							'<div class="card-block">'+
								pice.opis +
							'</div>'+
							'<div class="card-block">'+
								'<div class="zvezdiceJelo pull-right">'+
									'<p>' + convertScoreToStars(pice.ocena) + '</p>' +
								'</div>'+
								'<br>'+
								'<div class="pull-right">'+
									pice.brojOcena + ' reviews' +
								'</div>'+
								'<br>'+
								'<h4 class="pull-right" style="text-align:left;">'+
									valuta + ' ' + pice.cena +
								'</h4>'+
								'<br>'+ '<br>'+ 
								'</div>' +
						'</div>'+
					'</div>';
			
			return html;		
		}
		
		
		
		
		function sortPoImenuRestorane(){
			document.getElementById('restoranProfil').style.display = 'none';
			document.getElementById('glavniDiv').style.display = 'block';
			$.ajax({ 
			    type: 'GET',
			    url: 'restoran/sortirajrestoranepoimenu',
			    dataType: 'json',
				 success: function(data){
					 $('#skrolable').html('');
			    			console.log(data);
			    			$('#desniDiv').html('<h3><i>Svi restorani</i></h3><br><div><input name="staTrazimR" type="text" placeholder="Traži restoran"><input type="button" onclick="return dajRestoraneTrazi()" value="Traži"><br><br><table  id="skrolable"></table></div>');
			    			$('#desniDiv').show();
			    			if(data.length>0){
			    				$('#glavniDiv').show();
			    				$('#leviDiv').hide();
			    				document.getElementById("helloHeder").innerHTML = "";
			        			for(var i=0;i<data.length;i++){
			        				$('#skrolable').append('<tr><td><img id="slikaRestImg" src="/slike/restorani/restoran.jpg" style="width:250px;height:100"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><h3><a onclick="dajStranicuRestorana(\''+data[i].id+'\')" href="#">'+data[i].naziv+'</a></h3><h6>'+ data[i].opis+'</h6></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id='+i+' onclick="return rezervisemRestoran(\''+data[i].id+'\',\''+i+'\')" value="Rezerviši" /></td></tr><tr><td><br><br></td></tr>');
			        			}	
			        			}else{
			        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
			                		
			        			}
				 }
			});
		}
		
		function dajRestoraneTrazi(){
			document.getElementById('restoranProfil').style.display = 'none';
			document.getElementById('glavniDiv').style.display = 'block';
			var r = document.getElementsByName('staTrazimR');
			var naziv = r[0].value;
			
			if(naziv != ""){
			
			$.ajax({ 
			    type: 'GET',
			    url: 'restoran/trazirestoran',
			    dataType: 'json',
			    data: {tekst : naziv},
				 success: function(data){
					 $('#skrolable').html('');
			    			console.log(data);
			    			$('#desniDiv').html('<h3><i>Svi restorani</i></h3><br><div><input name="staTrazimR" type="text" placeholder="Traži restorane"><input type="button" onclick="return dajRestoraneTrazi()" value="Traži"><br><br><br><table  id="skrolable"></table></div>');
			    			$('#desniDiv').show();
			    			if(data.length>0){
			    				$('#glavniDiv').show();
			    				$('#leviDiv').hide();
			    				document.getElementById("helloHeder").innerHTML = "";
			        			for(var i=0;i<data.length;i++){
			        				$('#skrolable').append('<tr><td><img id="slikaRestImg" src="/slike/restorani/restoran.jpg" style="width:250px;height:100"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><h3><a onclick="dajStranicuRestorana(\''+data[i].id+'\')" href="#">'+data[i].naziv+'</a></h3><h6>'+ data[i].opis+'</h6></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id='+i+' onclick="return rezervisemRestoran(\''+data[i].id+'\',\''+i+'\')" value="Rezerviši" /></td></tr><tr><td><br><br></td></tr>');
			        			}
			        			}else{
			        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
			                		
			        			}
				 }
			});
			}
		}
		
		function traziPoVrsti(){
			document.getElementById('restoranProfil').style.display = 'none';
			document.getElementById('glavniDiv').style.display = 'block';
			var i = document.getElementById('vrstaRestorana');
			var selektovanog = i.options[i.selectedIndex].value;
			
			if(selektovanog === "izaberi"){
				return;
			}else{
				$.ajax({ 
				    type: 'GET',
				    url: 'restoran/dobaviRestorane',
				    dataType: 'json',
					 success: function(data){
				    			console.log(data);
				    			$('#desniDiv').html('<h3><i>Svi restorani</i></h3><br><div><input name="staTrazimR" type="text" placeholder="Traži restoran"><input type="button" onclick="return dajRestoraneTrazi()" value="Traži"><br><br><br><table  id="skrolable"></table></div>');
				    			$('#desniDiv').show();
				    			if(data.length>0){
				    				$('#glavniDiv').show();
				    				$('#leviDiv').hide();
				    				
				    				document.getElementById("helloHeder").innerHTML = "";
				        			for(var i=0;i<data.length;i++){
				        				if(String(data[i].tip) === selektovanog)
				        					$('#skrolable').append('<tr><td><img id="slikaRestImg" src="/slike/restorani/restoran.jpg" style="width:250px;height:100"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><h3><a onclick="dajStranicuRestorana(\''+data[i].id+'\')" href="#">'+data[i].naziv+'</a></h3><h6>'+ data[i].opis+'</h6></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id='+i+' onclick="return rezervisemRestoran(\''+data[i].id+'\',\''+i+'\')" value="Rezerviši" /></td></tr><tr><td><br><br></td></tr>');
					        			}
				        		}else{
				        			$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
				                		
				        		}
				    			
				        	}
				   });
		}
		}

		
		function sortPoUdaljenosti(){
			
			document.getElementById('restoranProfil').style.display = 'none';
			document.getElementById('glavniDiv').style.display = 'block';
			
			
				$.ajax({ 
				    type: 'GET',
				    url: 'restoran/sortPoUdaljenosti',
				    dataType: 'json',
				    data: {grad : grad},
					 success: function(data){
						 console.log(data);
						 $('#skrolable').html('');
				    			console.log(data);
				    			$('#desniDiv').html('<h3><i>Svi restorani</i></h3><br><div><input name="staTrazimR" type="text" placeholder="Traži restoran"><input type="button" onclick="return dajRestoraneTrazi()" value="Traži"><br><br><table  id="skrolable"></table></div>');
				    			$('#desniDiv').show();
				    			if(data.length>0){
				    				$('#glavniDiv').show();
				    				$('#leviDiv').hide();
				    				document.getElementById("helloHeder").innerHTML = "";
				        			for(var i=0;i<data.length;i++){
				        				$('#skrolable').append('<tr><td><img id="slikaRestImg" src="/slike/restorani/restoran.jpg" style="width:250px;height:100"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><h3><a onclick="dajStranicuRestorana(\''+data[i].id+'\')" href="#">'+data[i].naziv+'</a></h3><h6>'+ data[i].opis+'</h6></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id='+i+' onclick="return rezervisemRestoran(\''+data[i].id+'\',\''+i+'\')" value="Rezerviši" /></td></tr><tr><td><br><br></td></tr>');
				        			}	
				        			}else{
				        				$('#skrolable').html('<h3>Ništa nije pronađeno!</h3>');
				                		
				        			}
					 }
				});
			}
		
				
		function izlogujSe(){
			document.cookie = "emailValue=";
		}
		
		
		
		
		function rezervisemRestoran(id){
			document.getElementById('restoranProfil').style.display = 'none';
			document.getElementById('glavniDiv').style.display = 'block';
			document.getElementById('desniDiv').style.display = 'block';
			$('#leviDiv').html('');
//			document.getElementById('datumDiv').style.display = 'block';
			$.ajax({	     
				type: 'GET',
				url: 'restoran/dajRestoranSaId', 
				dataType: 'JSON',
		        data: {id : id},
		        success: function(data){
		        	document.getElementById("helloHeder").innerHTML = "Rezervacija restorana: " + data.naziv;
		        	//$('#desniDiv').html('<table><tr><td>Izaberite datum i vreme posete restoranu: </td><td><input type="date" name="datum" id="datum"></td></tr><tr><td>Dužina boravka u restoranu (u satima): </td><td>nekisatinesto</td></tr></table>');
		        	$('#desniDiv').html('<div class="container" style="display: none;" id="datumDiv"><div class="row"><div class=\'col-sm-6\'><div class="form-group"><div class=\'input-group date\' id=\'datetimepicker1\'><input type=\'text\' class="form-control" /><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span><script type="text/javascript">$(function () {$(\'#datetimepicker1\').datetimepicker();});</script></div></div>');
		        }
		        
		        });
		}
	
		
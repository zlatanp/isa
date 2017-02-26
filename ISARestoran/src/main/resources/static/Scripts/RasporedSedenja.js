var emailLogovanog = null;
var cookie = null;
var restoran = null;
var restoranID = null;
var src = 'canvasBackground.jpg';
var imeElementa = null;

var stolovi = [];

// POSTAVKA
$(document).ready(function() {
	cookie = document.cookie;
	emailLogovanog = cookie.split("=")[1];
	console.log(emailLogovanog);

	restoranID = getIdFromUrl();
	getRestoran(restoranID);
	getCanvas();

});

$(document).on("click", "#okrugliStoBtn", function() {
	dodajOkrugli();
});

$(document).on("click", "#cetvrtastiStoBtn", function() {
	dodajCetvrtasti();
});

// FUNKCIJA KOJA GENERISE RANDOM ID STOLA
function randomString(length, chars) {
	var result = '';
	for (var i = length; i > 0; --i)
		result += chars[Math.round(Math.random() * (chars.length - 1))];
	return result;
}

function dodajOkrugli() {
	var canvas = document.getElementById("canvasId").fabric;

	var nameCircle = randomString(32,
			'0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');

	var img = document.createElement('img');
	img.src = '../../../slike/stolovi/okrugliSto.png';
	
	var imgInstance = new fabric.NamedImage(img, {
		  left: 100,
		  top: 100,
		  height: 75,
		  width: 75,
		  opacity: 1,
		  name: nameCircle + "circle"
		});
	canvas.add(imgInstance);
	$('#segmentSel').val("UNUTRA");
	$('#brojStolica').val("4");
	$('#nazivStola').val(nameCircle + "circle");
	dodajStoNaKanvas();
	setTimeout(function(){
		canvas.renderAll(); 
	}, 50);

}

function dodajCetvrtasti() {
	var canvas = document.getElementById("canvasId").fabric;

	var nameCircle = randomString(32,
			'0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');

	var img = document.createElement('img');
	img.src = '../../../slike/stolovi/cetvrtastiSto.png';
	
	var imgInstance = new fabric.NamedImage(img, {
		  left: 100,
		  top: 100,
		  height: 75,
		  width: 75,
		  opacity: 1,
		  name: nameCircle + "circle"
		});
	canvas.add(imgInstance);
	$('#segmentSel').val("UNUTRA");
	$('#brojStolica').val("4");
	$('#nazivStola').val(nameCircle + "circle");
	dodajStoNaKanvas();
	setTimeout(function(){
		canvas.renderAll(); 
	}, 50);

}

function getCanvas() {
	// dobavi raspored ako ga ima preko ajaxa
	$.ajax({
		url : 'restoran/getCanvas/' + emailLogovanog,
		type : 'GET',
		dataType : 'text',
		success : function(data) {
			if (data == "") {
				console.log("vratio se sime");
				pripremiPrazanCanvas();
			} else {
				// iscitaj kanvas ako postoji raspored
				console.log("vratio se neko drugi");
				pripremiGotovCanvas(data);
			}
		}
	});
}

function pripremiGotovCanvas(data){
	console.log("pripremam gotov");
	var canvas = new fabric.CanvasEx("canvasId");
	canvas.loadFromJSON(data);
	document.getElementById("canvasId").fabric = canvas;
	canvas.setHeight(450);
	canvas.setWidth(800);
	
	canvas.on('mouse:dblclick', function(options) {
		console.log("klik me");
		if (imeElementa != null) {
			$("#modalZaSto").modal();
			$("#nazivStola").val(imeElementa);
			dobaviStoPoID(imeElementa);
			imeElementa = null;
		}
	});

	canvas.on('object:selected', onObjectSelected)

	fabric.Object.prototype.originX = fabric.Object.prototype.originY = 'center';
	fabric.Object.prototype.transparentCorners = false;
	fabric.Object.prototype.objectCaching = true;
}

function pripremiPrazanCanvas() {
	
	var canvas = new fabric.CanvasEx("canvasId");
	document.getElementById("canvasId").fabric = canvas;

	canvas.setHeight(450);
	canvas.setWidth(800);
	
	var center = canvas.getCenter();
	
	var slika = '../../../slike/stolovi/pozadina.png';

	canvas.setBackgroundImage(slika,
			canvas.renderAll.bind(canvas),{
			scaleX:1,
			scaleY:1,
			top: center.top,
			left: center.left,
			originX: 'center',
			originY: 'center'
	});
	
	
	

	canvas.on('mouse:dblclick', function(options) {
		if (imeElementa != null) {
			$("#modalZaSto").modal();
			$("#nazivStola").val(imeElementa);
			dobaviStoPoID(imeElementa);
			imeElementa = null;
		}
	});

	canvas.on('object:selected', onObjectSelected)

	fabric.Object.prototype.originX = fabric.Object.prototype.originY = 'center';
	fabric.Object.prototype.transparentCorners = false;
	fabric.Object.prototype.objectCaching = true;
}

// FUNKCIJA KOJA NA OSNOVU ID-A STOLA VRACA OSTALA NJEGOVA POLJA I POPUNJAVA
// MODALNI
// PROZOR ZA TAJ STO SA PODACIMA IZ BAZE
// AKO GA NEMA U BAZI PROVERI LISTU
// AKO NEMA NI TAMO VRATI PRAZNO
function dobaviStoPoID(id) {
	var push = false;

	for (var i = 0; i < stolovi.length; i++) {
		if (stolovi[i].naziv === id) {
			$("#brojStolica").val(stolovi[i].brojStolica);
			$("#segmentSel").val(stolovi[i].tipSegmenta);
			push = true;
			break;
		}
	}

	if (!push) {
		$.ajax({
			url : 'restoran/getTable',
			data : id,
			dataType : "json",
			type : 'POST',
			success : function(data) {
				// zapisi to u modal dijalog
				$("#segmentSel").val(data.tipSegmenta);
				$("#brojStolica").val(data.brojStolica);
			}

		})
	}
}

function onObjectSelected(e) {
	console.log("objekat selektovan")
	console.log(e.target);
	imeElementa = e.target.get('name');
	console.log(imeElementa);
}

// DOBAVLJANJE RESTORANA PO ID-u koji je u linku
function getRestoran(id) {
	console.log("get restoran id:" + id)
	$.ajax({
		url : 'restoran/restoranProfil/' + id,
		type : 'GET',
		dataType : 'json',
		async : false,
		success : function(retVal) {
			if (retVal != "" && retVal != null) {
				$("#logo").text(retVal.naziv);
				$("#opis").text(retVal.opis);
				restoran = retVal;
			} else {
				alert("upao ovde");
				window.location.replace("notFound.html");
			}
		},
		error : function() {
			window.location.replace("notFound.html");

		}
	});
}

// DODAVANJE STOLA
$(document).on("click", "#btnStoAdd", function(e) {
	console.log("btnStoAdd save")
	if(!validateFormAddSto()){
		return;
	}
	dodajStoNaKanvas();
});

function dodajStoNaKanvas() {
	var segment = $("#segmentSel").val();
	var stolica = $("#brojStolica").val();
	var nazivStola = $("#nazivStola").val();
	var push = false;
	var noviSto = new Object();

	noviSto['naziv'] = nazivStola;
	noviSto['brojStolica'] = stolica;
	noviSto['tipSegmenta'] = segment;

	for (var i = 0; i < stolovi.length; i++) {
		if (stolovi[i].naziv === nazivStola) {
			stolovi[i].brojStolica = stolica;
			stolovi[i].tipSegmenta = segment;
			push = true;
			break;
		}
	}

	if (!push) {
		stolovi.push(noviSto);
	}
	$("#modalZaSto").modal('hide');
}

// BRISANJE STOLA
$(document).on("click", "#btnStoDelete", function() {
	obrisiStoSaKanvasa();
});

function obrisiStoSaKanvasa() {
	var canvas = document.getElementById('canvasId').fabric;
	var nazivStola = document.getElementById("nazivStola").value;

	for (i = 0; i < stolovi.length; i++) {
		if (stolovi[i].naziv === nazivStola) {
			stolovi.splice(i, 1);
			break;
		}
	}

	canvas.getActiveObject().remove();
	$('#modalZaSto').modal('hide');
}

// cuvanje kanvasa
$(document).on("click", "#saveCanvas", function(e) {
	e.preventDefault();
	var canvas = document.getElementById('canvasId').fabric;
	$.ajax({
		url : 'restoran/saveCanvas/' + emailLogovanog,
		type : 'POST',
		dataType : 'json',
		contentType : "application/json",
		data : JSON.stringify(canvas),
		success : function(data) {
			console.log(data);
			sacuvajStolove();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR 5: " + errorThrown);
		}
	});
})

function sacuvajStolove(){
	console.log(stolovi);
	$.ajax({
		url: 'restoran/saveTable/' + emailLogovanog,
		type: 'POST', 
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(stolovi),
		success: function(data){
			if(data){
				toastr.success("Uspešno ste sačuvali raspored sedenja.");
			}else {
				toastr.error("Došlo je do greške prilikom čuvanja rasporeda.");
			}
		}
	});
}


// KLIKOVI
$(document).on("click", "#jeloHref", function(e) {
	e.preventDefault();
	window.location.replace("jelovnik.html?" + restoranID);
});

$(document).on("click", "#piceHref", function(e) {
	e.preventDefault();
	window.location.replace("kartaPica.html?" + restoranID);
});

$(document).on("click", "#sedenjeHref", function(e) {
	e.preventDefault();
	window.location.replace("sedenje.html?" + restoranID);
});

$(document).on("click", ".OpenPonude", function(e) {
	e.preventDefault();
	window.location.replace("ponude.html?" + restoranID);
});

$(document).on("click", ".OpenZaposleni", function(e) {
	e.preventDefault();
	window.location.replace("zaposleni.html?" + restoranID);
});

$(document).on("click", ".OpenUpdateRest", function(e) {
	e.preventDefault();
	window.location.replace("menadzerPage.html");
});

// DOBAVI ID RESTORANA IZ URL-a
function getIdFromUrl() {
	var id = null;
	if (window.location.href.indexOf('?') != -1) {
		id = window.location.href.split('?')[1];
	}
	return id;
}

// Uvek kopiraj na kraj skripte
function izlogujSe() {
	var x = document.cookie;
	var delovi = x.split("=");
	var emailUlogovanog = delovi[1];

	if (emailUlogovanog.search("@") != -1) {
		document.cookie = "emailValue=" + "";
		window.location.href = "index.html";
	}
}


//NAMED IMAGE-> custom objekat
fabric.NamedImage = fabric.util.createClass(fabric.Image, {

	  type: 'named-image',

	  initialize: function(element, options) {
		this.callSuper('initialize', element, options);
		options && this.set('name', options.name);
	  },

	  toObject: function() {
		return fabric.util.object.extend(this.callSuper('toObject'), { name: this.name });
	  }
});fabric.NamedImage.async = true;


fabric.NamedImage.fromObject = function(object, callback) {
	  fabric.util.loadImage(object.src, function(img) {
		callback && callback(new fabric.NamedImage(img, object));
	  });
};

// POJEDINACNA VALIDACIJA
function validateField(field){
	if(field.val() === ""){
		return false;
	}else {
		return true;
	}
}

function validateFormAddSto(){
	if(!validateField($("#segmentSel"))){
		toastr.error("Morate odrabrati segment za sto.");
		$("#segmentSel").focus();
		return false;
	}
	if(!validateField($("#brojStolica"))){
		toastr.error("Morate uneti broj stolica.");
		$("#brojStolica").focus();
		return false;
	}
	var brojStolica = $("#brojStolica").val();
	if(isNaN(brojStolica) || brojStolica <= 0){
		toastr.error("Broj stolica mora biti pozitivan broj.");
		$("#brojStolica").focus();
		return false;
	}
	return true;
}
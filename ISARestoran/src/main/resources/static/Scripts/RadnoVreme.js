Array.prototype.filterObjects = function(key, value) {
    return this.filter(function(x) { return x[key] === value; })
}
var radnici = new Array();
var defSmene = new Array();
var email = null;
var list = new Array();  // lista eventova
var day = "";

$(document).ready(function(){
	cookie = document.cookie;
	email = cookie.split("=")[1];	
});

$(document).on("shown.bs.modal", "#modalRadnoVreme", function(e){
	e.preventDefault();
	radnici = new Array();
	var currentDate = new Date();
	var currentMonth = currentDate.getMonth() + 1;
	
	$.ajax({
		url: 'smene/getAllSmene',
		type: 'GET',
		dataType: 'json',
		success: function(data){
			console.log(data);
			upisiSmene(data);
		}
	});
	
	$.ajax({
		url: 'smene/dobaviSmeneText',
		type: 'POST',
		data: currentMonth+ "",
		dataType: 'json',
		contentType: 'text/plain',
		success: function(data){
				console.log("tekstSmene: " + data);
				popuniKalendar(data);
		}
	});
	
	$.ajax({
		url: 'restoran/dobaviZaposlene/' + email,
		type: 'GET',
		contentType: 'json',
		success: function(data){
				console.log("zaposleni: " + data);
				
				$.each(data, function(index,radnik){
					radnici.push({'imePrezime': radnik.ime + " " + radnik.prezime});
					console.log(radnik.ime);
					$("#svi_zaposleni").append('<option val="' + radnik.id + '"><b>' + radnik.ime + ' ' + radnik.prezime +'</b></option>');
				});
				
		}
	});
	
});


function upisiSmene(smene){
	console.log("smene");
	$('#sve_smene').empty();
	$.each(smene,function(index,smena){
		console.log(smena.vremeOD);
		console.log(smena.vremeDO);
		
		defSmene.push({	'vremeOD' : smena.vremeOD,
			  			'vremeDO' : smena.vremeDO,
			  			'naziv' : smena.naziv
		});
		
		$("#sve_smene").append('<option val="' + smena.vremeOD + ':' + smena.vremeDO + '"><b>' + smena.naziv +'</b>'
				+' (' + smena.vremeOD + '-' + smena.vremeDO + ') </option>')
	});
}


function popuniKalendar(events){

	var eventsModalSmene = new Array();
	$.each(events,function(index,event){
		eventsModalSmene.push({id: event.radiUSmeniID,
						   title: event.radnikIme + " " + event.radnikPrezime,
						   start: event.datumOD,
						   end: event.datumDO,
						   allDay: false});
	});
	
	console.log(eventsModalSmene);
		
		
	var calendar = $('#calendar').fullCalendar({
			
			height: 300,
			
			header:
			{
				left: 'prev next today',
				center: 'title',
			},
			
			events: eventsModalSmene,
			
			defaultView: 'month',
			
			selectable: true,
			selectHelper: true,
			
			
			select: function(start, end, allDay)
			{
				
				var check = moment(start, 'DD.MM.YYYY').format('YYYY-MM-DD')
			    var today = moment(new Date()).format('YYYY-MM-DD')
			    if(check < today)
			    {
			    	toastr.info("Menjanje proslih dana nije dozovljeno");
			    }else{
			    	$('#radnik').prop('disabled',false);
					$('#timepickerOD').prop('disabled',false);
					$('#timepickerDO').prop('disabled',false);
					
					calendar.fullCalendar('unselect');
			    
			    }
				
			},
			
			eventConstraint: {
                start: moment().format('YYYY-MM-DD'),
                end: '2100-01-01' 
            },
			
			eventColor: '#12382A',
			editable: true,
			
			 eventDrop: function(event, delta, revertFunc) {

				 	if (typeof event.novo !== 'undefined'){
				 		var custom = list.filterObjects('eventID',event.id);
				 		custom[0].datumOD = moment(event.start).format("YYYY-MM-DD HH:mm");
				    	custom[0].datumDO = moment(event.end).format("YYYY-MM-DD HH:mm");
				    	custom[0].eventID = "";
				    	
				 	}
				 	else{
				        customEvent ={
				    			'radnikIme' : event.title.split(" ")[0],
				    			'radnikPrezime' : event.title.split(" ")[0],
				    			'datumOD' : moment(event.start).format("YYYY-MM-DD HH:mm"),
				    			'datumDO' : moment(event.end).format("YYYY-MM-DD HH:mm"),
				    			'smenaID' : event.smenaID,
				    			'radiUSmeniID' : event.id
				    			
				    	}
				        list.push(customEvent);
				 	}
			        
			        


			 },
			
			dayClick: function(date, jsEvent, view) {
	                if (typeof coloredDay !== 'undefined'){
	                	coloredDay.css('background-color', 'white');
	                }
			        $(this).css('background-color', '#B6FCB6');
			        coloredDay = $(this);
			        day = date;
			    },
			
			 timeFormat: 'H:mm' 
			
		});

	$('#calendar').fullCalendar('refetchEvents');
	
	$('#calendar.td.fc-day.fc-past').css('background-color','#EEEEEE');
}
$(document).on("click", "#btnSmena", function(e){
	if ($('#svi_zaposleni').val() == "def"){
		toastr.error("Morate izbrati radnika");
		return;
	}
	var radnik_ime = $('#svi_zaposleni').val().split(" ")[0];
	var radnik_prezime = $('#svi_zaposleni').val().split(" ")[1];
	if (!(daLiRadnikPostoji($('#svi_zaposleni').val()))){
		toastr.error("Ne postoji radnik sa imenom " +  $('#svi_zaposleni').val());
		return;
	}
    var javaDate = new Date(day);
    var goodMonth = javaDate.getMonth()+1;
    var goodDay = javaDate.getDate();
    if (goodMonth < 10)
    	 goodMonth = '0'+goodMonth;
    if (goodDay < 10)
    	goodDay = '0' + goodDay;
    var radnoVreme = $('#sve_smene option:selected').val().split("-");
    console.log(radnoVreme);
    var smenaNaziv = $('#sve_smene option:selected').text().split(" (")[0];
    console.log(smenaNaziv);
    var vremeOD = radnoVreme[0];
    var vremeDO = radnoVreme[1];
    console.log(vremeOD);
    console.log(vremeDO);

    var res = defSmene.filterObjects('naziv', smenaNaziv);
    console.log(defSmene);
    console.log(res);
    var smenaID = res[0].smena;
    var dateStringFrom= javaDate.getFullYear()+' '+goodMonth+' '+ goodDay+ ' ' + vremeOD;

    var dateStringTo= javaDate.getFullYear()+' '+goodMonth+' '+ goodDay+ ' ' + vremeDO;
	
	var currTime = Date();
	
	event = {
			title : $('#svi_zaposleni').val(),
			start : moment(dateStringFrom, "YYYY-MM-DD HH:mm"),
			end: 	moment(dateStringTo, "YYYY-MM-DD HH:mm"),
			allDay: false,
			smenaID : smenaID,
			id : currTime,
			novo: 'novo'
	}
	
	customEvent ={
			'radnikIme' : $('#svi_zaposleni').val().split(" ")[0],
			'radnikPrezime' : $('#svi_zaposleni').val().split(" ")[1],
			'datumOD' : moment(event.start).format("YYYY-MM-DD HH:mm"),
			'datumDO' : moment(event.end).format("YYYY-MM-DD HH:mm"),
			'smenaID' : smenaID,
			'eventID' : currTime
			
	}
	$('#calendar').fullCalendar('renderEvent',event);
	
	list.push(customEvent);
})

$(document).on("click", "#btnPotvrdiRadnoVreme", function(e){
	console.log(JSON.stringify(list));	
});


function daLiRadnikPostoji(imePrezime){
	var radnikImePrezime = radnici.filterObjects('imePrezime',imePrezime);
	if (radnikImePrezime[0] == null){
		return false;
	}
	return true;
}

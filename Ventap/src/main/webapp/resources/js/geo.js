function initMap() {
        		var map = new google.maps.Map(document.getElementById('map'), {
          			center: {lat: 18.0858100, lng: -15.9785000},
          			zoom: 3
        		});

	        	if (navigator.geolocation) {
	          		navigator.geolocation.getCurrentPosition(function(position) {
	            	var pos = {
	              		lat: position.coords.latitude,
	              		lng: position.coords.longitude
	            	};
	            	var pos1 = {
		              		lat: -2.887486,
		              		lng: -78.990260
		            };
	            	var pos2 = {
		              		lat: -2.883547,
		              		lng: -78.985157
		            };

	            	var image = 'imagenes/bandera.jpg';
	            	map.setCenter(pos);
	            	map.setZoom(15);
	           		var marker = new google.maps.Marker({
	          			position: pos,
	          			map: map,
	          			draggable: true,
	          			animation: google.maps.Animation.DROP,
	          			//icon: image
	          			label: "A",
	          			title: "Ubicaicon Actual"
	          			
	        		});
//	           		var marker1 = new google.maps.Marker({
//	          			position: pos1,
//	          			map: map,
//	          			draggable: true,
//	          			animation: google.maps.Animation.DROP,
//	          			label: 'SB',
//	          			title: 'San Blas'
//	        		});
	           		if(document.getElementById('tipo').innerText=="admin"){
	           			//alert("Entraaaaaaaaaaaaaaaaaaa");
	           			google.maps.event.trigger(map, 'resize');
	           			map.setCenter(pos);
	           		}
	           		//para leer los datos de la tabla
//	           		alert("entra");
	           		var tab=document.getElementById("formPro:propiedades");
	           		           		
	           		//alert(tabla);
	           		//gets rows of table
	           	    var rowLength = tab.rows.length;
	           	    var markers=[];
	           	    var infW=[];
	           	   // alert(rowLength);
	           	    //loops through rows    
	           	    for (i = 1; i < rowLength; i++){

	           	      //gets cells of current row  
	           	       var oCells = tab.rows.item(i).cells;

	           	       //gets amount of cells of current row
	           	       //leo los valores de las celdas en las posiciones de latitud y longitud
	           	       //luego convierto dichos valores a float
	           	       var cellLength = oCells.length;
	           	       var la=parseFloat(oCells.item(3).innerHTML);
	           	       var lon=parseFloat(oCells.item(4).innerHTML);
	           	       //guardo los valores en un LatLong
	           	       var posi={
	           	    		  lat: la,
	           	    		  lng: lon
	           	       }
	           	       var nombreLugar=oCells.item(1).innerHTML.trim();//quito los espacios
	           	       var str = oCells.item(7).innerHTML;
     	           //	console.log(str);
	           	       console.log(str.indexOf("value="));
	           	       if(str.indexOf("type=")>str.indexOf("value=")){//si esq se ejecuta en mozilla
	           	    	str=str.substring(str.indexOf("value=")+7,str.indexOf("type=")-2);
	           	       }else{//si se ejecuta en chrome
	           	       str=str.substring(str.indexOf("value=")+7,str.indexOf(">")-1);
	           	    	}
	           	       str=str.replace("jpg","jpeg");
	           	    var icon = {
	           	    	    url: str, // url
	           	    	    scaledSize: new google.maps.Size(50, 50), // scaled size
	           	    	    origin: new google.maps.Point(0,0), // origin
	           	    	    anchor: new google.maps.Point(0, 0) // anchor
	           	    	};
	           	       //agrego el marker al mapa.
        	            markers[i-1]= new google.maps.Marker({
        	            	position: posi,
        	            	map: map,
        	            	draggable: true,
        	            	animation: google.maps.Animation.DROP,
        	            	title: nombreLugar,
        	            	icon: icon
        	            	
        	            });
        	           
        	           console.log(str);
        	           markers[i-1].index=i-1;
        	           var enlace="Detalle.xhtml?id="+oCells.item(0).innerHTML;
        	           var contentString = '<div id="iw-container">' +
                       '<div class="iw-title">'+nombreLugar+'</div>' +
                       '<div class="iw-content">' +
                         '<p><b>Categoria: </b>'+oCells.item(5).innerHTML+'</p>'+
	                		'<p><b>Direccion: </b>'+oCells.item(2).innerHTML+'</p>'+
	                		'<p><b>Propietario: </b>'+oCells.item(6).innerHTML+'</p></font>'+
	                		
	                	'	<br><p align="center" style="color: yellow"><font size="5" ><b><a style="color: #48b5e9" href="'+enlace+'">Entrar</a><b></font></p>'+
                       '</div>' +
                       '<div class="iw-bottom-gradient"></div>' +
                     '</div>';

        	           var contentStringg =
        	        '<div id="content" style="background-image: url(resources/css/cue.jpg); width:400px; heigth: 300px">'+
   	                	'<div id="siteNotice">'+
   	                	'</div>'+
   	                	'<h1 class=".iw-title" id="firstHeading" class="firstHeading" style="color: white" align="center"><font size="6">'+nombreLugar+'</font></h1>'+
   	                	'<div id="bodyContent">'+
   	                		'<div style="display: inline-flex; flex-direction: row"><div><p><b>Propietario: </b>'+oCells.item(7).innerHTML+'</p>'+
   	                		'<p><b>Direccion: </b>'+oCells.item(2).innerHTML+'</p>'+
   	                		'<p><b>Propietario: </b>'+oCells.item(6).innerHTML+'</p></font></div>'+
   	                	'	<div><p align="center" style="color: yellow"><font size="6" ><b><a style="color: yellow" href="'+enlace+'">Entrar al Tour de este Local</a><b></font></p></div>'+
   	                '</div></div><style>'+
   	             '.gm-style{background: rgba(255,255,255,0.1)}#content{border-radius:4px;box-shadow:8px 8px 16px #222;color:#fff;text-align:center;padding:5px 20px 10px; overflow:hidden;text-transform:uppercase;}'+
   	                	'</style></div>';
        	           //'<br><p align="center"><img src="'+str+'" style="width: 100px; height: 100px"/></p></div>';//imagen en el infow
        	           //document.getElementById('content').innerHTML=document.getElementById('formTour:iv').innerHTML;
   	    	        infW[i-1] = new google.maps.InfoWindow({
   	    	            content: contentString
   	    	          });
   	    	        google.maps.event.addListener(markers[i-1], 'click', function(){
   	    	        	infW[this.index].open(map,markers[this.index]);
   	    	        	var iwOuter = $('.gm-style-iw');

   	    	         /* Since this div is in a position prior to .gm-div style-iw.
   	    	          * We use jQuery and create a iwBackground variable,
   	    	          * and took advantage of the existing reference .gm-style-iw for the previous div with .prev().
   	    	         */
   	    	         var iwBackground = iwOuter.prev();

   	    	         // Removes background shadow DIV
   	    	         iwBackground.children(':nth-child(2)').css({'display' : 'none'});

   	    	         // Removes white background DIV
   	    	         iwBackground.children(':nth-child(4)').css({'display' : 'none'});

   	    	         // Moves the infowindow 115px to the right.
   	    	         iwOuter.parent().parent().css({left: '115px'});

   	    	         // Moves the shadow of the arrow 76px to the left margin.
   	    	         iwBackground.children(':nth-child(1)').attr('style', function(i,s){ return s + 'left: 76px !important;'});

   	    	         // Moves the arrow 76px to the left margin.
   	    	         iwBackground.children(':nth-child(3)').attr('style', function(i,s){ return s + 'left: 76px !important;'});

   	    	         // Changes the desired tail shadow color.
   	    	         iwBackground.children(':nth-child(3)').find('div').children().css({'box-shadow': 'rgba(72, 181, 233, 0.6) 0px 1px 6px', 'z-index' : '1'});

   	    	         // Reference to the div that groups the close button elements.
   	    	         var iwCloseBtn = iwOuter.next();

   	    	         // Apply the desired effect to the close button
   	    	         iwCloseBtn.css({opacity: '1', right: '38px', top: '3px', border: '7px solid #48b5e9', 'border-radius': '13px', 'box-shadow': '0 0 5px #3990B9'});

   	    	         // If the content of infowindow not exceed the set maximum height, then the gradient is removed.
   	    	         if($('.iw-content').height() < 140){
   	    	           $('.iw-bottom-gradient').css({display: 'none'});
   	    	         }

   	    	         // The API automatically applies 0.7 opacity to the button after the mouseout event. This function reverses this event to the desired value.
   	    	         iwCloseBtn.mouseout(function(){
   	    	           $(this).css({opacity: '1'});
   	    	         });
   	    	        });
   	    	        
   	    	       // markers.push(markerBase);
   	    	        //infW.push(infowindow);
//   	    	        markerBase.addListener('click', function(){
//   	    	        	infowindow.open(map,markerBase);
//   	    	        });
        	           
        	           //loops through each cell in current row
	           	       for(var j = 0; j < cellLength; j++){

	           	              // get your cell info here

	           	              var cellVal = oCells.item(j).innerHTML;
	           	              
	           	              //alert(cellVal);
	           	           }
	           	    }
	           	    
	           		
//	           		var marker2 = new google.maps.Marker({
//	          			position: pos2,
//	          			map: map,
//	          			draggable: true,
//	          			animation: google.maps.Animation.DROP,
//	          			label: 'CA',
//	          			title: 'Posicion Siguiente'
//	        		});
	           		
	           		google.maps.event.addListener(map, 'click', function(event) {
	           			//alert(tipous);
	           			console.log("consola");
	           			
	           			var tipous=document.getElementById('tipo').innerText;
	           			//alert("entraa");
	           			//alert(document.getElementById('tipo').innerText);
	           			if(tipous=="admin"){
	           				addMarker(event.latLng, map);
	           			}else{
	           				//si es un usuario simple
	           			}
	            	});

	           		function addMarker(location, map) {
            			var marker = new google.maps.Marker({
	            			position: location,
	            		    map: map,
		          			draggable: true,
		          			animation: google.maps.Animation.DROP
	            		});
            			
            			document.getElementById('latitud').value=location.lat();
            			document.getElementById('longitud').value=location.lng();
            			
            			
            			
            			//alert("listp");
            			
            			//window.location.href = "PerfilAdministrador.jsf";
	            	}
	           		
//	           		google.maps.event.addListener(marker, 'click', function(event) {
//						//ruta(event.latLng);
//	           			//otraPagina();
//	           			var projection = map.getProjection();
//    	        	   	//alert("Entra al mapa");
//    	    	        $('.contextmenu').remove();
//    	    	        	
//    	    	        var nuevodiv=document.createElement("div");
//    	    	        
//    	    	        nuevodiv.className  = 'contextmenu';
//
//    	    	        //nuevodiv.innerHTML='<a id="menu1"><div class="context"><p class="titulo">Aereopuerto Mariscal \n Lamar<\/p><\/div><div class="segundo"><p class="localizacion">Cuenca, Ecuador<br><\/br><div class="estado_labels"><span id="a4" class="label">Activo<\/span><\/div><\/p><\/div><\/a>';
//    	    	        
//
//    	    	        //alert(nuevodiv.innerHTML);
//    	    	        //console.log($("map").htmsl());
//    	    	        //alert($("map").html())
//    	    	        $(map.getDiv()).append(nuevodiv);
//    	    	        //$(map.getDiv()).append(nuevodiv);
//    	    	        //console.log($("map").html(nuevodiv));
//    	    	        $('.contextmenu').css('left', e.pixel.x-25);
//
//    	    	        $('.contextmenu').css('top', e.pixel.y-175);
//
//    	    	        nuevodiv.style.visibility = "visible";
//
//
//	            	});
	           		
//	           		google.maps.event.addListener(marker2, 'click', function(event) {
//						ruta(event.latLng);
//	            	});
	            	function otraPagina(){
	            		window.location.href = "Project10.html";
	            	}
	            	function ruta(location){
	            		var objConfigDR = {
	    	    	           	map: map,
	    	    	        }

	    	    	        var objConfigDS = {
	    	    	    		origin: pos,
	    		    	    	destination: location,
	    			    	    travelMode: google.maps.TravelMode.DRIVING
	    	    			}
	    	    			
	    	           		var directionsService = new google.maps.DirectionsService();
	    					var directionRenderer = new google.maps.DirectionsRenderer(objConfigDR);

	    					directionsService.route(objConfigDS, fnRutear);


	    		      		function fnRutear(resultados, status){
	    						if(status == 'OK'){
	    							directionRenderer.setDirections(resultados);
	    						}else{
	    							alert('error' + status);
	    						}
	    					}	
		            }


	          	//	});//, function() {
	            		//handleLocationError(true, infW, map.getCenter());
	          		});
						          		
	        	} 

	        	//else {
	          		//handleLocationError(false, infW, map.getCenter());
	        	//}
      		}
         	
      		function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        		infoWindow.setPosition(pos);
        		infoWindow.setContent(browserHasGeolocation ?
                	'Error: The Geolocation service failed.' :
                    'Error: Your browser doesn\'t support geolocation.');
      		}          	  	
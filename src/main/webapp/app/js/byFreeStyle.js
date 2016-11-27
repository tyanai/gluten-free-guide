var substringMatcher = function(strs) {
  return function findMatches(q, cb) {
    var matches, substringRegex;

    // an array that will be populated with substring matches
    matches = [];

    // regex used to determine if a string contains the substring `q`
    substrRegex = new RegExp(q, 'i');

    // iterate through the pool of strings and for any string that
    // contains the substring `q`, add it to the `matches` array
    $.each(strs, function(i, str) {
      if (substrRegex.test(str)) {
        matches.push(str);
      }
    });

    cb(matches);
  };
};

var states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
  'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
  'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
  'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
  'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
  'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
  'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
  'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
  'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
];

function initFreeStyleText(){
	
	$('#the-products .form-control ').typeahead({
		hint: false,
		highlight: true,
		minLength: 2
		
	},
	{
		name: 'states',
		limit: 10,
		source: ajaxFreeTextProduct
		
	});
	
	$('#the-manufactors .form-control ').typeahead({
		hint: false,
		highlight: true,
		minLength: 1
	},
	{
		name: 'states',
		limit: 10,
		source: ajaxFreeTextManufactor
	});
	
	$('#submitFreeManufactor').on('click', function(event) {
		  event.preventDefault(); 
		  
		  var manufactorText = $('#freeManufactorValue').val().trim();
		  var productText = $('#freeProductValue').val().trim();
		  if (productText == '') {
			  productText = 'null';
		  }
		  $('#backButtonDown').hide();
		  
		  if (manufactorText != '') {
			  checkRecommendation(productText,manufactorText,'null','free');
		  }
		  
	});
	
	$('#submitFreeManufactorClean').on('click', function(event) {
		  event.preventDefault(); 
		  
		  $('#freeManufactorValue').val('');
		 
		  
	});
	
	$('#submitFreeProduct').on('click', function(event) {
		  event.preventDefault(); 
		  var manufactorText = $('#freeManufactorValue').val().trim();
		  var productText = $('#freeProductValue').val().trim();
		  if (manufactorText == '') {
			  manufactorText = 'null';
		  } 
		  $('#backButtonDown').hide();
		  
		  if (productText != '') {
			  checkRecommendation(productText,manufactorText,'null','free');
		  }
		  
		  
	});
	
	$('#submitFreeProductClean').on('click', function(event) {
		  event.preventDefault(); 
		  
		  $('#freeProductValue').val('');
		 
		  
	});
	
	
}


function ajaxFreeTextProduct(query, syncresults, process) {
	
		
	var queryObj = {};
	queryObj['manufactor'] = 'null';
	queryObj['product'] = query;
	queryObj['category'] = 'null';
	
	var queryDao = {};
	queryDao['queryDao'] = queryObj;
	
	$.ajax({
	    type: "POST",
	    url: '../rest/web_searchproduct_post',
	    data: JSON.stringify(queryDao),
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
			 xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	
	    	if (data.listDao.items){
	    		var suggestions = [];
	    		if (data.listDao.items.value){
	    			suggestions.push(data.listDao.items.value);
	    		} else {
	    			for (var i=0; i<data.listDao.items.length; i++){
		    			suggestions.push(data.listDao.items[i].value);
		    		}
	    		}
	    		
	    		return process(suggestions);
	    	} else{
	    		return false;
	    	}
	    	
	    },
	    error: function(jqXHR) {
	    	alert('Response: ' + jqXHR.status + ' ' +jqXHR.statusText + '.<br>Reason: ' + jqXHR.responseText);
	    },
	    complete: function(){

	    }
	});
	
    
};

function ajaxFreeTextManufactor(query, syncresults, process) {
	
	var queryObj = {};
	queryObj['manufactor'] = query;
	queryObj['product'] = 'null';
	queryObj['category'] = 'null';
	
	var queryDao = {};
	queryDao['queryDao'] = queryObj;
	
	$.ajax({
	    type: "POST",
	    url: '../rest/web_searchmanufacture_post',
	    data: JSON.stringify(queryDao),
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
			 xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	
	    	if (data.listDao.items){
	    		var suggestions = [];
	    		if (data.listDao.items.value){
	    			suggestions.push(data.listDao.items.value);
	    		} else {
	    			for (var i=0; i<data.listDao.items.length; i++){
		    			suggestions.push(data.listDao.items[i].value);
		    		}
	    		}
	    		
	    		return process(suggestions);
	    	} else{
	    		return false;
	    	}
	    	
	    },
	    error: function(jqXHR) {
	    	alert('Response: ' + jqXHR.status + ' ' +jqXHR.statusText + '.<br>Reason: ' + jqXHR.responseText);
	    },
	    complete: function(){

	    }
	});
	
    
};





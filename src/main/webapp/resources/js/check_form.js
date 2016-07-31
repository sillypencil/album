function checkForm(oForm, url, errorFunction){
	var isSuccess = false;
	$.ajax({
		type: 'POST',
		url: url,
		async: false,
		data: $(oForm).serialize(),
		success: function(data, textStatus){
			isSuccess = true;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			errorFunction(XMLHttpRequest, textStatus, errorThrown);
			isSuccess = false;
		}
	});
	return isSuccess;
}

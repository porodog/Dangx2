/**
 * 
 */

function doInsertPage(idleTypeCd) {
	location.href = `/admin/idle/${idleTypeCd}/form.do`;
}

function doDelete() {
	let dataArr = new Array();
	const idleIdxs = document.getElementsByName('idleIdxs');
	idleIdxs.forEach(function(a, b) {
		if(a.checked) {
			let jsonData = {
				idx : a.value
			};
			dataArr.push(jsonData);
		}
	});
	
	if(dataArr.length>0 && confirm('미사용으로 변경하시겠습니까?')) {
		const form = document.createElement("form");
		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", "post");
		form.setAttribute("action", `/admin/idle/${idleTypeCd}/delete.do`);
		
		let intput = document.createElement("input");
		intput.setAttribute("type", "hidden");
		intput.setAttribute("name", "idleIdxs");
		intput.setAttribute("value", JSON.stringify(dataArr));
		form.appendChild(intput);
		
		document.body.appendChild(form);
	 	form.submit();
	}
}


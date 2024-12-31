/**
 * 
 */


document.addEventListener('DOMContentLoaded', function() {
	console.log("ready");
});

function fn_paging(page) {
	location.href = '/idle/'+getMenuType+'/list.do?page='+page;
}
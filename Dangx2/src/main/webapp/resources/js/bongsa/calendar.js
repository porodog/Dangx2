/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	//console.log("apply ready");
	
	const calendarEl = document.getElementById('calendar');
	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		selectable: true,
		fixedWeekCount: false,
		headerToolbar: { // header
			start: '',
			center: 'title',
			end: 'prev today next',
		},
		validRange: function(nowDate) { // month range
			const startDate = new Date(nowDate);
			startDate.setMonth(nowDate.getMonth()-2);
			const endDate = new Date(nowDate);
			endDate.setMonth(nowDate.getMonth()+2);
			
		    return {
		      start: startDate.getFullYear()+'-'+(startDate.getMonth()+1).toString().padStart(2, '0'),
		      end: endDate.getFullYear() + '-' + (endDate.getMonth()+1).toString().padStart(2, '0')
		    };
		},
		events: bongsaList,
		eventClick: function(info) {
			//console.log(info);
		},
		dateClick: function(info) {
			console.log(info);
			
			const now = new Date();
			const selDate = new Date(info.dateStr);
			if(now<selDate) {
				if(info.dayEl.children[0].children[1].childElementCount>1) {
					location.href = info.dayEl.children[0].children[1].childNodes[0].children[0].href;
					//alert("이미 신청함");
					return false;
				}
				location.href = `/bongsa/form.do?date=${info.dateStr}`;
			}
	    },
	});

calendar.render();
});
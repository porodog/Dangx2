/**
 * 
 */

const useridInput = document.getElementById('userId');
const userIcon = document.getElementById('userIcon');

const userpwdInput = document.getElementById('userPwd');
const passIcon = document.getElementById('passIcon');


useridInput.addEventListener('focus', function(){
	userIcon.src = '../../resources/image/usericon-focus.png'; // 포커스 시 변경이미지
});

useridInput.addEventListener('blur', function(){
	userIcon.src = '../../resources/image/usericon.png';
});

userpwdInput.addEventListener('focus', function(){
	passIcon.src = '../../resources/image/passicon-focus.png';
});

userpwdInput.addEventListener('blur', function(){
	passIcon.src = '../../resources/image/passicon.png';
});
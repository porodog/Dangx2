/**
 * 
 */
const form = document.querySelector('#form__wrap');
const checkAll = document.querySelector('.agree_box input');
const checkBoxes = document.querySelectorAll('.checkbox input');
const submitButton = document.querySelector('button');

const agreements = {
  termsOfService: false,
  privacyPolicy: false,
};

// submit 이벤트 리스너
form.addEventListener('submit', (e) => {
  const { termsOfService, privacyPolicy } = agreements;
  if (!termsOfService || !privacyPolicy) {
    // 모든 필수 항목이 체크되지 않았으면 기본 동작 막기
    e.preventDefault();
    alert('모든 필수 약관에 동의해야 합니다.');
  }
});

checkBoxes.forEach((item) => item.addEventListener('input', toggleCheckbox));

function toggleCheckbox(e) {
  const { checked, id } = e.target;  
  agreements[id] = checked;
  this.parentNode.classList.toggle('active');
  checkAllStatus();
  toggleSubmitButton();
}

function checkAllStatus() {
  const { termsOfService, privacyPolicy} = agreements;
  if (termsOfService && privacyPolicy) {
    checkAll.checked = true;
  } else {
    checkAll.checked = false;
  }
}

function toggleSubmitButton() {
  const { termsOfService, privacyPolicy } = agreements;
  if (termsOfService && privacyPolicy) {
    submitButton.disabled = false;
  } else {
    submitButton.disabled = true;
  }
}

checkAll.addEventListener('click', (e) => {
  const { checked } = e.target;
  if (checked) {
    checkBoxes.forEach((item) => {
      item.checked = true;
      agreements[item.id] = true;
      item.parentNode.classList.add('active');
    });
  } else {
    checkBoxes.forEach((item) => {
      item.checked = false;
      agreements[item.id] = false;
      item.parentNode.classList.remove('active');
    });
  }
  toggleSubmitButton();
});
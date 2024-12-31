/**
 * 
 */

function restrictHangul(input) {
        input.value = input.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, ''); // 한글을 제거
    }
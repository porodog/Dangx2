	/*검색창이 빈 상태로 검색 버튼 클릭시*/
	function sendIt() {
        const f = document.searchForm;
        if (f.searchValue.value.trim() == "") { 
            alert("검색어를 입력하세요.");
            return;
        }
		search('1');
    }
	
    function fn_paging(pageNum) {
		search(pageNum);
    }

	function search(page) {
		const f = document.searchForm;
		f.page.value = page;
		f.action = `/boardList${boardTypeCd}.do`;
        f.submit();
	}
	
	
	function writeForm() {
		if(sessionIdx!="null" && sessionIdx!="") {
			location.href = `/writeForm.do?boardType=${boardTypeCd}`;
		}
		else {
			if(confirm("로그인 후 글 등록이 가능합니다.\n로그인페이지로 이동하시겠습니까?")) {
				location.href = '/login/loginPage.do';
			}
		}
	}
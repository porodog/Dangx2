function doDelete() {
    let dataArr = [];
    const idleIdxs = document.getElementsByName('boardIdxs');
    idleIdxs.forEach(function(a) {
        if(a.checked) {
            let jsonData = {
                idx: a.value
            };
            dataArr.push(jsonData);
        }
    });

    if(dataArr.length > 0 && confirm('미사용으로 변경하시겠습니까?')) {
        const form = document.createElement("form");
        form.setAttribute("charset", "UTF-8");
        form.setAttribute("method", "post");
        form.setAttribute("action", `/admin/board/${boardTypeCd}/delete.do`);  // boardTypeCd가 제대로 설정되어 있어야 함

        let input = document.createElement("input");
        input.setAttribute("type", "hidden");
        input.setAttribute("name", "boardIdxs");
        input.setAttribute("value", JSON.stringify(dataArr));
        form.appendChild(input);

        document.body.appendChild(form);
        form.submit();
    }
}

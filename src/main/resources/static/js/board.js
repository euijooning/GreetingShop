// 삭제 기능
function deleteBoard() {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");

  let id = document.getElementById('board_id').value;
  fetch(`/boards/${id}`, {
    method: 'DELETE',
    headers: {
      'header': header,
      "Content-Type": "application/json",
      'X-CSRF-Token': token
    }
  }).then(() => {
    alert('삭제가 완료되었습니다.');
    location.replace('/boards');
  });
}

// 수정 기능
function updateBoard() {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");

  let id = document.getElementById('board_id').value;
  let title = document.getElementById('title').value;
  let content = document.getElementById('content').value;

  fetch(`/boards/${id}`, {
    method: 'PATCH',
    headers: {
      'header': header,
      "Content-Type": "application/json",
      'X-CSRF-Token': token
    },
    body: JSON.stringify({
      "id":id,
      "title" : title,
      "content": content
    })
  }).then(() => {
    alert('수정이 완료되었습니다.');
    location.replace('/boards');
  });
}
/*
// $.ajax({
  //   url : `/boards/${id}`,
  //   type : "PUT",
  //   beforeSend : function (xhr){
  //     /*데이터를전송하기 전에 헤더에 csrf 값을 설정*/
//     xhr.setRequestHeader(header, token);
//   },
//   dataType : "json",
//   data: JSON.stringify(body),
//   contentType : "application/json; charset=utf-8",
//   cache : false,
//   success : function (result, status) {
//     window.location.reload();
//     console.log("board is updated.");
//     location.href=`/boards/${id}`;
//   },
//   error : function (jqxHR, status, error) {
//     window.location.reload();
//     if (jqxHR.status == '401') {
//       alert("로그인 후 이용하세요");
//       location.href = '/members/login';
//     } else {
//       alert(jqxHR.responseText);
//     }
//   }
// });

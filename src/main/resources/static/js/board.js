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
      "id": id,
      "title": title,
      "content": content
    })
  }).then(() => {
    alert('수정이 완료되었습니다.');
    location.replace('/boards');
  });
}


// 댓글 수정 함수 추가
function editComment(button) {
  const commentId = button.getAttribute('data-id');
  const commentElement = document.getElementById('comment-' + commentId);
  const commentContent = commentElement.textContent;

  const newContent = prompt('Edit your comment:', commentContent);
  if (newContent) {
    $.post(`/boards/${id}/comment/${commentId}/update`, {content: newContent}, function(data) {
      window.location.href = data;
    });
  }
}
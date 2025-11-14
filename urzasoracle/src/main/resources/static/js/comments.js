

function toggleAddComment() {
    let box = document.getElementById('addCommentBox');
    box.style.display = (box.style.display === 'none') ? 'block' : 'none';
}

async function loadComments() {
    try {
        let res = await fetch('/api/CommentController/comments-refresh?cardId=' + cardId);
        if (res.ok) {
            let comments = await res.json();
            let list = document.getElementById('commentsList');
            list.innerHTML = '';
            comments.forEach(c => {
                let div = document.createElement('div');
                div.className = 'comment mb-2 p-2 bg-secondary bg-opacity-10 rounded';
                div.innerHTML = '<span class=comment-user>' + c.userName + '</span>' +
                    '<small class="text-secondary ms-2">' + c.timeOfComment.toLocaleString() + '</small>' +
                    '<p class="mb-1 text-light">' + c.comment + '</p>';
                list.appendChild(div);
            });
        } else {
            console.error('Error loading comments: ' + res.status);
        }
    } catch (e) {
        console.error(e);
    }
}

async function addComment() {
    let input = document.getElementById('commentInput');
    let text = input.value.trim();
    if (!text) return;
    try {
        let res = await fetch('/api/CommentController/add-comment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken,
            },
            body: new URLSearchParams({
                commentText: text,
                currentCardId: cardId
            })
        });
        if (res.status === 401) {
            window.location.href = '/login';
            return;
        }
        if (res.ok) {
            input.value = '';
            document.getElementById('addCommentBox').style.display = 'none';
            loadComments();
        } else {
            alert('Errore nell\'invio del commento');
            console.log(res);
        }
    } catch (e) {
        alert('Errore di rete');
        console.error(e);
    }
}

document.addEventListener('DOMContentLoaded', loadComments);

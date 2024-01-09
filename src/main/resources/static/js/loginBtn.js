// login 함수 정의
function login() {
    var data = {
        username: $('#username').val(),
        password: $('#password').val(),
    };

    // AJAX 요청 설정
    $.ajax({
        type: 'POST',
        url: '/users/login',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function () {
            alert('로그인 되었습니다.');
            window.location.href = '/home';
        },
        error: function (error) {
            $('#error-message').text(error.responseJSON.error);
        }
    });
}

// 문서가 로드되면 초기화
$(document).ready(function () {
    // login 함수를 클릭 이벤트에 연결
    $('#btn-login').on('click', function () {
        login();
    });
});

var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });

    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({ //비동기적 처리. 페이지 변경사항을 요청 후 페이지 전체를 다시 받는게 아닌 변경사항만 받도록.
           type: 'POST', //POST타입
           url: '/api/v1/posts', //url은 PostsApiController의 api경로
           dataType: 'json',
           contentType: 'application/json; charset = utf-8',
           data: JSON.stringify(data) //수정할 데이터
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/'; //글 등록이 성공하면 메인 페이지로 돌아감
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    update : function () { //update함수
        var data = {
            title: $('#title').val(),
            content: $("#content").val()
        };

        var id = $('#id').val(); //id는 수정 대상이 아니므로

        $.ajax({
            type: 'PUT', //update 기능이므로 put
            url: '/api/v1/posts/'+id, //수정할 게시글을 url에 id값을 붙여 식별
            dataType: 'json',
            contentType: 'application/json; charset = utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset = utf-8',
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        })
    }
};

main.init();
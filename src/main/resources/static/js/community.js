function comment2Target(targetId,type,content) {

    if(!content){
        alert("评论不能为空");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if(response.code==200) {
                window.location.reload();
            }else {
                if(response.code=2003) {
                    var isAccepted=confirm(response.message);
                    if(isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=b85fd778f06b1317767d&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}
/**
 * 提交评论
 */
function comment2Question() {
    var questionId=$("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2Target(questionId,1,commentContent);
}
function comment2Comment(e) {
    var commentId=e.getAttribute("data-id");
    var commentContent = $("#input-"+commentId).val();
    comment2Target(commentId,2,commentContent);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id=e.getAttribute("data-id");
    var comments=$("#comment-"+id);
    if(comments.hasClass("in")) {
        comments.removeClass("in");
    }else {
        var subCommentContainer=$("#comment-"+id);
        if(subCommentContainer.children().length!=1) {
            comments.addClass("in");
        }else {
            $.getJSON("/comment/"+id,function (data) {

                $.each(data.data.reverse(),function (index,comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class":"media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html":comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class":"pull-right",
                        "html": moment(comment.gmtCreate).format('YY-MM-DD HH:mm:ss')
                    })));
                    var mediaElement = $("<div/>", {
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-1g-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass("in");
            });
        }
    }
}
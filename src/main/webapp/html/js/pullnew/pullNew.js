/*
$("#go").click(function(){
    var params = {};
    params.beInviteMobile = $("#beInviteMobile").val();
    params.inviteMobile = $("#inviteMobile").val();
    $.ajax({
        url: '/pull/newMember.do',
        type: "post",
        cache: false,
        dataType: "json",
        async : false,
        data: params,
        error:function(){
        },
        success: function (data) {
            alert(data.code+","+data.msg);
        }
    });
})*/

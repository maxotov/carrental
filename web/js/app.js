$(document).ready(function(){
    $("#vendorType").change(function(e) {
        var id = $("select#vendorType").val();

        $.ajax({
            type: "POST",
            url: "actionservlet",
            data: {
                action: "get_models",
                id: id
            },
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    var options = '';
                    for (var i = 0; i < data.models.length; i++) {
                        options += '<option value="' + data.models[i].id + '">' + data.models[i].name + '</option>';
                    }
                    $("select#modelType").html(options);
                } else {
                    $("select#modelType").html('');
                }
            }

        });
    });
    $( "#dialogHelp" ).dialog({
        autoOpen: false,
        show: "blind",
        hide: "explode"
    });
    $( "#help" ).click(function() {
        $( "#dialogHelp" ).dialog("open");
        return false;
    });
});

function open_dialog(id){
    console.log(id);
    $("#id_user").val(id);
    $( "#dialog" ).dialog({
        resizable: false,
        modal: true,
        width: 400
    });
}

function dialog_delete(idAccess, idUser){
    $("#idAccess").val(idAccess);
    $("#idUser").val(idUser);
    $( "#dialog_delete" ).dialog({
        resizable: false,
        modal: true,
        width: 400
    });
}





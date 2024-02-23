
function delete_task(task_id){
    let url = "/" + task_id;
    $.ajax({
        url:url,
        type: 'DELETE'
    })

    setTimeout(()=>{
        document.location.reload();
    },300);
}

function edit_task(task_id){

    let delete_id = "#delete_" + task_id;
    $(delete_id).remove();

    let edit_id = "#edit_" + task_id;
    let save_btn = "<button id='save_" + task_id + "'>Save</button>";
    $(edit_id).html(save_btn);
    let property_save_tag = "update_task(" + task_id + ")";
    $(edit_id).attr("onclick",property_save_tag);

    let description_id = "#description_" + task_id;
    $(description_id).html("<input id='input_description_" + task_id + "' type='text' value='" + $(description_id).text() + "'>");

    getStatusSelect(task_id);
}

function getStatusSelect(task_id){

    let status_id = "status_" + task_id;

    let tr_val = $("#" + status_id).text();

    let code = "<label for='select_" + status_id + "'></label>" +
        "<select name='" + status_id + "' id='select_" + status_id + "'>" +
        "  <option value=\"IN_PROGRESS\">IN_PROGRESS</option>\n" +
        "  <option value=\"DONE\">DONE</option>\n" +
        "  <option value=\"PAUSED\">PAUSED</option>\n" +
        "</select>"

    $("#" + status_id).empty().append(code);
    $("#select_" + status_id).val(tr_val).change();
}

function update_task(task_id){
    let value_description = $("#input_description_" + task_id).val();
    let value_status      = $("#select_status_" +     task_id).val();

    let url = "/" + task_id;

    $.ajax({
        url:url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async:false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    })

    setTimeout(()=>{
        document.location.reload();
    },300);
}

function add_task(){
    let value_description = $("#description_new").val();
    let value_status      = $("#status_new").val();

    let url = "/";

    $.ajax({
        url:url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async:false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    })

    setTimeout(()=>{
        document.location.reload();
    },300);
}
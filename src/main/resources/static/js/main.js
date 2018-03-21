function insertNewTextField(){
    var hostSize=$("#hostsSize");
    $(hostSize).append(
        $('<input/>',{
            type:'text',
            placeholder:'Lan Size',
            class:'hosts'
        })
    )
}

function calculate(){
    var hostsSize= $("#hostsSize .hosts").map(function() {
        return $(this).val();
    }).get();

    var numberOfRouterNetworks=$("#routerNetNumbers").val();

    var ip=$("#IP .prefix").val();

    var cidr=$("#IP .cidr").val();

    var request=JSON.stringify({
        hostsSize:hostsSize,
        numberOfRouterNetworks:numberOfRouterNetworks,
        ip:ip,
        cidr:cidr
    });

    $.ajax({
        url:location.origin+'/calculate',
        type:'put',
        data: request,
        dataType: 'json',
        contentType: "application/json",
        success: function(data){
            constructResult(data);
        }
    });
}

function constructResult(data){
    var result=$("#result");
    $(result).children('tbody').remove();
    var tableBody = $('<tbody/>');


    for(var i=0,len=data.length;i<len;i++){
        var newRow = $('<tr/>');
        var rPrefix = $('<td/>');
        var rBroadcast= $('<td/>');
        // $(row).text(data[i].network+"/"+data[i].cidr+" "+data[i].broadcast+"/"+data[i].cidr);
         console.log(data[i]);
        $(rPrefix).html(data[i].network+"/"+data[i].cidr);
        $(rBroadcast).html(data[i].broadcast+"/"+data[i].cidr);
        $(newRow).append($(rPrefix));
        $(newRow).append($(rBroadcast));
        $(tableBody).append($(newRow));
    }
    $(result).append($(tableBody));
}
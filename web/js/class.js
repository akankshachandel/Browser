
$(document).ready(onReady);
function onReady() {


    $("#ClassLink").on("click", function (e) {
       e.preventDefault();
        $("#ListDiv").hide();
$("#ImgDiv").show();
                    loadClassData();
                    $("#ImgDiv").hide();
                    $("#ListDiv").show();
   
    });


}

function loadClassData() {
   var dng= $('#ClassLink').text();
    //alert (dng);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "rest/OntoService/getClasses/",
        dataType: 'json',
        cache: false,
        success: function (data, textStatus, xhr) {
             var listTableBody = $("#listTableBody");
        listTableBody.empty();
   
       
            $.each(data,function(key,object){
                    $.each(object,function(key2,object2){
                      
                         $.each(object2,function(index,value){
                             var row = $("<tr></tr>");
                            if(key==="head"){
                             
                               row.append( $("<td><b>"+value+"</b></td>"));
                            }else{
                                $.each(value,function(i,val){
                                    
                                    if(val.type==="uri"){ 
                                        var str = val.value;
                                        var arr= str.split('/');
                                        var name = arr[(arr.length)-1] ;   
                                         var cls = arr[(arr.length)-2] ;  
                                      //   alert("class:"+cls);
                                        row.append( $("<td><a href='#' class='"+cls+"' data_val='"+name+"'>"+name+"</a></td>"));
                                  } else if(val.type==="typed-literal" || val.type==="literal"){ 
                                      row.append( $("<td>"+val.value+"</td>"));
                                  }
                                  
                              }); 
                            }
                             row.appendTo(listTableBody);
                           });
                       });
        	    
                        });
      
        },
        error: function (data, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}




    
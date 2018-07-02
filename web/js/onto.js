
$(document).ready(onReady);
function onReady() {
//    var results = document.getElementById("ListDiv");
//            results.innerHTML="<img src='img/loading.gif'>";
//    
  
    $("#ListLink").on("click", function () {
        loadInitData();


        $("#ListDiv").show();

    });


}

function loadInitData() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "rest/OntoService/getOnto",
        dataType: 'json',
        cache: false,
        success: function (data, textStatus, xhr) {
             var listTableBody = $("#listTableBody");
        listTableBody.empty();
           
                  var row1 = $("<tr></tr>");
                  
                   $.each(data,function(key,object){
                    $.each(object,function(key2,object2){
                      
                         $.each(object2,function(index,value){
                             var row = $("<tr></tr>");
                            if(key==="head"){
                             
                               row1.append( $("<td><b>"+value+"</b></td>"));
                           }
                             }); 
                               }); 
                                 }); 
                   row1.appendTo(listTableBody);
                  
                  
            
            $.each(data,function(key,object){
                    $.each(object,function(key2,object2){
                      
                         $.each(object2,function(index,value){
                             var row = $("<tr></tr>");
                            if(key==="head"){
                             
                             
                            }else{
                                $.each(value,function(i,val){
                                    if(val.type==="uri"){ 
                                        var str = val.value;
                                        var arr= str.split('/');
                                        var name = arr[(arr.length)-1] ;   
                                       
                                        row.append( $("<td><a href='"+val.value+"' target ='ifrm'>"+name+"</a></td>"));
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




    
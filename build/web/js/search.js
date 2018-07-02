 $(document).ready(function(){

    $("#sbdbt").on("click", function () {

    var dise =  $("#searchvalue").val();
     alert("You are searching for "+dise+" disease.");
 $("#ListDiv").hide();
$("#ImgDiv").show();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "rest/OntoService/searchD/"+dise,
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
                                         var cls = arr[(arr.length)-2] ;
                                      //  alert("class:"+cls);
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

$("#ImgDiv").hide();

        $("#ListDiv").show();

    });

     
 $("#sbgbt").on("click", function () {
    var gene =  $("#searchvalue").val();
     
 $("#ListDiv").hide();
$("#ImgDiv").show();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "rest/OntoService/searchG/"+gene,
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
                                         var cls = arr[(arr.length)-2] ;
                                      //  alert("class:"+cls);
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


$("#ImgDiv").hide();
        $("#ListDiv").show();
   });


});
   
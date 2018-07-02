

 $(document).ready(function(){
     
  $('td').on('click','.diseases', function (e) {
  e.preventDefault();
      var dng = $(this).attr('data_val'); 
  $("#ListDiv").hide();
$("#ImgDiv").show();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "rest/OntoService/disease/"+dng,
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


$("#ImgDiv").hide();
        $("#ListDiv").show();

    });


});
   
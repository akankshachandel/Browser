$(document).ready(onReady);
function onReady() {
  
    $("#qrybtn").on("click", function () {
         $("#ListDiv").hide();
$("#ImgDiv").show();
        loadQueryData();

$("#ImgDiv").hide();
        $("#ListDiv").show();

    });

   $("#sparql1").on("click", function () {
        var dsno =  $("#dno").val();
                            var qry1="SELECT ?Possible_Drug\n" +
"WHERE {<http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/diseases/"+dsno+"> diseasome:possibleDrug ?Possible_Drug.}";
                       
                         $('#queryinput').val(qry1);
                       
                            });
   $("#sparql2").on("click", function () {
                          var qry2="SELECT DISTINCT ?resource ?value\n" +
"WHERE { ?resource <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/diseasome/name> ?value.\n" +
"FILTER regex(?value, 'syndrome', 'i').\n" +
"}\n" +
"ORDER BY ?resource ?value\n";
 $('#queryinput').val(qry2);
                            });
}

function loadQueryData() {
    
    var query =  $("#queryinput").val();
    //var query1="SELECT * WHERE {?subject ?predicate ?object} limit 50";
    var qry = JSON.stringify({
    "query" : query
    });
   // alert(qry);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "rest/OntoService/Query",
        dataType: 'json',
        data: qry,
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

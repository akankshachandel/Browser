/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservice;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;


//////////jena//////
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import java.io.ByteArrayOutputStream;


/**
 *
 * @author amitabh
 */
@Path("/OntoService")
public class OntoService {
    String endpoint = "http://wifo5-04.informatik.uni-mannheim.de/diseasome/sparql";
    String queryString = "PREFIX db: <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/>"
                        + "PREFIX diseasome: <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/diseasome/>"
                        + "PREFIX vocabClass: <http://wifo5-04.informatik.uni-mannheim.de/drugbank/vocab/resource/class/>"
                        + "PREFIX drugbank: <http://wifo5-04.informatik.uni-mannheim.de/drugbank/resource/drugbank/>"
                        + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
                        + "PREFIX vocabProperty: <http://wifo5-04.informatik.uni-mannheim.de/drugbank/vocab/resource/property/>"
                        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                        + "PREFIX d2r: <http://sites.wiwiss.fu-berlin.de/suhl/bizer/d2r-server/config.rdf#>"
                        + "PREFIX d2r: <http://sites.wiwiss.fu-berlin.de/suhl/bizer/d2r-server/config.rdf#>"
                        + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                        + "PREFIX map: <file:/C:/apps/diseasome/diseasome.n3#>"
                        + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                        + "PREFIX dbpedia: <http://dbpedia.org/ontology/>"
                        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getOnto")
    public Response getOnto() {
               //1// + " SELECT DISTINCT ?instance WHERE { ?instance a <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/diseasome/genes> }";
               //2// +"SELECT DISTINCT ?property ?hasValue WHERE { <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/diseases/10> ?property ?hasValue}";
        queryString +="SELECT * WHERE {?subject ?predicate ?object} limit 50";
      
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();

    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getProperties")
    public Response getProperties() {
        queryString +=" SELECT DISTINCT ?property\n" +
                        "WHERE { [] ?property [] }\n" +
                        "ORDER BY ?property";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getClasses")
    public Response getClasses() {
        queryString +=" SELECT DISTINCT ?Class\n" +
                        "WHERE { [] a ?Class }\n" +
                        "ORDER BY ?Class";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
    }

        
   
    
         
   @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("disease/{name}")
    public Response getdisease (@PathParam("name")String value) {
       
         queryString +="PREFIX diseases: <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/diseases/>\n" +
                        "SELECT ?prop ?lit\n" +
                        "WHERE { diseases:"+value+" ?prop  ?lit.\n" +
                        " }\n" +
                        "limit 50";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
   
    }
   
             
  @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gene/{name}")
    public Response getgene (@PathParam("name")String value) {
       
        queryString +="PREFIX genes: <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/genes/>\n" +
                        "SELECT ?prop ?lit\n" +
                        "WHERE { genes:"+value+"  ?prop  ?lit.\n" +
                        " }\n" +
                        "limit 50";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
   
    }
   
        
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("diseaseNgenes/{name}")
    public Response getdiseaseNgenes (@PathParam("name")String value) {
       
        queryString +="SELECT DISTINCT ?"+value+"\n" +
                        "WHERE { ?"+value+" a diseasome:"+value+". }\n" +
                        "ORDER BY ?"+value+"\n"+
                        "limit 50";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
   
    }
   
        
        
         @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchD/{name}")
    public Response getSearchD (@PathParam("name")String value) {
       
        queryString +="SELECT DISTINCT ?resource ?Disease\n" +
                        "WHERE { ?resource <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/diseasome/name> ?Disease.\n" +
                        "FILTER regex(?Disease,'"+value+"', 'i').\n" +
                        "}\n" +
                        "ORDER BY ?resource ?Disease";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
   
    } 
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchG/{name}")
    public Response getSearchG (@PathParam("name")String value) {
       
        queryString +="SELECT DISTINCT ?property ?hasValue ?v\n" +
"WHERE {\n" +
"  { <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/genes/"+value+"> ?property ?hasValue }\n" +
"  UNION\n" +
"  { ?v ?property <http://wifo5-04.informatik.uni-mannheim.de/diseasome/resource/genes/"+value+"> }\n" +
"}\n" +
"ORDER BY (!BOUND(?hasValue)) ?property ?hasValue ?v";
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
   
    }
        
        
        
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("Query")
    public Response getQuery (String payload) {
        //queryString +="SELECT * WHERE {?subject ?predicate ?object} limit 50";
        
        InputQuery qry = fromJSON(payload);
        String q=qry.getQuery();
        queryString +=q;
        System.out.println(queryString);
        Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
        QueryEngineHTTP qe = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(endpoint, query);
        String json = "";
        try {
            ResultSet resultSet = qe.execSelect();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(b, resultSet);
            json = b.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        qe.close();
        return Response.ok(json).build();
   
    }
    
    public String toJSONString(InputQuery query) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 								// UTC
		Gson gson = gsonBuilder.create();
		return gson.toJson(query);
	}

    public InputQuery fromJSON(String string) {
                GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 								// UTC
		Gson gson = gsonBuilder.create();
                
		return gson.fromJson(string,InputQuery.class);
    
    }
}

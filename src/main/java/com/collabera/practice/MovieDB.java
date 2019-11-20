package com.collabera.practice;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;


public class MovieDB {
	
	public static void main(String[] args) {
		MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("my_db");
        
        System.out.println("List of Databases");
        for (String s : mongo.listDatabaseNames()) {
        	System.out.println(s);
        }
        
        db.createCollection("movies");
        
        MongoIterable<String> colls = db.listCollectionNames();
        System.out.println("\nCollections in Database");
        for (String s : colls) {
        	System.out.println(s);
        }
        
        MongoCollection<Document> movies = db.getCollection("movies");
        
        Document movie = new Document();
        
        movie.put("title", "It");
        movie.put("genre", "Horror");
        movie.put("rating", "R");
        
        movies.insertOne(movie);
        
        System.out.println("\nAfter Insert One:");
        for (Document d: db.getCollection("movies").find()) {
        	
        	for (String s : d.keySet()) {
        		System.out.println(s + ": " + d.get(s));
        	}
        	
        }
        
        Document movie1 = new Document();
        Document movie2 = new Document();
        Document movie3 = new Document();
        Document movie4 = new Document();
        Document movie5 = new Document();
        
        movie1.put("title", "Inside Out");
        movie1.put("genre", "Adventure, Comedy");
        movie1.put("rating", "PG");
        
        movie2.put("title", "Sweeney Todd");
        movie2.put("genre", "Drama, Horror, Musical");
        movie2.put("rating", "R");
        
        movie3.put("title", "Revenge of the Nerds");
        movie3.put("genre", "Comedy");
        movie3.put("rating", "R");
        
        movie4.put("title", "Back to the Future");
        movie4.put("genre", "Adventure, Comedy, Sci-Fi");
        movie4.put("rating", "PG");
        
        movie5.put("title", "La La Land");
        movie5.put("genre", "Drama, Romance");
        movie5.put("rating", "PG-13");
        
        List<Document> movList = new ArrayList<Document>();
        movList.add(movie1);
        movList.add(movie2);
        movList.add(movie3);
        movList.add(movie4);
        movList.add(movie5);
        
        movies.insertMany(movList);
        
        System.out.println("\nAfter Insert Many:");
        for (Document d: db.getCollection("movies").find()) {

        	for (String s : d.keySet()) {
        		System.out.println(s + ": " + d.get(s));
        	}
        	
        }
        
        BasicDBObject searchQuery = new BasicDBObject();
        
        searchQuery.put("title", "Inside Out");
        searchQuery.put("rating", "PG");
        
        FindIterable<Document> fi = movies.find(searchQuery);
        
        for (Document d: fi) {
        	System.out.println("\nResult of search Query:");
        	for (String s : d.keySet()) {
        		System.out.println(s + ": " + d.get(s));
        	}
        }
        
      
        BasicDBObject updateValue = new BasicDBObject();
        updateValue.put("genre", "Animation, Adventure, Comedy");
        
        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", updateValue);
        
        movies.findOneAndUpdate(searchQuery, updateObject);
        
        fi = movies.find(searchQuery);
        for (Document d: fi) {
        	System.out.println("\nAfter Update:");
        	for (String s : d.keySet()) {
        		System.out.println(s + ": " + d.get(s));
        	}
        }
        
        searchQuery.put("rating", "PG-13");
        searchQuery.put("title", "La La Land");
        movies.deleteOne(searchQuery);
        
        
        System.out.println("\nAfter Delete");
        for (Document d: db.getCollection("movies").find()) {
        	
        	for (String s : d.keySet()) {
        		System.out.println(s + ": " + d.get(s));
        	}
        	
        }
        
        
        movies.drop();
        mongo.close();
        

        
	}

}

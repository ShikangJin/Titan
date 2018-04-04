package db.mongodb;

import java.text.ParseException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

public class MongoDBTableCreation {
	// Run as Java application to create tables with index
	public static void main(String[] args) throws ParseException {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);
		
		// Remove old tables
		db.getCollection("users").drop();
		db.getCollection("items").drop();
		
		// Create new tables, populate data and create index
		db.getCollection("users")
		.insertOne(new Document().append("first_name", "John").append("last_name", "Smith")
				.append("password", "3229c1097c00d497a0fd282d586be050").append("user_id", "1111"));
		// Make sure that user_id is unique
		IndexOptions indexOptions = new IndexOptions().unique(true);
		
		// Use 1 for ascending order and -1 for descending order
		// user table contains history info
		db.getCollection("users").createIndex(new Document("user_id", 1), indexOptions);
		
		// item table contains category info
		db.getCollection("items").createIndex(new Document("item_id", 1), indexOptions);
		
		mongoClient.close();
		System.out.println("import is done successfully.");
		
	}
}

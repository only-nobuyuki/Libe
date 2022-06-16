package dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import util.ClassConverter;

public class LibeDao {

	public void conectUseJson(String crud, String json) {
		MongoClient client = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase db = client.getDatabase("personal");
		MongoCollection<Document> libe = db.getCollection("libe");
		DBOperatorFactry.ope(crud, libe, ClassConverter.convertDocumentList(json));
		client.close();
	}

	public void conectUseList(String crud, List<Document> doc) {
		MongoClient client = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase db = client.getDatabase("personal");
		MongoCollection<Document> libe = db.getCollection("libe");
		DBOperatorFactry.ope(crud, libe, doc);
		client.close();
	}
}

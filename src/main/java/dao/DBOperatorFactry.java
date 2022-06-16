package dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;

/**
 * @author ibett
 *
 */
public class DBOperatorFactry {

	public static void ope(String name, MongoCollection<Document> documents, List<Document> json) {
		DBOperater dBOperater = new DBOperatorFactry().new DBOperaterImpl();

		if ("create".equals(name)) {
			dBOperater.create(documents, json);
		}
		if ("update".equals(name)) {
			dBOperater.update(documents, json);
		}

	}

	/**
	 * @author ibett
	 *
	 */
	private class DBOperaterImpl implements DBOperater {

		@Override
		public InsertManyResult create(MongoCollection<Document> collection, List<Document> json) {
			return collection.insertMany(json);
		}

		@Override
		public UpdateResult update(MongoCollection<Document> collection, List<Document> json) {

			for (int i = 0; i < json.size(); i++) {

				Document d = json.get(i);

			}

			Document filter = new Document();
			filter.append("特記事項", "新人");

			return collection.updateMany(filter, json);
			//return collection.replaceOne(filter, json);
		}
	}

}

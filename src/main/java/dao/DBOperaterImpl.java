package dao;

import java.lang.reflect.Method;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;

/**mongoDBの操作
 * @author unknown
 */
public class DBOperaterImpl implements DBOperater {

	public void ope(String name, MongoCollection<Document> documents, List<Document> json) {
		DBOperaterImpl dBOperaterImpl = new DBOperaterImpl();
		Method[] methods = dBOperaterImpl.getClass().getDeclaredMethods();
		for (Method method : methods) {
			switch (method.getName()) {
			case "create":
				create(documents, json);
				break;
			case "update":
				update(documents, json);
				break;
			}
		}
	}

	@Override
	public InsertManyResult create(MongoCollection<Document> documents, List<Document> json) {
		return documents.insertMany(json);
	}

	@Override
	public UpdateResult update(MongoCollection<Document> documents, List<Document> json) {
		Document doc=json.get(0);
		return documents.updateMany(doc, json);
	}

}

package dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;

/**mongoDBの操作IF
 * @author unknown
 */
public interface DBOperater {

	InsertManyResult create(MongoCollection<Document> documents, List<Document> json);

	UpdateResult update(MongoCollection<Document> documents, List<Document> json);

}

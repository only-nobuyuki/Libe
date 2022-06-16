package util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

/**異なるクラスへの変換
 * @author unknown
 */
public class ClassConverter {
	/*mongoDBに格納する形式*/
	private static List<Document> jsonList;

	/**
	 * @author unknown
	 * @param json
	 * @return mongoDBに格納する形式
	 */
	public static List<Document> convertDocumentList(String json) {
		Document d = Document.parse(json);
		jsonList = new ArrayList<Document>();
		jsonList.add(d);
		return jsonList;
	}

	/**
	 * @author unknown
	 * @param json
	 * @return mongoDBに格納する形式
	 */
	public static Document convertDocument(String json) {
		return Document.parse(json);
	}
}

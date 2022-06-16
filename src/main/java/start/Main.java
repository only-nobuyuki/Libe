package start;

import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;

import dao.LibeDao;
import parse.Formatter;
import scrape.LibeHtml;;

//TODO アプリにする
public class Main {

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		LibeHtml html = new LibeHtml();
		List<String[]> list = html.getProfileTexts();
		LibeDao dao = new LibeDao();
		Formatter f = new Formatter();
		//String json = f.editToJson(list);
		//dao.conectUseJson("update", json);
		List<Document> docList = null;
		try {
			docList = f.editToBson(list);
		} catch (JsonProcessingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		dao.conectUseList("update", docList);
		long endTime = System.currentTimeMillis();

		System.out.println("全体処理時間：" + (endTime - startTime) + " ms");
	}

}

package scrape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**ライブHPの情報を扱う
 * @author unknown
 */
public class LibeHtml {
	/*LIBE東京 東京ポータル*/
	final String LIBETOPPAGE = "https://libe-tokyo.com/index2.html";

	/**Libe東京のtopページからプロフィールページ内のテキスト情報を取得する
	 * @return アクセス先URLのテキスト情報
	 */
	public List<String[]> getProfileTexts() {
		return serchProfileTexts(serchProfilePageURLs());
	}

	/**Webページにつなぐ
	 * @author unknown
	 * @param url アクセス先URL
	 * @return アクセス先URLのテキスト情報
	 */
	private Document connectWebPage(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**topページからプロフィールページのURLを探索
	 * @author unknown
	 * @return 個人ページのURLリスト
	 */
	private List<String> serchProfilePageURLs() {
		Document document = connectWebPage(LIBETOPPAGE);
		List<String> urlList = new ArrayList<>();
		Elements leftColum = document.select("div.girls-left a[href*=/profile-]");
		for (Element element : leftColum) {
			//絶対パスが取得できない場合はnullを返す
			urlList.add(element.absUrl("href"));
		}
		Elements rightColum = document.select("div.girls-right a[href*=/profile-]");
		for (Element element : rightColum) {
			urlList.add(element.absUrl("href"));
		}
		return urlList;
	}

	/**プロフィールページから属性値がprofileのテキストを取得
	 * @author unknown
	 * @param urlList プロフィールページのURLリスト
	 * @return プロフィールページごとのプロフィールテキストのリスト
	 */
	private List<String[]> serchProfileTexts(List<String> urlList) {
		List<String[]> profilesList = new ArrayList<>();
		for (String url : urlList) {
			Elements profilePageElements = connectWebPage(url).select("dd.profile");
			int numOfElement = profilePageElements.size();
			String[] profileArray = new String[numOfElement];
			for (int i = 0; i < numOfElement; i++) {
				profileArray[i] = profilePageElements.get(i).ownText();
			}
			profilesList.add(profileArray);
		}
		return profilesList;
	}

}

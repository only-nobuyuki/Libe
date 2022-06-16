package parse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import constant.LibeAttributeConstant;
import util.ExtractionPattern;

/**	プロフィールを加工する
 * @author unknown
 *
 */
public class Formatter {
	/*独自パターン*/
	ExtractionPattern extractionPattern = new ExtractionPattern();

	//TODO:DBに格納するための型で戻すように修正
	/**
	 * @author unknown
	 * @param libeProfileList htmlから抽出したままの個人ページ情報
	 * @return
	 */
	public String editToJson(List<String[]> profileList) {
		/*Node生成用*/
		ObjectMapper mapper = new ObjectMapper();
		/*全キャストのプロフィール*/
		ObjectNode allCastProfile = mapper.createObjectNode();
		allCastProfile = putId(allCastProfile);
		for (String[] profileArray : profileList) {
			// 全キャストのプロフィールのkey
			String allCastProfileKey = "";
			/*キャストのプロフィール*/
			ObjectNode castProfile = null;

			int length = profileArray.length;
			for (int i = 0; profileArray.length > i; i++) {
				final int start = 0;
				final int end = length - 1;
				//ループ処理の最初に初期化
				if (start == i) {
					castProfile = mapper.createObjectNode();
				}
				String profile = profileArray[i];
				//allCastProfileのKeyにキャストの名前を指定
				if (profile.length() < 25) {
					Matcher result = extractAttribute(LibeAttributeConstant.NAME, profile);
					if (result.find()) {
						allCastProfileKey = extractAttributeValue(profile);
					}
					//TODO:enumはforで回すように書き換える
					//					for(LibeAttributeConstant e:LibeAttributeConstant.values()) {
					//						checkProfile(e.getAttribute(), profile, castProfile);
					//					}
					checkProfile(LibeAttributeConstant.NAME, profile, castProfile);
					checkProfile(LibeAttributeConstant.AGE, profile, castProfile);
					checkProfile(LibeAttributeConstant.HEIGHT_HALFWIDTH, profile, castProfile);
					checkProfile(LibeAttributeConstant.BUST_HALFWIDTH, profile, castProfile);
					checkProfile(LibeAttributeConstant.STICK, profile, castProfile);
					checkProfile(LibeAttributeConstant.BALL, profile, castProfile);
					checkProfile(LibeAttributeConstant.PENIS_SIZE_HALFWIDTH, profile, castProfile);
					checkProfile(LibeAttributeConstant.TYPE, profile, castProfile);
				}
				if (end == i) {
					allCastProfile.putPOJO(allCastProfileKey, castProfile);
				}
			}
		}
		try {
			String json = mapper.writeValueAsString(allCastProfile);
			System.out.println(json);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author unknown
	 * @param libeProfileList htmlから抽出したままの個人ページ情報
	 * @return
	 * @throws JsonProcessingException
	 */
	public List<Document> editToBson(List<String[]> profileList) throws JsonProcessingException {
		/*Node生成用*/
		ObjectMapper mapper = new ObjectMapper();
		/*全キャストのプロフィール*/
		ObjectNode allCastProfile = mapper.createObjectNode();
		allCastProfile = putId(allCastProfile);

		List<Document> docList = new ArrayList<>();
		//setScrapingDay(docList);
		//IDの任意指定
		//		String json = mapper.writeValueAsString(allCastProfile);
		//		Document id = ClassConverter.convertDocument(json);
		//		docList.add(id);
		for (String[] profileArray : profileList) {
			// 全キャストのプロフィールのkey
			String allCastProfileKey = "";
			/*キャストのプロフィール*/
			ObjectNode castProfile = null;

			int length = profileArray.length;
			for (int i = 0; profileArray.length > i; i++) {
				final int start = 0;
				final int end = length - 1;
				//ループ処理の最初に初期化
				if (start == i) {
					castProfile = mapper.createObjectNode();
				}
				String profile = profileArray[i];
				//allCastProfileのKeyにキャストの名前を指定
				if (profile.length() < 25) {
					Matcher result = extractAttribute(LibeAttributeConstant.NAME, profile);
					if (result.find()) {
						allCastProfileKey = extractAttributeValue(profile);
					}
					//TODO:enumはforで回すように書き換える
					//					for(LibeAttributeConstant e:LibeAttributeConstant.values()) {
					//						checkProfile(e.getAttribute(), profile, castProfile);
					//					}
					checkProfile(LibeAttributeConstant.NAME, profile, castProfile);
					checkProfile(LibeAttributeConstant.AGE, profile, castProfile);
					checkProfile(LibeAttributeConstant.HEIGHT_HALFWIDTH, profile, castProfile);
					checkProfile(LibeAttributeConstant.BUST_HALFWIDTH, profile, castProfile);
					checkProfile(LibeAttributeConstant.STICK, profile, castProfile);
					checkProfile(LibeAttributeConstant.BALL, profile, castProfile);
					checkProfile(LibeAttributeConstant.PENIS_SIZE_HALFWIDTH, profile, castProfile);
					checkProfile(LibeAttributeConstant.TYPE, profile, castProfile);
				}
				if (end == i) {
					//allCastProfile.putPOJO(allCastProfileKey, castProfile);
					String j = mapper.writeValueAsString(castProfile);
					Document d = new Document(allCastProfileKey, j);
					docList.add(d);
				}
			}
		}
		//String json = mapper.writeValueAsString(allCastProfile);
		//System.out.println(json);
		return docList;
	}

	/**
	 * 属性が「名前」の要素に対する独自処理<br>
	 * []を使用した特殊な情報が追記されているため
	 * @author unknown
	 * @param profile
	 * @return 属性「名前」の属性値
	 */
	private String extractAttributeValue(String profile) {
		Matcher result = extractionPattern.buildBracketPattern().matcher(profile);
		//WHYDO:属性名前は「名前:」で固定されているため
		int startIndex = 3;
		if (result.find()) {
			//属性が名前のプロフィールに[]があった場合はspecialProfile(大かっこなし)を除去
			String withBracket = result.group();
			profile = profile.replace(withBracket, "");
		}
		//属性が名前のみの場合
		return profile.substring(startIndex);
	}

	//TODO 取り出しと設定を分けた方が良いかも
	/**属性ごとに属性値を取り出しObjectNodeに設定する
	 * @author unknown
	 * @param attribute
	 * @param profile
	 * @param castProfile
	 */
	private void checkProfile(LibeAttributeConstant attribute, String profile, ObjectNode castProfile) {
		Matcher result = extractAttribute(attribute, profile);
		if (result.find()) {
			//属性が名前＆[]があった場合の処理
			String value = null;
			Matcher re = extractionPattern.buildBracketPattern().matcher(profile);
			if (extractionPattern.buildAttributePattern(LibeAttributeConstant.NAME).matcher(result.group()).find()
					&& re.find()) {
				//specialProfile(大かっこなし)を切り出して
				String withBracket = re.group();
				int startIndex = 1;
				int endIndex = withBracket.length() - 1;
				String specialProfile = withBracket.substring(startIndex, endIndex);
				castProfile.put("特記事項", specialProfile);
				//名前の値を切り出して
				String inProgress = profile.replace(withBracket, "");
				startIndex = 3;
				endIndex = inProgress.length();
				value = inProgress.substring(startIndex, endIndex);
			} else {
				final int INDEX = attribute.getAttribute().length() + 1;
				final int LASTINDEX = profile.length();
				value = profile.substring(INDEX, LASTINDEX);
			}
			castProfile.put(attribute.getAttribute(), value);
		} else {
			//TODO 属性取得できないエラー処理
		}
	}

	/**
	 * オリジナルプロフィールと属性パターンのマッチング
	 * @author unknown
	 * @param attribute
	 * @param profile
	 * @return マッチングの結果
	 */
	private Matcher extractAttribute(LibeAttributeConstant attribute, String profile) {
		return extractionPattern.buildAttributePattern(attribute).matcher(profile);
	}

	/**
	 * IDを設定する<br>
	 * mongoDB登録時のidを指定する
	 * @author unknown
	 * @param objectNode
	 * @return monogoDB登録時に使用するIDが設定されたObjectNodeインスタンス
	 */
	private ObjectNode putId(ObjectNode objectNode) {
		final int id = 19821025;
		String idHex = Integer.toHexString(id);
		ObjectNode idArray = new ObjectMapper().createObjectNode().put("$oid", "616193206455a37cd70143d0");
		objectNode.putPOJO("_id", new ObjectId("616193206455a37cd70143d0"));
		return objectNode;
	}

	private void setScrapingDay(List<Document> docList) {
		LocalDate ldt = LocalDate.now();
		docList.add(new Document("更新日", ldt));

	}

}
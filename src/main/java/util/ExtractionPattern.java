package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.text.Transliterator;

import constant.LibeAttributeConstant;

//TODO 変数を保持する必要がないのでstaticメソッドでいいのでは
/**独自のPatternを定義する
 * @author unknown
 */
public class ExtractionPattern {
	/*半角から全角への変換用*/
	private Transliterator toFullwidthTransliterator = Transliterator.getInstance("Halfwidth-Fullwidth");
	/*半角英数字パターン*/
	private Pattern halfwidthAlphanumeric = Pattern.compile("[a-zA-Z0-9]+");

	/**属性とコロンの組み合わせ
	 * @author unknown
	 * @param attribute 属性
	 * @return 独自パターン
	 */
	public Pattern buildAttributePattern(LibeAttributeConstant attribute) {
		String attributeTypeStr = attribute.getAttribute();
		StringBuilder patten = new StringBuilder();
		Matcher matcher = halfwidthAlphanumeric.matcher(attributeTypeStr);
		//WHYDO:英数字が含まれてる場合は全角も考慮
		if (matcher.find()) {
			String fullwidthAlphabet = toFullwidthTransliterator.transliterate(matcher.group());
			String IncludingFullwidthAlphabet = matcher.replaceAll(fullwidthAlphabet);
			patten.append(IncludingFullwidthAlphabet);
			//WHYDO:定数を使用するより可読性が上がる気がする
			patten.append(":");
			patten.append("|");
			patten.append(IncludingFullwidthAlphabet);
			patten.append("：");
			patten.append("|");
		}
		//WHYDO:ひらカナ漢字のみの場合は全角のみで引数を受ける
		patten.append(attributeTypeStr);
		patten.append(":");
		patten.append("|");
		patten.append(attributeTypeStr);
		patten.append("：");
		return Pattern.compile(patten.toString());
	}

	/**[]で囲まれた1文字以上の文字列
	 * @author unknown
	 * @return 独自パターン
	 */
	public Pattern buildBracketPattern() {
		return Pattern.compile("\\[.+\\]|\\［.+\\］|\\[.+\\］|\\［.+\\]");
	}
}
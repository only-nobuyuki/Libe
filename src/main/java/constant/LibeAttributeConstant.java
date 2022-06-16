package constant;

/**	Libeの個人ページのプロフィールの属性
 * @author unknown
 *
 */
public enum LibeAttributeConstant {
	NAME("名前"),
	AGE("年齢"),
	HEIGHT_HALFWIDTH("T"),
	BUST_HALFWIDTH("B"),
	STICK("竿"),
	BALL("玉"),
	PENIS_SIZE_HALFWIDTH("Pサイズ"),
	TYPE("タイプ");

	private String attribute;

	private LibeAttributeConstant(String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}
}

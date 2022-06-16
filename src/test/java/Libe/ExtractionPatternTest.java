package Libe;

import java.util.regex.Pattern;

import org.junit.Test;

import constant.LibeAttributeConstant;
import util.ExtractionPattern;

public class ExtractionPatternTest {
	@Test
	public void testBuildAttributePattern() {
		//テスト対象
		ExtractionPattern extractionPattern = new ExtractionPattern();
		//事前準備
		//テストパラメータ
		LibeAttributeConstant pal;
		for (LibeAttributeConstant e : LibeAttributeConstant.values()) {
			Pattern p = extractionPattern.buildAttributePattern(e);
			System.out.println(Pattern.compile(p.toString()));
		}
	}
}

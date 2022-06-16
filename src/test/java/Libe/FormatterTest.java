package Libe;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import parse.Formatter;

public class FormatterTest {

	@Test
	public void testEditToHJson() {
		//テスト対象
		Formatter formatter = new Formatter();
		//事前準備
		List<String[]> lists = new ArrayList<>();
		String[] array1=new String[3];
		String[] array2=new String[3];
		String[] array3=new String[3];
		array1[0]="名前：楊原しほ";
		array1[1]="年齢：23歳";
		array1[2]="B：83 (Cカップ)";
		array2[0]="名前：[AV女優]春野ひなた";
		array2[1]="竿：有り";
		array2[2]="タイプ:ニューハーフ/地毛";
		array3[0]="名前：夢月まゆ(ムツキマユ)";
		array3[1]="年齢：19歳";
		array3[2]="アナル受け(60分～):○";
		lists.add(array1);
		lists.add(array2);
		lists.add(array3);
		//テスト実行
		String result=formatter.editToHJson(lists);
		System.out.println(result);
		//検証
		assertThat(result, is(notNullValue()));
	}
}

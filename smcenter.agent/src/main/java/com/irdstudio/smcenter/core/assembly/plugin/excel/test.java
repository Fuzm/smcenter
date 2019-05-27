package com.irdstudio.smcenter.core.assembly.plugin.excel;

import com.irdstudio.smcenter.core.assembly.plugin.excel.export.ExcelTemplateBuilder;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String str = "201|+|安徽省尚洋经贸有限公司|+|商业|+|其他商业|+|市区|+|法人企业|+||+|null|+|70504066-X|+|340000000023058|+|34011170504066X|+|34011170504066X|+|3401010000052103|+|null|+|0|+||+||+|合肥市包河区黄山路省杂技团鸿城商|+|合肥市包河区黄山路省杂技团鸿城商住楼1幢401室|+||+||+||+||+|null|+|null|+|null|+||+||+|王健|+|2010-01-20 00:00:00.0|+|吴祥好|+||+||+||+||+||+|null|+||+|null|+|null|+|null|+|null|+|不计息|+|0|+|3|+|null|+|null|+|null|+|null|+|null|+|";
//		String a[] = str.split("\\|\\+\\|");
//		System.out.println(a.length);
//		System.out.println(a[0]);
		
		ExcelTemplateBuilder excel = new ExcelTemplateBuilder("e:\\test.xlsx");
		excel.writeIn("test excel!!!", 0,11, 2);
		excel.saveAsTargetExcel("e:\\test2.xlsx");
	}

}

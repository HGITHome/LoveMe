package com.dgut.common.util;

import java.util.ArrayList;
import java.util.List;


/**保存手机号码的开头
 * 1、移动号段有134,135,136,137,138,139,147,150,151,152,157,158,159,178,182,183,184,187,188。
   2、联通号段有130，131，132，155，156，185，186，145，176。
   3、电信号段有133，153，177，180，181，189。
 * @author Administrator
 *
 */
public class PhoneUtils {
	static List<String> phoneList ;
	public static void  addPhone(){
		phoneList = new ArrayList<String>();
		phoneList.add("134");
		phoneList.add("135");
		phoneList.add("136");
		phoneList.add("137");
		phoneList.add("138");
		phoneList.add("139");
		phoneList.add("147");
		phoneList.add("150");
		phoneList.add("151");
		phoneList.add("152");
		phoneList.add("157");
		phoneList.add("158");
		phoneList.add("159");
		phoneList.add("178");
		phoneList.add("182");
		phoneList.add("183");
		phoneList.add("184");
		phoneList.add("187");
		phoneList.add("188");
		phoneList.add("130");
		phoneList.add("131");
		phoneList.add("132");
		phoneList.add("155");
		phoneList.add("156");
		phoneList.add("185");
		phoneList.add("186");
		phoneList.add("145");
		phoneList.add("176");
		phoneList.add("133");
		phoneList.add("153");
		phoneList.add("177");
		phoneList.add("180");
		phoneList.add("181");
		phoneList.add("189");
		
	}
	public static List<String> getList(){
		return phoneList;
	}
}

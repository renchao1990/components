package com.rc.components.study.arithmetic;

import org.junit.Test;

/**
 * 罗马数字
 * 
 * 字符：
 * 基本字符		I	V	X	L	C	D	M
 * 阿拉伯数字	1	5	10	50	100	500	1000
 * 
 * 计数方法：
 * 1、相同的数字连写，所表示的数等于这些数字相加得到的数，如：Ⅲ = 3；
 * 2、小的数字在大的数字的右边，所表示的数等于这些数字相加得到的数， 如：Ⅷ = 8；Ⅻ = 12；
 * 3、小的数字，（限于Ⅰ、X 和C）在大的数字的左边，所表示的数等于大数减小数得到的数，如：Ⅳ= 4；Ⅸ= 9；
 * 4、正常使用时，连写的数字重复不得超过三次。（表盘上的四点钟“IIII”例外）
 * 5、在一个数的上面画一条横线，表示这个数扩大1000倍。
 * 
 * 原则：
 * 1、基本数字Ⅰ、X 、C 中的任何一个，自身连用构成数目，或者放在大数的右边连用构成数目，都不能超过三个；放在大数的左边只能用一个。
 * 2、不能把基本数字V 、L 、D 中的任何一个作为小数放在大数的左边采用相减的方法构成数目；放在大数的右边采用相加的方式构成数目，只能使用一个。
 * 3、V 和X 左边的小数字只能用Ⅰ。
 * 4、L 和C 左边的小数字只能用X。
 * 5、D 和M 左边的小数字只能用C。
 * 
 * 示例：
 * 个位数举例Ⅰ,1 】Ⅱ，2】 Ⅲ，3】 Ⅳ，4 】Ⅴ，5 】Ⅵ，6】Ⅶ，7】 Ⅷ，8 】Ⅸ，9 】
 * 十位数举例Ⅹ，10】 Ⅺ，11 】Ⅻ，12】 XIII,13】 XIV,14】 XV,15 】XVI,16 】XVII,17 】XVIII,18】 XIX,19】 XX,20】 XXI,21 】XXII,22 】XXIX,29】 XXX,30】 XXXIV,34】 XXXV,35 】XXXIX,39】 XL,40】 L,50 】LI,51】 LV,55】 LX,60】 LXV,65】 LXXX,80】 XC,90 】XCIII,93】 XCV,95 】XCVIII,98】 XCIX,99 】
 * 百位数举例C,100】 CC,200 】CCC,300 】CD,400】 D,500 】DC,600 】DCC,700】 DCCC,800 】CM,900】 CMXCIX,999】
 * 千位数举例M,1000】 MC,1100 】MCD,1400 】MD,1500 】MDC,1600 】MDCLXVI,1666】 MDCCCLXXXVIII,1888 】MDCCCXCIX,1899 】MCM,1900 】MCMLXXVI,1976】 MCMLXXXIV,1984】 MCMXC,1990 】MM,2000 】MMMCMXCIX,3999】
 * 
 * @author: rc
 * @date: 2018年3月3日 下午7:42:47
 * @version: V1.0
 * @review: rc/2018年3月3日 下午7:42:47
 */
public class RomanNumeral {
	
	private final String stringValue;
	private final int intValue;
	
	public RomanNumeral(String romanStr){
		this.stringValue = romanStr;
		this.intValue = intValue(romanStr);
	}
	
	/**
	 * 获取罗马字符串
	 * @return
	 */
	public String stringValue() {
		return stringValue;
	}
	/**
	 * 获取阿拉伯数字值
	 * @return
	 */
	public int intValue() {
		return intValue;
	}

	/**
	 * 罗马数字转阿拉伯数字
	 * @param romanStr
	 * @return
	 */
	public static int intValue(String romanStr){
		if (romanStr.isEmpty()) {
			throw new IllegalArgumentException("非法的罗马数字格式");
		}
		
		int result = 0; //总数值
		int repeatLength = 1; //一个字母连续重复出现的长度
		int place = 0;	//当前已经计算出的位数
		char[] charArray = romanStr.toCharArray();
		
		for (int i = charArray.length - 1; i >= 0; i--) { //从右往左遍历
			EBaseNumeral numeral =EBaseNumeral.build(charArray[i]);
			if (numeral.getPlace() <= place) { //后面出现的字符位数应该大于前面已经确定了的最大位数
				throw new IllegalArgumentException("非法的罗马数字格式");
			}
			if (i == 0) { //只剩下最后一个字符时，直接加上结果值，计算完成
				place = numeral.getPlace();	//进位
				result += numeral.getN();
				break;
			} 
			EBaseNumeral numeralAfter = EBaseNumeral.build(charArray[i-1]); //获取下一个字符
			if (!numeral.isRepeatable()) { //5开头的数字
				if (numeral.equals(numeralAfter)) { //下一个字符相同
					throw new IllegalArgumentException("非法的罗马数字格式");
				} else if (numeral.getPlace() < numeralAfter.getPlace()) { //下一个字符的位数大
					place = numeral.getPlace(); //进位
					result += numeral.getN();
				} else if (numeral.getPlace() == numeralAfter.getPlace()) { //下一个字符的位数不变并且是1开头的数字
					place = numeral.getPlace(); //进位
					result += numeral.getN() - numeralAfter.getN();
					i--;
				} else { //其他情况均非法
					throw new IllegalArgumentException("非法的罗马数字格式");
				}
			} else { //1字开头
				if (numeral.equals(numeralAfter)) {	//下一个字符相同
					if (repeatLength >= 3) { //字符连续出现次数超过3次
						throw new IllegalArgumentException("非法的罗马数字格式");
					}
					repeatLength++;
					result += numeral.getN();
					continue;
				} else if (numeral.getPlace() < numeralAfter.getPlace()){ //下一个字符的位数大
					place = numeral.getPlace(); //进位
					repeatLength = 0;
					result += numeral.getN();
				} else if (numeral.getPlace() == numeralAfter.getPlace()) { //下一个字符的位数不变并且是5开头的数字
					place = numeral.getPlace(); //进位
					repeatLength = 0;
					result += numeral.getN() + numeralAfter.getN();
					i--;
				} else if (numeral.getPlace() > numeralAfter.getPlace() && numeral.getN() == numeralAfter.getN() *10) { //下一个字符位数变10倍的变小
					place = numeralAfter.getPlace(); //进位
					repeatLength = 0;
					result += numeral.getN() - numeralAfter.getN();
					i--;
				} else {
					throw new IllegalArgumentException("非法的罗马数字格式");
				}
			}
		}
		return result;
	}
	
	/**
	 * @param intNumeral 只支持1-3999的数字转换
	 * @return
	 */
	public static String stringValue(int numberal){
		if (numberal < 1 || numberal > 3999) {
			throw new IllegalArgumentException("非法的罗马数字格式");
		}
		int baseNumberal = 0; //当前位的数字
		int place = 1; //位数
		String stringValue = "";
		while (numberal > 0) {
			baseNumberal = numberal % 10; // 获取位数
			switch (baseNumberal * place) {
				case 0: break;
				case 1: stringValue = "I" + stringValue; break;
				case 2: stringValue = "II" + stringValue; break;
				case 3: stringValue = "III" + stringValue; break;
				case 4: stringValue = "IV" + stringValue; break;
				case 5: stringValue = "V" + stringValue; break;
				case 6: stringValue = "VI" + stringValue; break;
				case 7: stringValue = "VII" + stringValue; break;
				case 8: stringValue = "VIII" + stringValue; break;
				case 9: stringValue = "IX" + stringValue; break;
				case 10: stringValue = "X" + stringValue; break;
				case 20: stringValue = "XX" + stringValue; break;
				case 30: stringValue = "XXX" + stringValue; break;
				case 40: stringValue = "XL" + stringValue; break;
				case 50: stringValue = "L" + stringValue; break;
				case 60: stringValue = "LX" + stringValue; break;
				case 70: stringValue = "LXX" + stringValue; break;
				case 80: stringValue = "LXXX" + stringValue; break;
				case 90: stringValue = "XC" + stringValue; break;
				case 100: stringValue = "C" + stringValue; break;
				case 200: stringValue = "CC" + stringValue; break;
				case 300: stringValue = "CCC" + stringValue; break;
				case 400: stringValue = "CD" + stringValue; break;
				case 500: stringValue = "D" + stringValue; break;
				case 600: stringValue = "DC" + stringValue; break;
				case 700: stringValue = "DCC" + stringValue; break;
				case 800: stringValue = "DCCC" + stringValue; break;
				case 900: stringValue = "CM" + stringValue; break;
				case 1000: stringValue = "M" + stringValue; break;
				case 2000: stringValue = "MM" + stringValue; break;
				case 3000: stringValue = "MMM" + stringValue; break;
				default: throw new IllegalArgumentException("非法的罗马数字格式");
			}
			numberal /= 10;
			place *= 10;
		}
		return stringValue;
		
	}
	
	
	//罗马数字基本字符
	private enum EBaseNumeral{
		
		I('I',1,1,true),
		V('V',5,1,false),
		X('X',10,2,true),
		L('L',50,2,false),
		C('C',100,3,true),
		D('D',500,3,false),
		M('M',1000,4,true),
		;
		
		char c;	//罗马字符
		int n;	//对应的阿拉伯数值
		int place;	//所在的位数：1表示个位，2表示10位，3表示百位，4表示千位
		boolean repeatable;	//当前字符可否连续多次出现，只有IXCM可以连续多次出现，VLD只能出现一次，即5开头的数字
		
		EBaseNumeral(char c, int n, int place, boolean repeatable){
			this.c = c;
			this.n = n;
			this.place = place;
			this.repeatable = repeatable;
		}

		public char getC() {
			return c;
		}

		public int getN() {
			return n;
		}

		public int getPlace() {
			return place;
		}

		public boolean isRepeatable() {
			return repeatable;
		}
		
		public static EBaseNumeral build(char c){
			switch (c) {
				case 'I': return I;
				case 'V': return V;
				case 'X': return X;
				case 'L': return L;
				case 'C': return C;
				case 'D': return D;
				case 'M': return M;
				default: throw new IllegalArgumentException("非法的罗马数字格式");
			}
		}
	}
	
	@Test
	public static void test(){
		int i = 0;
		while (i++ < 3999) {
			System.out.print("numberal=" + i);
			String s = null;
			try {
				s = stringValue(i);
				System.out.print(", stringValue()=" + s);
			} catch (Exception e) {
				System.out.print(", stringValue()=" + e.getMessage());
			}
			try {
				int n = intValue(s);
				System.out.print(", intValue()=" + n);
			} catch (Exception e) {
				System.out.print(", intValue()=" + e.getMessage());
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		test();
		
	}

}

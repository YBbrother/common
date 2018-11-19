package learn;

/**
 * 商品校验码的算法 ean-13条码算法 前12位的奇数位的和c1 前12位的偶数位的和c2 将奇数和跟偶数和的三倍相加 取结果的个位数，对十取余
 * （如果个位数是0，那么校验码不是10，而是0）
 */
public class EanCodeUtil {
	public static void main(String[] args) {
		String eanCode = eanCode("123675223432");
		System.out.println(eanCode);
	}

	public static String eanCode(String code) {
		int c1 = 0;
		int c2 = 0;
		for (int i = 0; i < code.length(); i += 2) {
			char c = code.charAt(i);
			// 字符串code中第i个位置上的字符
			int n = c - '0';
			c1 += n; // 累加奇数位的数字和
		}
		for (int i = 1; i < code.length(); i += 2) {
			char c = code.charAt(i); // 字符串code中第i个位置上的字符
			int n = c - '0';
			c2 += n; // 累加偶数位的数字和
		}
		int cc = c1 + c2 * 3;
		int check = cc % 10;
		check = (10 - cc % 10) % 10;
		code += check + "";
		return code;
	}
}

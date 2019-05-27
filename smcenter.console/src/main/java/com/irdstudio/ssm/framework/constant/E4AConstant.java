package com.irdstudio.ssm.framework.constant;

public class E4AConstant {

	public static Long TtimestampTimeOut = Long.valueOf(300000L);
	
	public static int TOKEN_TIMEOUT = 3600; 
	
	public static int VALIDATE_CODE_TIMEOUT = 300;
	
	public static final String USER_SESSION_KEY_CATCHE = "user:session:key:";
	
	public static final String VALIDATE_CODE_KEY_CATCHE = "validate:code:key:";
	
	/** cookies 登录sessionId键值 */
	public static final String COOKIES_KEY_SSO_CLIENT = "ssoclient";
	/** cookies 登录时间戳键值 */
	public static final String COOKIES_KEY_SSO_TIMESTAMP = "ssottp";
	/** cookies 登录md5校验键值 */
	public static final String COOKIES_KEY_SSO_MD5 = "ssomd5";
	
	/** */
	public static final String SECURITY_SALT = "123456";
	
	/**
	 * RSA私钥
	 */
	public static final String RSA_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMBjBDzWtzUpby1N" + 
			"inGstLcCtXEHjSi2ZtbJfCANBGQuwK5AqKropmAAz/ywm4jXZLUlU5ecy7l3jbS8" + 
			"oUVL/4hvPhPGqOEuaCjCdqC4xNsAYHMg+983mUvnaYOab8K8q7Zg8IW9lXxi6U1M" + 
			"J7G+6Ps6JZFRekBtpCq7m086MbK1AgMBAAECgYBFux3sFz8sa6o6V2KYtSqDMt4k" + 
			"68HEQeigbUMqMs2mqjN4dUMtP0oefezk//Y/8SI1biQQTeqhfK3GFqC1h0hEwHf5" + 
			"DmPnK/M5O2VjuT9VRDaLTe8IGja8Ntu0Z222cGp6VWMDpmGyjIz/dq8Qfvrd10Gx" + 
			"RIq8haAeXLWQ6X22eQJBAOFXljyVqtBg2ZHz1DaE2w0coB5ocQgtfmBbXRS75Uui" + 
			"3hke6TSW1n5EPd8T/GiHxEdrmJbPWrK03o/Ur0AfulcCQQDaj6G/IZ2HEEjjk7p7" + 
			"iQUmVDt09fReYPpdZEoNlFUhiFSK1olFqo4Hp13NAmM3Fv6VGYmdSf/fI5acSjfA" + 
			"JavTAkBvPS2M+tU3yJee1R8NSshX0Km6WRRzVQHtzyxD7/1hOSUqgXfd3hhEfKuO" + 
			"U+9mKp9aImNRD0tWrLKxjex1WzRBAkBByOW4b9fIZYBq73Y75LwmE0hqJfXNyobR" + 
			"QXSArdsJ4sz/lR9wVqu1Zgz3vfY7CLLMEgm3zCQ197JHjJdHQI99AkEAioozD+L7" + 
			"kzE/BTs6f7m23ID8roBLBzHHOll2N3RTbuVEmicCdXLj8FUlvl9Q6AhpxjdSNgcY" + 
			"uFEy4MCJRz0Ldg==";
}

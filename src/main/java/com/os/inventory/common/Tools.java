package com.os.inventory.common;

import java.util.Random;
import java.util.UUID;

public class Tools {

    public static  final String SSL_SMS_GATEWAY_USER_NAME = "bcpsbrand";
    public static final String SSL_SMS_GATEWAY_PASSWORD = "51>1R01s";
    public static final String SSL_SMS_GATEWAY_SID = "bcpsbrand";

    public static final String SSL_LIVE_STORE_ID = "bcpsfellowsannualsubscriptionlive";
    public static final String SSL_LIVE_STORE_PASSWORD = "64FEAC2BA7D1C76686";

    public static final String COMA = ",";
    public static final String COMA_AND_SPACE = ", ";
    public static final String STR_ZERO = "0";
    public static final String SINGLE_SPACE = " ";
    public static final String EMPTY_SPACE = "";
    public static final String COLON = ":";
    public static final String THREE_DOTS = "...";
    public static final String UNDERSCORE = "_";
    public static final String DASH = "-";
    public static final String DOT = ".";
    public static final String EXTENSION_PNG = "png";
    public static final String EXTENSION_PDF = "pdf";
    public static final String EXTENSION_JPG = "jpg";
    public static final String EXTENSION_XLSX = "xlsx";
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";
    public static final String IS_ERROR = "isError";
    public static final String IS_APP_EXCEPTION = "isException";
    public static final String IMG_FILE_LOCATION = "/imgfiles/";
    public static final String IMG_FILE_LOCATION_MEMBER = "/imgfiles/member/";
    public static final String IMG_FILE_LOCATION2 = "/asset/uploads/";
    public static final String XLSX_FILE_LOCATION = "/xlsxfiles/";
    public static final String RPT_FILE_LOCATION = "/WEB-INF/jasper/";
    public static final String COMMON_FILE_LOCATION = "/WEB-INF/files/";
    public static final String MESSAGE = "message";
    public static final String SUCCESS = "success";
    public static final String STATUS = "status";
    public static final String CODE = "code";
    public static final String DATA = "data";
    private static final String REGEX_SYMBOL_START = "\\Q";
    private static final String REGEX_SYMBOL_END = "\\E";
    public static String gerpLocalHostDirectory = null;
    static String baseURL, defaultImagePath;
    static final String DECIMAL_VALIDATION_CHECK = "Please input valid amount.";
    public static final String SLASH = "/";
    private static final String AMOUNT_FORMAT = "৳ ##,##,##0.00";
    private static final String AMOUNT_FORMAT_WITHOUT_CURRENCY = "##,##,##0.00";
    private static final String QUANTITY_FORMAT = "##,##,##0.0000";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String COLON_WITH_SLASH = "://";
    private static final String OPENING_WWW = "//www.";
    private static final String DOUBLE_SLASH = "//";
    public static final Long RTF_RECORD_LIMIT = 2000L;

    private static final String[] units = { EMPTY_SPACE, "One", "Two", "Three", "Four",
            "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
            "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen" };

    private static final String[] tens = {
            EMPTY_SPACE,// 0
            EMPTY_SPACE,// 1
            "Twenty", 	// 2
            "Thirty", 	// 3
            "Forty", 	// 4
            "Fifty", 	// 5
            "Sixty", 	// 6
            "Seventy",	// 7
            "Eighty", 	// 8
            "Ninety" 	// 9
    };

    /*
     * @Method description: This function is called from, where we want to convert number to word
     * @param: Integer Type
     * @return: String [Number in word] */
    public static String convert(final int n) {
        if (n < 0) {
            return "Minus " + convert(-n);
        }

        if (n < 20) {
            return units[n];
        }

        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? SINGLE_SPACE : EMPTY_SPACE) + units[n % 10];
        }

        if (n < 1000) {
            return units[n / 100] + " Hundred" + ((n % 100 != 0) ? SINGLE_SPACE : EMPTY_SPACE) + convert(n % 100);
        }

        if (n < 100000) {
            return convert(n / 1000) + " Thousand" + ((n % 10000 != 0) ? SINGLE_SPACE : EMPTY_SPACE) + convert(n % 1000);
        }

        if (n < 10000000) {
            return convert(n / 100000) + " Lakh" + ((n % 100000 != 0) ? SINGLE_SPACE : EMPTY_SPACE) + convert(n % 100000);
        }

        return convert(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? SINGLE_SPACE : EMPTY_SPACE) + convert(n % 10000000);
    }

    /*
     * @Method description: This function is called from, where we need random number or random string
     * @param: Integer Type, Boolean Type
     * @return: String [Random number]  */
    public static String randGen(int length, Boolean upperAlphabetEnabled) {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String alphaNumeric = null;
        alphaNumeric = (upperAlphabetEnabled) ? (upperAlphabet + numbers) : numbers;

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);

            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static  String getSSLSMSString(int rowId, String mobile, String message){
        return "sms" + "[" + rowId + "]" + "[" + 0 + "]" + "=" + mobile + "&"
                + "sms" + "[" + rowId + "]" + "[" + 1 + "]" + "=" + message + "&"
                + "sms" + "[" + rowId + "]" + "[" + 2 + "]" + "=" + UUID.randomUUID();
    }
}

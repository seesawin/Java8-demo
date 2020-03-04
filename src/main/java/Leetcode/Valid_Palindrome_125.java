package Leetcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Valid_Palindrome_125 {
    /**
     * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
     * <p>
     * Note: For the purpose of this problem, we define empty string as valid palindrome.
     * <p>
     * Example 1:
     * <p>
     * Input: "A man, a plan, a canal: Panama"
     * Output: true
     * Example 2:
     * <p>
     * Input: "race a car"
     * Output: false
     */
    public static void main(String[] args) {
        Valid_Palindrome_125 object = new Valid_Palindrome_125();
//        String test = "av@vcva";
//        String test = "A man, a plan, a canal: Panama";
//        String test = "A man, a plan, a canal -- Panama";
        String test = "0P";
        boolean palindrome = object.isPalindrome(test);
        System.out.println(palindrome);

    }

    public boolean isPalindrome(String s) {
        s = this.validtaeString(s);

        String left;
        String right;

        for (int i = 0; i < s.length() / 2; i++) {
            left = String.valueOf(s.charAt(i));
            right = String.valueOf(s.charAt(s.length() - 1 - i));

            if (!left.equals(right)) {
                return false;
            }
        }

        return true;
    }

    public String validtaeString(String str) {
        StringBuilder sb = new StringBuilder();
        str = str.toLowerCase();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if ((ch >= 'a' && ch <= 'z') || Character.isDigit(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }


    static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[`~!@#$%^&;*()+=|{}':;',//[//].<>/?~!@#￥%……&;*()——+|{}【】‘;:”“’。,、?]");

    // 過濾特殊字元
    public String StringFilter(String str) throws PatternSyntaxException {
        // 只允許字母和數字
        //String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字元
        String regEx = "[`~!@#$%^&;*()+=|{}':;',//[//].<>/?~!@#￥%……&;*()——+|{}【】‘;:”“’。,、?]";
        Pattern p = SPECIAL_REGEX_CHARS.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").replaceAll("\\s+","").toLowerCase().trim();
    }
}

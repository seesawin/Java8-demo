package Leetcode;

public class Reverse_Words_in_a_String_151 {

    /**
     * Given an input string, reverse the string word by word.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: "the sky is blue"
     * Output: "blue is sky the"
     * Example 2:
     * <p>
     * Input: "  hello world!  "
     * Output: "world! hello"
     * Explanation: Your reversed string should not contain leading or trailing spaces.
     * Example 3:
     * <p>
     * Input: "a good   example"
     * Output: "example good a"
     * Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
     * <p>
     * <p>
     * Note:
     * <p>
     * A word is defined as a sequence of non-space characters.
     * Input string may contain leading or trailing spaces. However, your reversed string should not contain leading or trailing spaces.
     * You need to reduce multiple spaces between two words to a single space in the reversed string.
     * <p>
     * <p>
     * Follow up:
     * <p>
     * For C programmers, try to solve it in-place in O(1) extra space.
     */
    public static void main(String[] args) {
//        String s = "a good   example";
//        String s = "the sky is blue";
        String s = "  hello world! ";
        Reverse_Words_in_a_String_151 object = new Reverse_Words_in_a_String_151();
        String s1 = object.reverseWords(s);
        System.out.println(s1);
    }

    public String reverseWords(String s) {
        String[] s1 = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = s1.length - 1; i >= 0; i--) {
            String word = s1[i];
            if (!"".equals(word)) {
                sb.append(String.valueOf(word)).append(" ");
            }
        }
        return sb.toString().trim();
    }
}

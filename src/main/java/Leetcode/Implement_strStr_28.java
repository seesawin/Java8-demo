package Leetcode;

public class Implement_strStr_28 {

    /**
     * Implement strStr().
     * <p>
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     * <p>
     * Example 1:
     * <p>
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     * Example 2:
     * <p>
     * Input: haystack = "aaaaa", needle = "bba"
     * Output: -1
     * Clarification:
     * <p>
     * What should we return when needle is an empty string? This is a great question to ask during an interview.
     * <p>
     * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
     */
    public static void main(String[] args) {
//        String haystack = "hello";
//        String needle = "wll";
//        String haystack = "a";
//        String needle = "a";
//        String haystack = "aaa";
//        String needle = "aaa";
        String haystack = "mississippi";
        String needle = "issip";


        Implement_strStr_28 object = new Implement_strStr_28();
        int i = object.strStr(haystack, needle);
        System.out.println("i: " + i);
    }

    public int strStr(String haystack, String needle) {
        if(needle.equals("")) {
            return 0;
        }

        int startIndex = 0;
        int currentNiddleCheckIndex = 0;
        boolean startToCheck = false;
        int nextTestIndex = 0;
        char[] charArray = haystack.toCharArray();
        for (int i = 0; i < haystack.length(); i++) {
            if (!startToCheck && charArray[i] == needle.charAt(0)) {
                currentNiddleCheckIndex++;
                startToCheck = true;
                startIndex = i;
                if (currentNiddleCheckIndex == needle.length()) {
                    return startIndex;
                }
                continue;
            }

            //cache the next possibie
            if (startToCheck && charArray[i] == needle.charAt(0)) {
                if(nextTestIndex == 0) {
                    nextTestIndex = i;
                }
            }

            if (currentNiddleCheckIndex > 0) {
                if (charArray[i] == needle.charAt(currentNiddleCheckIndex++)) {
                    if (currentNiddleCheckIndex == needle.length()) {
                        return startIndex;
                    }
                } else {
                    currentNiddleCheckIndex = 0;
                    startToCheck = false;
                    if(nextTestIndex > 0) {
                        i = nextTestIndex - 1;
                    }
                    nextTestIndex = 0;
                }
            }
        }
        return -1;
    }
}

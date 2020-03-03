package Leetcode;

public class Palindrome_Number_9 {
    /**
     * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
     * <p>
     * Example 1:
     * <p>
     * Input: 121
     * Output: true
     * Example 2:
     * <p>
     * Input: -121
     * Output: false
     * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
     * Example 3:
     * <p>
     * Input: 10
     * Output: false
     * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
     * Follow up:
     * <p>
     * Coud you solve it without converting the integer to a string?
     */
    public static void main(String[] args) {
        int x = 0;
        Palindrome_Number_9 object = new Palindrome_Number_9();
        boolean palindrome = object.isPalindrome(x);
        System.out.println(palindrome);

    }

    public boolean isPalindrome(int x) {
        boolean resultFlag = false;
        int oriX = x;
        if (x >= 0) {
            int result = 0;
            while (x != 0) {
                result = (result * 10) + x % 10;
                x /= 10;
            }
            if (result == oriX) {
                resultFlag = true;
            }
        } else {}
        return resultFlag;
    }
}

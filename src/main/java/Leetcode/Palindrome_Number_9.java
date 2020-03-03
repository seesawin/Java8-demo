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
        int x = 3223;
        Palindrome_Number_9 object = new Palindrome_Number_9();
        boolean palindrome = object.isPalindrome(x);
        System.out.println(palindrome);

        boolean palindrome2 = object.IsPalindrome2(x);
        System.out.println(palindrome2);

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

    //official solution
    public boolean IsPalindrome2(int x) {
        // Special cases:
        // As discussed above, when x < 0, x is not a palindrome.
        // Also if the last digit of the number is 0, in order to be a palindrome,
        // the first digit of the number also needs to be 0.
        // Only 0 satisfy this property.
        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while(x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // When the length is an odd number, we can get rid of the middle digit by revertedNumber/10
        // For example when the input is 12321, at the end of the while loop we get x = 12, revertedNumber = 123,
        // since the middle digit doesn't matter in palidrome(it will always equal to itself), we can simply get rid of it.
        return x == revertedNumber || x == revertedNumber/10;
    }
}

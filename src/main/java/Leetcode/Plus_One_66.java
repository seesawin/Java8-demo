package Leetcode;

public class Plus_One_66 {
    /**
     * Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
     * <p>
     * The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.
     * <p>
     * You may assume the integer does not contain any leading zero, except the number 0 itself.
     * <p>
     * Example 1:
     * <p>
     * Input: [1,2,3]
     * Output: [1,2,4]
     * Explanation: The array represents the integer 123.
     * Example 2:
     * <p>
     * Input: [4,3,2,1]
     * Output: [4,3,2,2]
     * Explanation: The array represents the integer 4321.
     */
    public static void main(String[] args) {
        int[] array = new int[]{9, 9, 9, 9, 9, 9};
        Plus_One_66 plusOne = new Plus_One_66();
        array = plusOne.plusOne(array);
        for (int i : array) {
            System.out.println(i);
        }
    }

    public int[] plusOne(int[] digits) {
        boolean addOneFlag = true;
        //loop from end
        for (int i = digits.length - 1; i >= 0; i--) {
            // add one to the last element or
            // add one to the now element, if the previous value is 10
            if (addOneFlag || i == digits.length - 1) {
                digits[i] = digits[i] + 1;
            }
            if (digits[i] == 10) {
                digits[i] = 0;
                addOneFlag = true;
            } else {
                addOneFlag = false;
            }
        }
        if (digits[0] == 0) {
            digits = expandingArray(digits);
        }
        return digits;
    }

    public int[] expandingArray(int[] digits) {
        int[] newDigits = new int[digits.length + 1];
        newDigits[0] = 1;
        //copy old array to a new array
        for (int j = 1; j < newDigits.length - 1; j++) {
            newDigits[j] = digits[j];
        }
        return newDigits;
    }
}

package Leetcode;

public class Reverse_String_344 {
    /**
     * Write a function that reverses a string. The input string is given as an array of characters char[].
     * <p>
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     * <p>
     * You may assume all the characters consist of printable ascii characters.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: ["h","e","l","l","o"]
     * Output: ["o","l","l","e","h"]
     * Example 2:
     * <p>
     * Input: ["H","a","n","n","a","h"]
     * Output: ["h","a","n","n","a","H"]
     */
    public static void main(String[] args) {
        char[] s1 = new char[]{'H', 'a', 'n', 'n', 'a', 'h'};
        char[] s2 = new char[]{'1', '2', '3', '4', '5', '6'};
        Reverse_String_344.reverseString(s1);
        System.out.println("----------------------------");
        Reverse_String_344.reverseStringFromEnd(s2);
    }

    public static void reverseString(char[] s) {
        int position = s.length - 1;
        // reverse from top index to end
        for (int i = 0; i < s.length / 2; i++) {
            chanageTwoValuesOfArray(i, position - i, s);
        }

        for (char c : s) {
            System.out.println(c);
        }
    }

    public static void reverseStringFromEnd(char[] s) {
        int position = s.length - 1;
        // reverse from end index to top
        for (int i = position; i > s.length / 2; i--) {
            chanageTwoValuesOfArray(i, position - i, s);
        }

        for (char c : s) {
            System.out.println(c);
        }
    }

    public static void chanageTwoValuesOfArray(int firstIndex, int secondIndex, char[] s) {
        char tempValue = s[firstIndex];
        s[firstIndex] = s[secondIndex];
        s[secondIndex] = tempValue;
    }
}

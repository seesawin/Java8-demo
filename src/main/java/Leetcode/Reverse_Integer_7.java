package Leetcode;

import java.util.ArrayList;

public class Reverse_Integer_7 {
    /**
     * Given a 32-bit signed integer, reverse digits of an integer.
     * <p>
     * Example 1:
     * <p>
     * Input: 123
     * Output: 321
     * Example 2:
     * <p>
     * Input: -123
     * Output: -321
     * Example 3:
     * <p>
     * Input: 120
     * Output: 21
     * Note:
     * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1].
     * For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
     */
    public static void main(String[] args) {
//        int digits = 65432;
//        int digits = 1534236469;
        int digits = -65432;
        Reverse_Integer_7 object = new Reverse_Integer_7();
//        int result = object.reverse(digits);
//        System.out.println(result);

//        int result2 = object.reverse2(digits);
//        System.out.println(result2);

//        int result3 = object.reverse3(digits);
//        System.out.println(result3);

        int result4 = object.reverse4(digits);
        System.out.println(result4);

        int result5 = object.reverse5(digits);
        System.out.println(result5);
    }

    public int reverse(int x) {
        boolean isPostitveFlag = true;
        if (x < 0) {
            isPostitveFlag = false;
            x = x * -1;
        }

        String s = String.valueOf(x);
        int length = s.length();
        StringBuilder sb = new StringBuilder();
        for (int i = length; i > 0; i--) {
            int start = i - 1;
            int end = i;
            sb.append(s.substring(start, end));
        }

        int finalInt = 0;
        try {
            finalInt = Integer.valueOf(sb.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (!isPostitveFlag) {
            finalInt = finalInt * -1;
        }

        return finalInt;
    }

    public int reverse2(int x) {
        if (x >= 2147483647 || x <= -2147483648) {
            return 0;
        }

        boolean isPostitveFlag = true;
        if (x < 0) {
            isPostitveFlag = false;
            x = x * -1;
        }

        ArrayList<Integer> arrayList = new ArrayList<>();
        int temp = x;
        do {
            // get the last number
            arrayList.add(temp % 10);
            // remove the last number
            temp = temp / 10;
        } while (temp > 0);

        int finalInt = 0;
        Double powerCount = Integer.valueOf(arrayList.size()).doubleValue();
        for (int i = 0; i < arrayList.size(); i++) {
            double i1 = Math.pow(10, --powerCount);
            Integer integer = arrayList.get(i);
            finalInt += integer * i1;
        }

        if (finalInt >= 2147483647 || finalInt <= -2147483648) {
            return 0;
        }

        if (!isPostitveFlag) {
            finalInt = finalInt * -1;
        }

        return finalInt;
    }

    // solution
    public int reverse3(int x) {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(-x)).append("-");
        StringBuilder reverse = stringBuilder.reverse();
        String ans = x < 0 ? new StringBuilder(String.valueOf(-x)).append("-").reverse().toString()
                : new StringBuilder(String.valueOf(x)).reverse().toString();
        try {
            return Integer.parseInt(ans);
        } catch (Exception e) {
            return 0;
        }
    }

    public int reverse4(int x) {
        Long result = 0L;

        boolean isPostitveFlag = true;
        if (x < 0) {
            isPostitveFlag = false;
            x = x * -1;
        }

        int length = String.valueOf(x).length();
        while (length > 0) {
            result = (result * 10) + (x % 10);
            x /= 10;
            length--;
        }

        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }

        if (!isPostitveFlag) {
            result = result * -1;
        }

        return result.intValue();
    }

    //solution V
    public int reverse5(int x) {
        long result = 0;
        while (x != 0) {
            result = (result * 10) + (x % 10);
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                return 0;
            }
            x /= 10;
        }
        return (int) result;
    }


}

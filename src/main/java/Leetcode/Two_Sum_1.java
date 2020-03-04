package Leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Two_Sum_1 {
    /**
     * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
     * <p>
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     * <p>
     * Example:
     * <p>
     * Given nums = [2, 7, 11, 15], target = 9,
     * <p>
     * Because nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     */
    public static void main(String[] args) {
//        int[] array = new int[]{0, 2, 7, 11, 15, 0};
//        int[] array = new int[]{3, 2, 95, -2, -3};
        int[] array = new int[]{3, 3};

        Two_Sum_1 object = new Two_Sum_1();
//        int[] ints = object.twoSum(array, 0);
//        int[] ints = object.twoSum(array, 0);
        int[] ints = object.twoSum3(array, 6);
//        int[] ints = object.twoSum4(array, 6);
        if (ints == null) {
            System.out.println(ints);
            return;
        }
        Arrays.stream(ints).forEach(System.out::println);
    }

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int base = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (base + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for(int i = 0; i < nums.length; i++) {
            int s = target - nums[i];
            Integer j = map.get(s);
            if(j != null && j != i) {
                return new int[] {i, j};
            }
        }
        return null;
    }

    public int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            int s = target - nums[i];
            Integer j = map.get(s);
            if(j != null) {
                return new int[] {j, i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    // official solution
    public int[] twoSum4(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}

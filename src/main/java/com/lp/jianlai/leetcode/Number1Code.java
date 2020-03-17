package com.lp.jianlai.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liupu
 * @Description: 两数之和：给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * @Date: 2020/3/17
 */
public class Number1Code {
    public static void main(String[] args) {
        int[] nums = {1,4,6,8,9,19};
        int target = 14;
        System.out.println(JSON.toJSON(twoSum(nums,target)));
    }
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            int subNum = target - nums[i];
            if(!map.containsKey(subNum)){
                map.put(nums[i], i);
            }else{
                int mark = map.get(subNum);
                return new int[]{mark,i};
            }
        }
        return null;
    }
}

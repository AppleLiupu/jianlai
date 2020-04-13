//package com.lp.jianlai.leetcode;
//
///**
// * @Author: liupu
// * @Description:无重复字符的最长子串
// * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
// *
// * 示例 1:
// *
// * 输入: "abcabcbb"
// * 输出: 3
// * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// * @Date: 2020/4/13
// */
//public class Number3Code {
//    public int lengthOfLongestSubstring(String s) {
//        int max = 0;
//        String maxStr = "";
//        StringBuilder tempStr = new StringBuilder();
//        char[] arr = s.toCharArray();
//        for(char c : arr){
//            int pos = tempStr.indexOf(String.valueOf(c));
//            if(pos >= 0){
//                if(tempStr.length() > max){
//                    max = tempStr.length();
//                    maxStr = tempStr.toString();
//                }
//                tempStr = tempStr.delete(0, pos + 1);
//            }
//            tempStr.append(c);
//        }
//
//        if(tempStr.length() > max){
//            max = tempStr.length();
//            maxStr = tempStr.toString();
//        }
//        System.out.println(maxStr);
//        return max;
//    }
//
//    /**
//     * 高手答案只用下标
//     * @param s
//     * @return
//     */
//    public int lengthOfLongestSubstring2(String s) {
//        int maxLength = 0;
//        char[] chars = s.toCharArray();
//        int leftIndex = 0;
//        for (int j = 0; j < chars.length; j++) {
//            for (int innerIndex = leftIndex; innerIndex < j; innerIndex++) {
//                if (chars[innerIndex] == chars[j]) {
//                    maxLength = Math.max(maxLength, j - leftIndex);
//                    leftIndex = innerIndex + 1;
//                    break;
//                }
//            }
//        }
//        return Math.max(chars.length - leftIndex, maxLength);
//    }
//
//    public static void main(String[] args) {
//        String s = "abccdfe";
//        Number3Code code = new Number3Code();
//        System.out.println(code.lengthOfLongestSubstring(s));
//    }
//}
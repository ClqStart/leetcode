package LeetCode.堆;

import java.util.HashMap;
import java.util.PrimitiveIterator;
import java.util.Stack;

//https://leetcode-cn.com/problems/valid-parentheses/solution/
public class _20_有效的括号 {
    class Solution {
        public boolean isValid(String s) {
            Stack<Character> stack = new Stack<>();
            int len = s.length();
            for (int i = 0; i < len; i++) {
                char c = s.charAt(i);
                if (c == '(' || c == '[' || c == '{') {
                    stack.push(c);
                } else {
                    if (stack.isEmpty()) return false;
                    char left = stack.pop();
                    if (left == '(' && c != ')') return false;
                    if (left == '[' && c != ']') return false;
                    if (left == '{' && c != '}') return false;
                }

            }
            return stack.isEmpty();
        }
    }

    class Solution1 {
        public boolean isValid(String s) {
            while (s.contains("{}") || s.contains("[]") || s.contains("()")) {
                s = s.replace("{}", "");
                s = s.replace("()", "");
                s = s.replace("[]", "");

            }
            return s.isEmpty();
        }
    }

    class Solution2 {
        public boolean isValid(String s) {
            HashMap<Character, Character> map = new HashMap<>();
            map.put('(', ')');
            map.put('[', ']');
            map.put('{', '}');
            Stack<Character> stack = new Stack<>();
            int len = s.length();
            for (int i = 0; i < len; i++) {
                char c = s.charAt(i);
                if(map.containsKey(c)){
                    stack.push(c);
                }else {
                    if(stack.isEmpty()) return false;
                    if(c!=map.get(stack.pop())) return  false;
                }
            }
            return  stack.isEmpty();

        }
    }
}

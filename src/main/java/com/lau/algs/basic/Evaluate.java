
package com.lau.algs.basic;

import org.apache.commons.lang.math.NumberUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author lau
 */
public class Evaluate {

    /**
     * 使用括号忽略运算符的优先级
     * 
     * @param exp
     * @return
     */
    public static Double compute(String exp) {
        Set<String> opSet = new HashSet<String>();
        opSet.add("+");
        opSet.add("-");
        opSet.add("*");
        opSet.add("/");
        String[] expArr = exp.split(" ");
        Stack<String> ops = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();
        for (String it : expArr) {
            if (it.equals("(")) {
            } else if (it.equals(")")) {
                String op = ops.pop();
                double val = vals.pop();
                if (op.equals("+"))
                    val = vals.pop() + val;
                else if (op.equals("-"))
                    val = vals.pop() - val;
                else if (op.equals("*"))
                    val = vals.pop() * val;
                else if (op.equals("/"))
                    val = vals.pop() / val;
                vals.push(val);
            } else if (opSet.contains(it)) {
                ops.push(it);
            } else {
                vals.push(NumberUtils.toDouble(it));
            }

        }

        return vals.pop();
    }

    public static void main(String args[]) {
        System.out.println(compute("( ( 1 - 2 ) * 3 )"));
    }
}

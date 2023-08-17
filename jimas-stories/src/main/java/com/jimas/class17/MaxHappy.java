package com.jimas.class17;

import java.util.ArrayList;
import java.util.List;

/**
 * 多叉树：
 * 要求直接上下级不能同时参加聚会,求happy之和最大
 * 比如X 有a\b\c 三个下级，可能性：
 * 1)、X参加
 * ①、a不参加,a的下属最大happy值
 * ②、b不参加,b的下属最大happy值
 * ③、c不参加,c的下属最大happy值
 * 2)、X不参加
 * ①、max(a参加,a不参加)
 * ②、max(b参加,b不参加)
 * ③、max(c参加,c不参加)
 *
 * @author liuqj
 */
public class MaxHappy {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int height = (int) (Math.random() * 10);
            Employee boss = generateEmployee(height);
            if (printMaxHappy1(boss) != printMaxHappy2(boss)) {
                System.out.println("error");
            }
        }
        System.out.println("success");
    }

    private static int printMaxHappy2(Employee boss) {

        return Math.max(process2(boss, false), process2(boss, true));
    }

    private static int process2(Employee x, boolean come) {
        if (x == null) {
            return 0;
        }
        // 上级来
        if (come) {
            int happy = x.happy;
            for (Employee employee : x.next) {
                happy += process2(employee, false);
            }
            return happy;
        }
        // 上级不来
        int happy = 0;
        for (Employee employee : x.next) {
            int nextHappyTrue = process2(employee, false);
            int nextHappyFalse = process2(employee, true);
            happy += Math.max(nextHappyFalse, nextHappyTrue);
        }
        return happy;
    }

    private static int printMaxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        Info process = process(boss);
        return Math.max(process.yes, process.no);
    }

    private static Info process(Employee x) {
        if (x == null) {
            return new Info(0, 0);
        }
        int yes = x.happy;
        int no = 0;
        for (Employee employee : x.next) {
            Info nextInfo = process(employee);
            yes += nextInfo.no;
            no += Math.max(nextInfo.yes, nextInfo.no);
        }
        return new Info(yes, no);

    }


    private static class Employee {
        int happy;
        List<Employee> next;

        public Employee(int happy) {
            this.happy = happy;
        }
    }


    private static class Info {
        /**
         * 节点参加时 最大happy值
         */
        int yes;
        /**
         * 节点不参加时 最大happy值
         */
        int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    private static Employee generateEmployee(int height) {
        if (height < 1) {
            return null;
        }
        int happy = (int) (Math.random() * 100);
        Employee boss = new Employee(happy);
        List<Employee> next = new ArrayList<>();
        boss.next = next;
        if (Math.random() > 0.4) {
            int width = (int) (Math.random() * 10);
            for (int i = 0; i < width; i++) {
                next.add(generateEmployee(height - 1));
            }
        }
        return boss;

    }
}

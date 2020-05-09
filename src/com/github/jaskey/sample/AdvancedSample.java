package com.github.jaskey.sample;

import com.github.jaskey.ProportionalSelector;

import java.util.Arrays;
import java.util.List;

public class AdvancedSample {
    public static void main(String[] args) {

        {

            System.out.println("===============Advanced sample output=====================");

            //使用自定义的候选对象
            MyCandidate c1 = new MyCandidate("service1", 1, "服务商1");
            MyCandidate c2 = new MyCandidate("service2", 2, "服务商2");
            MyCandidate c3 = new MyCandidate("service3", 3, "服务商3");
            MyCandidate c4 = new MyCandidate("service4", 4, "服务商4");

            List<MyCandidate> testCandidates = Arrays.asList(c1, c2, c3, c4);
            ProportionalSelector<MyCandidate> selector = new ProportionalSelector<>(testCandidates);

            for (int i=0 ;i < 20; i++) {
                System.out.println(selector.selectOne());
                System.out.println(selector.select(2));
            }


            System.out.println("===============update the weight, and reexamine again=====================");

            //动态更新权重，实际使用可能是数据库的配置修改了
            c1.setWeight(4);
            c2.setWeight(3);
            c3.setWeight(2);
            c4.setWeight(1);

            for (int i=0 ;i < 20; i++) {
                System.out.println(selector.selectOne());
                System.out.println(selector.select(2));
            }


            System.out.println("===============remove service 4 and add service 5===============");
            //删除服务商4
            selector.getCandidateSet().remove(c4);
            //增加服务商5
            MyCandidate c5 = new MyCandidate("service5", 4, "服务商5");
            selector.getCandidateSet().add(c5);

            for (int i=0 ;i < 20; i++) {
                System.out.println(selector.selectOne());
                System.out.println(selector.select(2));
            }
        }
    }
}

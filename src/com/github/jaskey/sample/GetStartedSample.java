package com.github.jaskey.sample;



import com.github.jaskey.ProportionalSelector;
import com.github.jaskey.SimpleCandidate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by linjunjie1103@gmail.com on 2020/5/8.
 */
public class GetStartedSample {

    public static void main(String[] args) {

        {
            //使用内置的简单的候选对象，用户也可以使用自定义对象，实现ICandidate接口即可
            SimpleCandidate c1 = new SimpleCandidate("service1", 1);
            SimpleCandidate c2 = new SimpleCandidate("service2", 2);
            SimpleCandidate c3 = new SimpleCandidate("service3", 3);
            SimpleCandidate c4 = new SimpleCandidate("service4", 4);

            List<SimpleCandidate> testCandidates = Arrays.asList(c1, c2, c3, c4);
            ProportionalSelector<SimpleCandidate> selector = new ProportionalSelector<>(testCandidates);

            System.out.println("--------按概率获取一个元素---------------");
            for (int i=0 ;i < 10; i++) {
                System.out.println(selector.selectOne());
            }

            System.out.println("--------每次按概率获取2个元素，适用于诸如多个服务商选择一个服务商，但一开始选择的不可用时可以重试后备服务商的场景---------------");
            for (int i=0 ;i < 10; i++) {
                System.out.println(selector.select(2));
            }
        }


    }










}

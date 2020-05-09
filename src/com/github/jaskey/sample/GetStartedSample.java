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
            //使用简单的候选对象
            SimpleCandidate c1 = new SimpleCandidate("service1", 1);
            SimpleCandidate c2 = new SimpleCandidate("service2", 2);
            SimpleCandidate c3 = new SimpleCandidate("service3", 3);
            SimpleCandidate c4 = new SimpleCandidate("service4", 4);


            List<SimpleCandidate> testCandidates = Arrays.asList(c1, c2, c3, c4);
            ProportionalSelector<SimpleCandidate> selector = new ProportionalSelector<>(testCandidates);

            for (int i=0 ;i < 20; i++) {
                System.out.println(selector.selectOne());
                System.out.println(selector.select(2));
            }
        }


    }










}

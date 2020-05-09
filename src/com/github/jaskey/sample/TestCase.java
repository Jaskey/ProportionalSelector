package com.github.jaskey.sample;

import com.github.jaskey.ProportionalSelector;
import com.github.jaskey.ICandidate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linjunjie1103@gmail.com on 2020/5/8.
 * 用来检查概率是否正确
 */
public class TestCase {

    private static final int TOTAL_TEST_CNT = 50000;//实验次数

    public static void main(String[] args)  {
        MyCandidate c1 = new MyCandidate("id1", 1, "other1");
        MyCandidate c2 = new MyCandidate("id2", 2, "other2");
        MyCandidate c3 = new MyCandidate("id3", 3, "other3");
        MyCandidate c4 = new MyCandidate("id4", 4, "other4");
        testChoose1From4(c1, c2, c3, c4);
        testChoose3From4(c1, c2, c3, c4);
    }



    private static void testChoose1From4(ICandidate c1, ICandidate c2, ICandidate c3, ICandidate c4) {
        List<? extends ICandidate> testCandidates = Arrays.asList(c1, c2, c3, c4);
        ProportionalSelector<? extends ICandidate> selector = new ProportionalSelector<>(testCandidates);
        int totalWeight = 0;

        System.out.println("Examine case : TOTAL_TEST_CNT : " + TOTAL_TEST_CNT + " testCandidates : " + testCandidates);
        System.out.println("====test select one ======= ");

        for (ICandidate c : testCandidates) {
            totalWeight+=c.weight();
        }

        for (ICandidate c : testCandidates) {
            System.out.println("expect chosen probability of " +c.id()+ ":" +1.0 * c.weight() / totalWeight);
        }



        List<Integer> chosenCntList = new ArrayList<>(testCandidates.size());

        //initialize chosen result with 0
        for (int i = 0; i< testCandidates.size(); i++) {
            chosenCntList.add(0);
        }

        for (int i = 0; i < TOTAL_TEST_CNT; i++) {
            ICandidate res = selector.selectOne();
            int chosenIndex = testCandidates.indexOf(res);
            int oldValue = chosenCntList.get(chosenIndex);
            int newValue = oldValue+1;
            chosenCntList.set(chosenIndex, newValue);
        }

        for (int i = 0; i< chosenCntList.size(); i++) {
            ICandidate c = testCandidates.get(i);
            int chosenCnt = chosenCntList.get(i);
            System.out.println("actual chosen probability of " + c.id() +":"+ 1.0 * chosenCnt / TOTAL_TEST_CNT);

        }

    }


    private static void testChoose3From4(ICandidate c1, ICandidate c2, ICandidate c3, ICandidate c4) {

        List<? extends ICandidate> testCandidates = Arrays.asList(c1, c2, c3, c4);
        ProportionalSelector<? extends ICandidate> selector = new ProportionalSelector<>(testCandidates);
        int totalWeight = 0;

        for (ICandidate c : testCandidates) {
            totalWeight+=c.weight();
        }

        double expect123 = (1.0 * c1.weight() / totalWeight) * (1.0 * c2.weight() / (totalWeight - c1.weight())) * (1.0 * c3.weight() / (totalWeight - c1.weight() - c2.weight()));//[c1,c2,c3]
        double expect124 = (1.0 * c1.weight() / totalWeight) * (1.0 * c2.weight() / (totalWeight - c1.weight())) * (1.0 * c4.weight() / (totalWeight - c1.weight() - c2.weight()));//[c1,c2,c4]
        double expect234 = (1.0 * c2.weight() / totalWeight) * (1.0 * c3.weight() / (totalWeight - c2.weight())) * (1.0 * c4.weight() / (totalWeight - c2.weight() - c3.weight()));//[c2,c3,c4]
        double expect432 = (1.0 * c4.weight() / totalWeight) * (1.0 * c3.weight() / (totalWeight - c4.weight())) * (1.0 * c2.weight() / (totalWeight - c4.weight() - c3.weight()));//[c4,c3,c2]

        int cnt123 = 0;
        int cnt124 = 0;
        int cnt234 = 0;
        int cnt432 = 0;
        System.out.println("====test select three from four=======");
        for (int i = 0; i < TOTAL_TEST_CNT; i++) {
            List<? extends ICandidate> res = selector.select(3);

            if (res.equals(Arrays.asList(c1, c2, c3))) {
                cnt123++;
            } else if (res.equals(Arrays.asList(c1, c2, c4))) {
                cnt124++;
            } else if (res.equals(Arrays.asList(c2, c3, c4))) {
                cnt234++;
            } else if (res.equals(Arrays.asList(c4, c3, c2))) {
                cnt432++;
            }
            //System.out.println(res);
        }

        System.out.println("expect123 = " + expect123 + ", actual123 = " + 1.0 * cnt123 / TOTAL_TEST_CNT);
        System.out.println("expect124 = " + expect124 + ", actual124 = " + 1.0 * cnt124 / TOTAL_TEST_CNT);
        System.out.println("expect234 = " + expect234 + ", actual234 = " + 1.0 * cnt234 / TOTAL_TEST_CNT);
        System.out.println("expect432 = " + expect432 + ", actual432 = " + 1.0 * cnt432 / TOTAL_TEST_CNT);
    }

}

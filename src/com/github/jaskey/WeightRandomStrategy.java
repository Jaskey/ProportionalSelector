package com.github.jaskey;



import java.util.*;

/**
 * Created by linjunjie1103@gmail.com on 2019/7/10.
 */
public class WeightRandomStrategy<T extends ICandidate> implements SelectStrategy<T> {



    /**
     * 按照权重挑选一个候选
     * @return
     */
    @Override
    public T selectOne(Set<T> candidateSet) {
        if (candidateSet== null  || candidateSet.isEmpty()){
            return null;
        }

        Random random = new Random();
        int weightSum = 0;

        List<T> validCandidateList = new ArrayList<>();//<=0的候选忽略不计
        for (T c : candidateSet) {
            int weight = c.weight();
            if (weight > 0) {
                weightSum += c.weight();
                validCandidateList.add(c);
            }
        }


        if (weightSum == 0) {//全部都是0，降级
            for (T c : candidateSet) {
                //All candidate's weight is zero, just select one
                return c;
            }
        }

        //以下是正常流程
        //[0,weightSum)
        int randomNum = random.nextInt(weightSum);
        int preSum = 0;

        for (int i = 0; i <validCandidateList.size(); i++) {
            T candidate = validCandidateList.get(i);
            preSum +=candidate.weight();

            if (randomNum < preSum) {
                return candidate;
            }
        }


        return validCandidateList.get(0);
    }



    @Override
    public List<T> select(final Set<T> candidateSet, int selectCnt) {

        //首选
        T firstChoice = this.selectOne(candidateSet);
        Set<T> remainsCandidates = new HashSet<>(candidateSet);//复制候选列表
        List<T> resList = new ArrayList<>(selectCnt);

        resList.add(firstChoice);

        //选备用
        ICandidate lastChosen = firstChoice;
        for (int i = 1; i< selectCnt; i++) {
            remainsCandidates.remove(lastChosen);//把优选剔除
            ProportionalSelector<T> flowRatioBackup = new ProportionalSelector(remainsCandidates);
            T backupCandidate = flowRatioBackup.selectOne();//再选一次就是备选
            resList.add(backupCandidate);
            lastChosen = backupCandidate;
        }

        return resList;
    }


}

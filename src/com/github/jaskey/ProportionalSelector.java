package com.github.jaskey;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by linjunjie1103@gmail.com on 2019/7/10.
 *
 * 流量配比管理器，当需要多个候选列表中通过某个策略选择一个时候使用，设计可支持多个算法，目前支持加权平均
 * 设计成线程安全，候选实体可动态修改
 */
public class ProportionalSelector<T extends ICandidate> {



    private final Set<T> candidateSet = new CopyOnWriteArraySet<>();


    // 策略设计模式
    // 流量配比策略，默认使用加权随机策略，可自定义扩展其他策略
    private SelectStrategy<T> selectStrategy = new WeightRandomStrategy<T>();

    public ProportionalSelector(Collection<T> candidates) {
        for (T c : candidates) {
            int weight  = c.weight();
            if (weight < 0) {
                throw new IllegalArgumentException("weight < 0 , "+c.id());
            }

            candidateSet.add(c);
        }
    }

    public void setSelectStrategy(SelectStrategy<T> selectStrategy) {
        this.selectStrategy = selectStrategy;
    }

    public Set<T> getCandidateSet() {
        return candidateSet;
    }


    /**
     * 按概率返回一个候选实体
     * @return
     */
    public T selectOne() {
        Set<T> candidateSetCurrent = new HashSet<>(candidateSet);//取当前的快照，避免并发被动态修改
        return selectStrategy.selectOne(candidateSetCurrent);
    }


    /**
     * 按概率返回多个候选实体
     * @param selectCnt
     * @return
     */
    public List<T> select(int selectCnt) {
        Set<T> candidateSetCurrent = new HashSet<>(candidateSet);//取当前的快照，避免并发被动态修改
        int sizeCurrent = candidateSetCurrent.size();
        if (selectCnt >= sizeCurrent) {
            selectCnt = sizeCurrent;
        }
        return selectStrategy.select(candidateSetCurrent,selectCnt);
    }







}



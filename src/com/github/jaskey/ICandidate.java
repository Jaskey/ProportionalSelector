package com.github.jaskey;

/**
 * Created by linjunjie1103@gmail.com on 2019/7/11.
 * 候选实体接口，把需要挑选的实体类需要实现此接口，定义其ID和权重
 */
public interface ICandidate {
    String id();

    //对应的权重
    int weight();
}

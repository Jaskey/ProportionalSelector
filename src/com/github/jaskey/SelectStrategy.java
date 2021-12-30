package com.github.jaskey;

import java.util.List;
import java.util.Set;

/**
 * Created by linjunjie1103@gmail.com on 2019/7/10.
 * 策略设计模式，用以实际选择候选实体
 */
public interface SelectStrategy<T> {
      
      T selectOne(Set<T> candidateSet);

      /**
       * 挑选多个候选
       * 适用类似以下场景：三个候选服务商，需要按权重选择一个，但是我们需要保证一开始选择的供应商接口不可用的情况下，还能重新选，那么就可以使用此方法，一下子选择两个候选的服务商，
       * @return
       */
      List<T> select(Set<T> candidateSet, int selectCnt) ;
}

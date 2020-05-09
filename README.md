# ProportionalSelector
按权重以指定概率/比例的方式获取元素的Java实现工具类。


# Get Started

            //使用简单的候选对象
            SimpleCandidate c1 = new SimpleCandidate("service1", 1);
            SimpleCandidate c2 = new SimpleCandidate("service2", 2);
            SimpleCandidate c3 = new SimpleCandidate("service3", 3);
            SimpleCandidate c4 = new SimpleCandidate("service4", 4);


            List<SimpleCandidate> testCandidates = Arrays.asList(c1, c2, c3, c4);
            ProportionalSelector<SimpleCandidate> selector = new ProportionalSelector<>(testCandidates);

            for (int i=0 ;i < 20; i++) {
                System.out.println(selector.chooseOne());
                System.out.println(selector.choose(2));
            }

输出：

```
SimpleCandidate{weight=3, id='service3'}
[SimpleCandidate{weight=3, id='service3'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=2, id='service2'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=3, id='service3'}
[SimpleCandidate{weight=1, id='service1'}, SimpleCandidate{weight=4, id='service4'}]
SimpleCandidate{weight=3, id='service3'}
[SimpleCandidate{weight=3, id='service3'}, SimpleCandidate{weight=4, id='service4'}]
SimpleCandidate{weight=1, id='service1'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=3, id='service3'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=3, id='service3'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=3, id='service3'}, SimpleCandidate{weight=4, id='service4'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=3, id='service3'}]
SimpleCandidate{weight=3, id='service3'}
[SimpleCandidate{weight=3, id='service3'}, SimpleCandidate{weight=4, id='service4'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=3, id='service3'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=3, id='service3'}, SimpleCandidate{weight=4, id='service4'}]
SimpleCandidate{weight=2, id='service2'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=3, id='service3'}]
SimpleCandidate{weight=3, id='service3'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=3, id='service3'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=3, id='service3'}
[SimpleCandidate{weight=4, id='service4'}, SimpleCandidate{weight=3, id='service3'}]
SimpleCandidate{weight=4, id='service4'}
[SimpleCandidate{weight=1, id='service1'}, SimpleCandidate{weight=2, id='service2'}]
SimpleCandidate{weight=1, id='service1'}
[SimpleCandidate{weight=2, id='service2'}, SimpleCandidate{weight=4, id='service4'}]
SimpleCandidate{weight=1, id='service1'}
[SimpleCandidate{weight=2, id='service2'}, SimpleCandidate{weight=3, id='service3'}]

```


 # 典型的适用场景
 1. 多个服务商，按照分流比例选取
 2. 负载均衡按比例路由到对应节点
 3. ABTest随机走不同流程
 4. ...
 
 # 更多特性
 
 1. 线程安全
 2. 支持动态修改权重（例如权重从数据库获取，每次获取可能都不一样）
 3. 支持动态增减元素，例如动态上线新的服务商时候可动态添加一个新的服务商进集合中，下线一个服务商时动态删除该元素。
 4. 支持自定义选取算法，默认采用加权随机算法，用户可自定义其实现算法，如A B C D四个候选实体比例是4:3:2:1，默认每次选择的时候第一个元素都是40%的概率，用户可自定义算法来实现前4次都是A元素，然后再三次B元素然后再2次C元素以此类推。
 
 
 更多特性的使用参看：`AdvancedSample`中的使用示例。 
 
 
 
 
 # Developer Doc
 
 ## ProportionalSelector
 
 API入口的类，其中有两个接口。`selectOne`和 `select`，后者可以传入数量，用以返回多个元素。
 
 ## ICandidate
 
 候选实体的定义接口（Interface），把需要挑选的实体类需要实现此接口，用以定义其ID和权重。其中权重将用于决定被挑选的概率。例如四个实体：权重分别是1、2、3、4，即每次被挑选的时候他们的概率分别是：10%，20%，30%，40%。
 
 如果仅简单使用，可用内置提供的`SimpleCandidate`对象
 
 
 ##  SelectStrategy
 具体挑选的策略算法，默认采用加权随机算法。如果需要自定义算法，还是使用`ProportionalSelector`中的`setSelectStrategy` 方法修改
 
 
 
 
 ## 测试概率是否符合预期
 
 查看 `TestCase.java`中的试验代码，以下是一次的实验输出的数据

```
Examine case : TOTAL_TEST_CNT : 50000 testCandidates : [MyCandidate{name='id1', weight=1, otherFields='other1'}, MyCandidate{name='id2', weight=2, otherFields='other2'}, MyCandidate{name='id3', weight=3, otherFields='other3'}, MyCandidate{name='id4', weight=4, otherFields='other4'}]
====test select one ======= 
expect chosen probability of id1:0.1
expect chosen probability of id2:0.2
expect chosen probability of id3:0.3
expect chosen probability of id4:0.4
actual chosen probability of id1:0.09872
actual chosen probability of id2:0.19842
actual chosen probability of id3:0.29914
actual chosen probability of id4:0.40372
====test select three from four=======
expect123 = 0.009523809523809523, actual123 = 0.00992
expect124 = 0.012698412698412698, actual124 = 0.01236
expect234 = 0.06000000000000001, actual234 = 0.05972
expect432 = 0.13333333333333333, actual432 = 0.13338
``` 
 
 

 
 
 

package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sang on 2018/3/27.
 */
public class CollectionUtil {

    public static List<String> complementA(List a, List b){
        List c =  new ArrayList();
        c.addAll(a);
        c.removeAll(b);
        return c;
    }

    public static void main(String[] args) {
         List a = Arrays.asList("sunny","脆饼大侠","我醉白","Princeyou","-_-","SG丶一刀","小公子",
                 "初见","夺命失心散","拖鞋党","我是你dad","上善若水","一只窜天猴","幸氏家族H","SE7VEN","100%",
                 "啊厉害","阴险小姐","一世心上人","宝","黑白境界","眷恋的配角","清风光耀","青青子衿",
                 "射爆你的蛋","三刀流","破阵丶贝勒爷","左手","老妇女");
         List b = Arrays.asList( "阿威","莫要念自醉自演","sunny" ,"训训","张用","KANG","。","青青子衿","破阵丶贝勒爷","左手","陈辉","我醉白","得得"
                 ,"L","100%","芝","眷恋的配角","念与北诗","黑白境界","阴险小姐","老妇女","我是你dad","宝","Princeyou","拖鞋党");
         List c =  CollectionUtil.complementA(a,b);
         c.stream().forEach(s->System.out.print(s+"，"));
        // System.out.println(c.size()+"----------");
        // CollectionUtil.complementA(b,a).stream().forEach(s->System.out.println(s));;
    }

}

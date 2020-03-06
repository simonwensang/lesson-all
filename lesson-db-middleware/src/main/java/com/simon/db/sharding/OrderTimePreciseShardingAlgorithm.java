package com.simon.db.sharding;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by sang on 2019/12/3.
 */
public class OrderTimePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    Date[] dateRanges = new Date[2];

    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019,1,1,0,0,0);
        dateRanges[0]=calendar.getTime();
        calendar.set(2020,1,1,0,0,0);
        dateRanges[1]=calendar.getTime();
    }


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        Date d = preciseShardingValue.getValue();
        Iterator <String> its = collection.iterator();
        String target = null;
        //for next 1:1 2:2
        for(Date s : dateRanges ){
            target = its.next();
            if(d.before(s)){
                break;
            }
        }
        return target;
    }
}

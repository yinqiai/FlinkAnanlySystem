package com.youfan.sink;

import com.youfan.entity.ChannelInfo;
import com.youfan.entity.LiuLiangInfo;
import com.youfan.utils.ClickHouseUtils;
import com.youfan.utils.DateUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2020/2/19.
 */
public class ChannelinfoSink implements SinkFunction<ChannelInfo> {

    @Override
    public void invoke(ChannelInfo value, Context context) throws Exception {
        String timeinfo = value.getTimeinfo();
        String deviceType = value.getDeviceType();
        String channelinfoString = value.getChannelinfo();
        long times = value.getTimes();
        long newusers = value.getNewusers();
        long hourActivenums = value.getHourActivenums();
        long dayActivenums = value.getDayActivenums();
        long monthActivenums = value.getMonthActivenums();
        long weekActivenums = value.getWeekActivenums();
        long userNums = value.getUserNums();
        Map<String,String> dataMap = new HashMap<String,String>();
        String id = System.currentTimeMillis()+deviceType;
        String timeinfonew = timeinfo.substring(0,8);
        String newFormateDate = DateUtils.transferFormat(timeinfonew,"yyyyMMdd","yyyy-MM-dd");
        dataMap.put("timeinfo",newFormateDate);
        dataMap.put("id",id);
        dataMap.put("timeinfoString",timeinfo);
        dataMap.put("deviceType",deviceType);
        dataMap.put("channelinfo",channelinfoString);
        dataMap.put("times",times+"");
        dataMap.put("newusers",newusers+"");
        dataMap.put("hourActivenums",hourActivenums+"");
        dataMap.put("dayActivenums",dayActivenums+"");
        dataMap.put("monthActivenums",monthActivenums+"");
        dataMap.put("weekActivenums",weekActivenums+"");
        dataMap.put("userNums",userNums+"");
        Set<String> intFieldSet = new HashSet<String>();
        intFieldSet.add("id");
        intFieldSet.add("times");
        intFieldSet.add("newusers");
        intFieldSet.add("hourActivenums");
        intFieldSet.add("dayActivenums");
        intFieldSet.add("monthActivenums");
        intFieldSet.add("weekActivenums");
        intFieldSet.add("userNums");
        ClickHouseUtils.insertData("ChannelInfo",dataMap,intFieldSet);
    }
}

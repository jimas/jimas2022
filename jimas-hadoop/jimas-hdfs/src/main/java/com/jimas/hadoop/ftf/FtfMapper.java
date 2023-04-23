package com.jimas.hadoop.ftf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * @author liuqj
 */
public class FtfMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Text reduceKey = new Text();
    IntWritable reduceVal = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //value:秦羽,侯费,澜叔,小黑
        String[] split = StringUtils.split(value.toString(), ',');

        for (int i = 1; i < split.length; i++) {
            //直接关系
            reduceKey.set(getUniqueKey(split[0], split[i]));
            reduceVal.set(0);
            context.write(reduceKey,reduceVal);
            for (int j = i + 1; j < split.length; j++) {
                //间接关系
                reduceKey.set(getUniqueKey(split[i], split[j]));
                reduceVal.set(1);
                context.write(reduceKey,reduceVal);
            }
        }

    }

    public static String getUniqueKey(String key1, String key2) {

        if (key1.compareTo(key2) > 0) {
            return key1 + "@" + key2;
        }
        return key2 + "@" + key1;
    }
}

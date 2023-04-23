package com.jimas.hadoop.ftf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liuqj
 */
public class FtfReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();


    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //key:秦羽@侯费,value :0
        int sum = 0;
        boolean flag = true;
        for (IntWritable value : values) {
            if (value.get() == 0) {
                flag = false;
            }
            sum += value.get();
        }
        if (flag) {
            result.set(sum);
            context.write(key, result);
        }

    }
}

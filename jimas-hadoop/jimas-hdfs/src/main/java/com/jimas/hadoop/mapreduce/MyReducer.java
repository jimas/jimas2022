package com.jimas.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.ReduceTask;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @see ReduceTask#run(org.apache.hadoop.mapred.JobConf, org.apache.hadoop.mapred.TaskUmbilicalProtocol)
 * @author liuqj
 */
public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();

    //相同的key为一组 ，这一组数据调用一次reduce
    //1 1
    //2 1
    //3 1
    //hello 100000
    //hadoop 100000
    //比如 某次reduce 其中 key 为 hello  values 为 100000个1

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
//        System.out.println(key);
//        System.out.println(values);
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        result.set(sum);
        context.write(key, result);
    }
}

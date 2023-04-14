package com.jimas.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapTask;
import org.apache.hadoop.mapred.TaskUmbilicalProtocol;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @see MapTask#run(JobConf, TaskUmbilicalProtocol)
 * @author liuqj
 */
public class MyMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    //value 为每一行数据 比如  hello hadoop 1

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
//        System.out.println(value);
        StringTokenizer it = new StringTokenizer(value.toString());
        while (it.hasMoreTokens()) {
            word.set(it.nextToken());
            context.write(word, one);
        }
    }
}

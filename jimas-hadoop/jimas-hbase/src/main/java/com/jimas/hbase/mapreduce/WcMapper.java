package com.jimas.hbase.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author liuqj
 */
public class WcMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    Text text = new Text();
    IntWritable val = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        String[] splits = value.toString().split(" ");
        for (String str : splits) {
            text.set(str);
            context.write(text, val);
        }
    }
}

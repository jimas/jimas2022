package com.jimas.hadoop.topn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author liuqj
 */
public class TPartitioner extends Partitioner<TKey, IntWritable> {

    @Override
    public int getPartition(TKey tKey, IntWritable intWritable, int numPartitions) {
        return tKey.getYear() % numPartitions;
    }
}

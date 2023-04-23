package com.jimas.hadoop.topn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * /data/topn/input/data.txt /data/topn/output
 *
 * @author liuqj
 */
public class MyTopNClient {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration(true);
        //不提交到yarn  指定本地
        conf.set("mapreduce.framework.name", "local");
        //异构平台执行 windows
        conf.set("mapreduce.app-submission.cross-platform", "true");

        //params
        GenericOptionsParser parser = new GenericOptionsParser(conf, args);
        String[] others = parser.getRemainingArgs();
        //job info
        Job job = Job.getInstance(conf);
        job.addCacheFile(new Path("/data/dict/dict.txt").toUri());
        job.setJarByClass(MyTopNClient.class);
        job.setJobName("myTopN");
        //region map 阶段
        //key 自定义key 需要实现 序列化、反序列化、sort
        job.setMapOutputKeyClass(TKey.class);
        job.setMapOutputValueClass(IntWritable.class);
        //分区器
        job.setPartitionerClass(TPartitioner.class);
        //排序比较器
        job.setSortComparatorClass(TSortComparator.class);
        job.setMapperClass(TMapper.class);
        //input
        TextInputFormat.addInputPath(job, new Path(others[0]));
        Path outPath = new Path(others[1]);
        if (outPath.getFileSystem(conf).exists(outPath)) {
            outPath.getFileSystem(conf).delete(outPath, true);
        }
        //output
        TextOutputFormat.setOutputPath(job, outPath);
        //endregion
        //region reduce 阶段

        job.setReducerClass(TReducer.class);
        //分组比较器
        job.setGroupingComparatorClass(TGroupingComparator.class);
        //endregion

        job.waitForCompletion(true);
    }
}

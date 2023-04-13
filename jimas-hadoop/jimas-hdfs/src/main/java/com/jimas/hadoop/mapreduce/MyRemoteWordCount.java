package com.jimas.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * 需要 package 打出jar 上传到 装好 hadoop 环境的 机器上
 * hadoop jar jimas-hdfs-1.0-SNAPSHOT.jar com.jimas.hadoop.mapreduce.MyRemoteWordCount inputpath outputpath
 * @author liuqj
 */
public class MyRemoteWordCount {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration(true);
        GenericOptionsParser parser = new GenericOptionsParser(conf, args);
        String[] remainingArgs = parser.getRemainingArgs();
        Job job = Job.getInstance(conf);
        job.setJobName("MyRemoteWordCount");
        job.setJarByClass(MyRemoteWordCount.class);
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(MyReducer.class);
        job.setNumReduceTasks(2);
        Path inPath = new Path(remainingArgs[0]);
        Path outPath = new Path(remainingArgs[1]);
        if (outPath.getFileSystem(conf).exists(outPath)) {
            outPath.getFileSystem(conf).delete(outPath, true);
        }
        TextInputFormat.addInputPath(job, inPath);
        TextOutputFormat.setOutputPath(job, outPath);
        job.waitForCompletion(true);

    }
}

package com.jimas.hadoop.ftf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * @author liuqj
 */
public class MyFriendToFriendClient {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration(true);
        GenericOptionsParser parser = new GenericOptionsParser(conf, args);
        //不提交到yarn  指定本地
        conf.set("mapreduce.framework.name", "local");
        //异构平台执行 windows
        conf.set("mapreduce.app-submission.cross-platform", "true");


        String[] remainingArgs = parser.getRemainingArgs();


        Job job = Job.getInstance(conf);

        TextInputFormat.addInputPath(job, new Path(remainingArgs[0]));
        Path outPath = new Path(remainingArgs[1]);
        if (outPath.getFileSystem(conf).exists(outPath)) {
            outPath.getFileSystem(conf).delete(outPath, true);
        }
        TextOutputFormat.setOutputPath(job, outPath);

        job.setJarByClass(MyFriendToFriendClient.class);
        job.setJobName("myFtf");
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setMapperClass(FtfMapper.class);

        job.setReducerClass(FtfReducer.class);
        //job.setNumReduceTasks(0); //通过控制 reduce 数量为0 可以仅校验map 产生的结果  part-m-00000
        job.waitForCompletion(true);
    }
}

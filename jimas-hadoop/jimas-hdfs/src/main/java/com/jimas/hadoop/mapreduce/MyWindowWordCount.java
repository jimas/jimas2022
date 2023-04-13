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
 * 需要 package 打出jar
 * windows 环境下 需要装 hadoop 环境
 * 编辑 Configuration 添加 program arguments 如下：
 *  -Dmapreduce.job.reduces=2  /user/hadoop/data.txt  /data/windows/result
 * 直接 main 方法执行
 * @author liuqj
 */
public class MyWindowWordCount {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration(true);
        //让框架知道是windows异构平台运行
        conf.set("mapreduce.app-submission.cross-platform","true");
        GenericOptionsParser parser = new GenericOptionsParser(conf, args);
        String[] remainingArgs = parser.getRemainingArgs();
        Job job = Job.getInstance(conf);
        //windows 环境下 必须设置
        job.setJar("D:\\IdeaProjects\\jimas2022\\jimas-hadoop\\jimas-hdfs\\target\\jimas-hdfs-1.0-SNAPSHOT.jar");
        job.setJobName("MyWindowWordCount");
        job.setJarByClass(MyWindowWordCount.class);
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(MyReducer.class);
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

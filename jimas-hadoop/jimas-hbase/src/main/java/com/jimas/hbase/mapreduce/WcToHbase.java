package com.jimas.hbase.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * word count：输入参数来源与 hdfs, 结果存入hbase.
 * 这里引用了第三方hbase相关的jar，默认情况下 Hadoop集群中没有相关jar
 * 非local 模式运行时，需把相关依赖jar 用shade打包到一起
 *
 * @author liuqj
 */
public class WcToHbase {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration(true);
        //指定本地模式，不会在yarn 上留下痕迹
        configuration.set("mapreduce.framework.name", "local");
        //让框架知道是windows异构平台运行
        configuration.set("mapreduce.app-submission.cross-platform", "true");
        configuration.set("hbase.zookeeper.quorum", "node01,node02,node03");
        GenericOptionsParser parser = new GenericOptionsParser(args);
        String[] remainingArgs = parser.getRemainingArgs();
        //创建job 对象
        Job job = Job.getInstance(configuration);
        //windows 执行交给yarn时 需配置 jar 路径，并移除本地模式,增加windows异构平台运行配置
//        job.setJar("C:\\Users\\liuqj\\IdeaProjects\\jimas2022\\jimas-hadoop\\jimas-hbase\\target\\jimas-hbase-1.0-SNAPSHOT.jar");
        job.setJobName("WcToHbase");
        job.setJarByClass(WcToHbase.class);

        //创建mapper类
        job.setMapperClass(WcMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //指定hdfs 的参数文件目录
        FileInputFormat.addInputPath(job, new Path("/user/wc.txt"));

        //创建reduce 类，提前创建 hbase 表 wc
        TableMapReduceUtil.initTableReducerJob("wc", WCReducer.class, job, null, null, null, null, false);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Put.class);
        job.waitForCompletion(true);

    }
}

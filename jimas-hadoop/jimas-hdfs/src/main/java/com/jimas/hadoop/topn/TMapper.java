package com.jimas.hadoop.topn;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author liuqj
 */
public class TMapper extends Mapper<LongWritable, Text, TKey, IntWritable> {
    TKey tKey = new TKey();
    IntWritable tVal = new IntWritable();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    HashMap<String, String> dictMap = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] files = context.getCacheFiles();
        Path path = new Path(files[0].getPath());
        try (FSDataInputStream open = path.getFileSystem(context.getConfiguration()).open(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(open))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\t");
                dictMap.put(split[0], split[1]);
            }
        }
    }

    //region key:该行面向文件的字节pos,value: 该行数据
    //endregion

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //2023-9-12 22:22:22    1   32
        String[] split = value.toString().split("\t");
        try {
            Date date = sdf.parse(split[0]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            tKey.setYear(calendar.get(Calendar.YEAR));
            tKey.setMonth(calendar.get(Calendar.MONTH) + 1);
            tKey.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            int wd = Integer.parseInt(split[2]);
            tKey.setWd(wd);
            tKey.setLocation(dictMap.get(split[1]));
            tVal.set(wd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        context.write(tKey, tVal);
    }
}

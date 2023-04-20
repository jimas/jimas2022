package com.jimas.hadoop.topn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liuqj
 */
public class TMapper extends Mapper<LongWritable, Text, TKey, IntWritable> {
    TKey tKey = new TKey();
    IntWritable tVal = new IntWritable();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            tVal.set(wd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        context.write(tKey, tVal);
    }
}

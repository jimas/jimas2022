package com.jimas.hadoop.topn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author liuqj
 */
public class TReducer extends Reducer<TKey, IntWritable, Text, IntWritable> {
    Text outputKey = new Text();
    IntWritable outputVal = new IntWritable();

    @Override
    protected void reduce(TKey key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //region key: map 中 输出的分组key
        //endregion
        boolean first = true;
        String firstKey = "";
        Iterator<IntWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            //next 会对 key value 都会更新，底层调用的 nextKeyValue
            IntWritable next = iterator.next();
            String currentKey = key.getYear() + "-" + key.getMonth() + "-" + key.getDay();
            if (first) {
                first = false;
                firstKey = currentKey;
                outputKey.set(currentKey);
                outputVal.set(next.get());
                context.write(outputKey, outputVal);
            } else if (!Objects.equals(firstKey, currentKey)) {
                outputKey.set(currentKey);
                outputVal.set(next.get());
                context.write(outputKey, outputVal);
                break;
            }
        }
    }
}

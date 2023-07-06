package com.jimas.hbase;

import org.apache.commons.lang3.RandomUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 通话记录表
 * <br>rowkey:主叫+"_"+Long.Max-通话开始时间的时间戳</br>
 * <br>被叫：dest:</br>
 * <br>时长：duration:</br>
 * <br>类型:type</br>
 * <br>通话开始时间:callTime</br>
 *
 * @author liuqj
 */
public class HbaseFilter {
    Connection connection = null;
    Admin admin = null;
    Table table = null;

    @Before
    public void init() throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "node01:2181,node02:2181,node03:2181");
        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();
        table = connection.getTable(TableName.valueOf("phone"));


    }

    @Test
    public void queryByFilter() throws Exception {
        Scan scan = new Scan();
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        PrefixFilter filter = new PrefixFilter(Bytes.toBytes("17601138404_"));
        SingleColumnValueFilter valueFilter = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("type"), CompareOperator.EQUAL, Bytes.toBytes("1"));
        filterList.addFilter(filter);
        filterList.addFilter(valueFilter);
        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.print(Bytes.toString(CellUtil.cloneRow(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("dest")))));
            System.out.print("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("dest")))));
            System.out.print("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("length")))));
            System.out.print("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("type")))));
            System.out.println("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("callTime")))));
        }
    }

    @Test
    public void queryByScan() throws Exception {
        Scan scan = new Scan();
        //start：减大值，stop：减小值
        scan.withStartRow(Bytes.toBytes("17601138404_" + (Long.MAX_VALUE - sdf.parse("20220228000000").getTime())));
        scan.withStopRow(Bytes.toBytes("17601138404_" + (Long.MAX_VALUE - sdf.parse("20220212000000").getTime())));
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.print(Bytes.toString(CellUtil.cloneRow(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("dest")))));
            System.out.print("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("dest")))));
            System.out.print("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("length")))));
            System.out.print("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("type")))));
            System.out.println("--" + Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("callTime")))));
        }
    }

    @Test
    public void insertDemoData() throws Exception {
        List<Put> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String tellA = getRandomNum("176");
            for (int j = 0; j < 1000; j++) {
                String dest = getRandomNum("158");
                String length = String.valueOf(RandomUtils.nextInt(10, 100));
                String type = String.valueOf(RandomUtils.nextInt(0, 2));
                String callTime = getTime("2022");
                Put put = new Put(Bytes.toBytes(tellA + "_" + (Long.MAX_VALUE - sdf.parse(callTime).getTime())));
                put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("dest"), Bytes.toBytes(dest));
                put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("length"), Bytes.toBytes(length));
                put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("type"), Bytes.toBytes(type));
                put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("callTime"), Bytes.toBytes(callTime));
                list.add(put);
            }
        }
        table.put(list);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private String getTime(String time) {
        return time + String.format("%02d", RandomUtils.nextInt(1, 12)) + String.format("%02d", RandomUtils.nextInt(1, 28))
                + String.format("%02d", RandomUtils.nextInt(1, 24)) + String.format("%02d", RandomUtils.nextInt(1, 60))
                + String.format("%02d", RandomUtils.nextInt(1, 60));
    }

    /**
     * 不足时 用0 补齐
     *
     * @param prefix
     * @return
     */
    private String getRandomNum(String prefix) {

        return prefix + String.format("%08d", RandomUtils.nextInt(1, 99999999));
    }

    @After
    public void close() {
        try {
            table.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            admin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

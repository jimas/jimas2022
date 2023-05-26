package com.jimas.hbase;

import org.apache.commons.lang3.RandomUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqj
 */
public class HbaseDemo {
    Connection connection = null;
    Admin admin = null;

    @Before
    public void init() throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "node01:2181,node02:2181,node03:2181");
        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();
    }

    @Test
    public void createTable() throws IOException {
        TableName tableName = TableName.valueOf("phone");
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(tableName)
                .setColumnFamily(ColumnFamilyDescriptorBuilder.of(Bytes.toBytes("cf")))
                .build();

        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
        admin.createTable(tableDescriptor);
    }

    @Test
    public void truncateTable() throws IOException {
        TableName tableName = TableName.valueOf("psn");
        admin.disableTable(tableName);
        admin.truncateTable(tableName, true);
    }

    @Test
    public void getByRowKey() throws IOException {
        Table table = connection.getTable(TableName.valueOf("psn"));
        Result result = table.get(new Get(Bytes.toBytes("11")));
        Cell cell = result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("name"));
        System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
        System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
        System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("name"))));
        table.close();
    }



    @Test
    public void put() throws IOException {
        String[] sexs = {"man", "woman"};
        List<Put> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Put put = new Put(Bytes.toBytes(String.valueOf(i)));
            put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("age"), Bytes.toBytes(String.valueOf(RandomUtils.nextInt(10, 50))));
            put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("sex"), Bytes.toBytes(String.valueOf(sexs[RandomUtils.nextInt(0, 1)])));
            put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"), Bytes.toBytes("lisi" + i));
            list.add(put);
        }
    }

    @After
    public void close() {
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

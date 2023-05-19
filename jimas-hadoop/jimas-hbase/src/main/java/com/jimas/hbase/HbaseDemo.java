package com.jimas.hbase;

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

/**
 * @author liuqj
 */
public class HbaseDemo {
    Connection connection = null;
    Admin admin = null;
    Table table = null;

    @Before
    public void init() throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "node03:2181,node04:2181,node05:2181");
        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();
        table = connection.getTable(TableName.valueOf("psn"));


    }

    @Test
    public void createTable() throws IOException {
        TableName tableName = TableName.valueOf("psn2");
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
    public void getByRowKey() throws IOException {
        Result result = table.get(new Get(Bytes.toBytes("11")));
        Cell cell = result.getColumnLatestCell(Bytes.toBytes("cf"), Bytes.toBytes("name"));
        System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
        System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
        System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("name"))));

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

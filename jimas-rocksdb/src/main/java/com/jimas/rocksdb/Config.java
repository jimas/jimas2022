package com.jimas.rocksdb;

import com.alibaba.fastjson.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rocksdb.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author liuqj
 */
public class Config {
    public static class Info {
        private Integer val;
        private String code;

        public Info(Integer val, String code) {
            this.val = val;
            this.code = code;
        }

        public Integer getVal() {
            return val;
        }

        public void setVal(Integer val) {
            this.val = val;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    RocksDB db;

    @Before
    public void beforeInit() {
        try (Options options = new Options().setCreateIfMissing(true)) {
//            HashLinkedListMemTableConfig memTableConfig = new HashLinkedListMemTableConfig();
//            options.setMemTableConfig(memTableConfig);
//            options.setAllowConcurrentMemtableWrite(false);
//            options.setWriteBufferSize(1024 * 1024 * 1024);
            db = RocksDB.open(options, "D:\\opt\\db\\jimas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void afterInit() {
        db.close();
    }

    @Test
    public void batchWriteTest() throws RocksDBException {
        try (WriteOptions writeOpts = new WriteOptions().setSync(false); WriteBatch updates = new WriteBatch()) {
            for (int i = 0; i < 100; i++) {
                updates.put((i + "11").getBytes(StandardCharsets.UTF_8), JSON.toJSONBytes(new Info(i, "你好" + i)));
            }
            db.write(writeOpts, updates);
        }

    }

    @Test
    public void writeTest() throws RocksDBException {
        db.put("11".getBytes(StandardCharsets.UTF_8), JSON.toJSONBytes(new Info(11, "你好1")));
        db.put("12".getBytes(StandardCharsets.UTF_8), JSON.toJSONBytes(new Info(12, "你好2")));
        db.put("13".getBytes(StandardCharsets.UTF_8), JSON.toJSONBytes(new Info(13, "你好3")));
    }

    @Test
    public void readTest() throws RocksDBException {
        byte[] bytes = db.get("1011".getBytes(StandardCharsets.UTF_8));
        if (bytes != null) {
            Info info = JSON.parseObject(bytes, Info.class);
            System.out.println(JSON.toJSON(info));
        }

    }

    @Test
    public void multiGetTest() throws RocksDBException {
        List<byte[]> bytes = db.multiGetAsList(Arrays.asList(
                "1011".getBytes(StandardCharsets.UTF_8)
                , "1111".getBytes(StandardCharsets.UTF_8)
                , "ss".getBytes(StandardCharsets.UTF_8)));

        for (byte[] aByte : bytes) {
            if (aByte != null) {
                Info info = JSON.parseObject(aByte, Info.class);
                System.out.println(JSON.toJSON(info));
            }
        }

    }

}

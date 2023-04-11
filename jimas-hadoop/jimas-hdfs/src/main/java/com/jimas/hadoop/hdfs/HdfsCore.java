package com.jimas.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;

/**
 * @author liuqj
 */
public class HdfsCore {
    public Configuration conf = null;
    public FileSystem fs = null;

    @Before
    public void conn() throws Exception {
        conf = new Configuration(true);
        //环境变量 HADOOP_USER_NAME
//        fs = FileSystem.get(conf);
        fs = FileSystem.get(URI.create("hdfs://mycluster/"), conf, "hadoop");
    }

    @After
    public void close() throws IOException {
        fs.close();
    }

    /**
     * 创建目录
     *
     * @throws Exception
     */
    @Test
    public void mkdir() throws Exception {
        Path path = new Path("/user/hadoop");
        if (fs.exists(path)) {
            FsStatus status = fs.getStatus(path);
            System.out.println(status.getUsed() + ":" + status.getCapacity() + ":" + status.getRemaining());
        } else {
            fs.mkdirs(path);
        }
    }

    /**
     * 上传文件
     *
     * @throws Exception
     */
    @Test
    public void upload() throws Exception {
        InputStream input = new BufferedInputStream(new FileInputStream(new File("./data/hello.txt")));
        Path path = new Path("/user/hadoop/tt.log");
        FSDataOutputStream output = fs.create(path, true);
        IOUtils.copyBytes(input, output, conf, true);
    }

    /**
     * 上传文件
     * 需要在windows本地搭建Hadoop环境
     *
     * @throws IOException
     */
    @Test
    public void upload2() throws IOException {
        //本地目录
        Path src = new Path("/data/copy4.txt");
        //远程目录
        Path dest = new Path("/user/hadoop/data2.txt");
        fs.copyFromLocalFile(src, dest);
    }


    /**
     * 下载到本地
     * java.io.FileNotFoundException: HADOOP_HOME and hadoop.home.dir are unset
     * windows本地调试hadoop报错（我这里是连接Hbase也是报同样的错） 其原因是需要在windows本地搭建Hadoop环境
     *
     * @throws IOException
     */
    @Test
    public void downloadToLocal() throws IOException {
        //本地目录
        Path dest = new Path("/data/copy4.txt");
        //远程目录
        Path src = new Path("/user/hadoop/data.txt");
        fs.copyToLocalFile(src, dest);
    }

    /**
     * 追加文件内容
     *
     * @throws IOException
     */
    @Test
    public void append() throws IOException {
        Path path = new Path("/user/hadoop/tt.log");
        FSDataOutputStream output = fs.append(path, 1024);
        InputStream input = new BufferedInputStream(new FileInputStream(new File("./data/hello.txt")));
        IOUtils.copyBytes(input, output, conf, true);

    }

    @Test
    public void delete() throws IOException {
        boolean delete = fs.delete(new Path("/data"), true);
        System.out.println(delete);
    }

    /**
     * 获取所有文件
     *
     * @throws IOException
     */
    @Test
    public void listFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(new Path("/"), true);
        while (remoteIterator.hasNext()) {
            System.out.println(remoteIterator.next());
        }
    }

    /**
     * 获取当前账号 家目录
     */
    @Test
    public void homeDirectory() {
        Path directory = fs.getHomeDirectory();
        System.out.println(directory);
    }

    @Test
    public void blocks() throws IOException {
        Path file = new Path("/user/hadoop/data.txt");
        FileStatus fss = fs.getFileStatus(file);
        BlockLocation[] blockLocations = fs.getFileBlockLocations(fss, 0, fss.getLen());
        for (BlockLocation blockLocation : blockLocations) {
            System.out.println(blockLocation);
            //        0,        1048576,        node04,node02  A
            //        1048576,  540319,         node04,node03  B
            //计算向数据移动~！
            //其实用户和程序读取的是文件这个级别~！并不知道有块的概念~！
            //面向文件打开的输入流 无论怎么读取都是从文件开始读取！
            FSDataInputStream in = fs.open(file);
            //        in.seek(1048576);
            in.seek(blockLocation.getOffset());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
            System.out.println((char) in.readByte());
        }
    }
}

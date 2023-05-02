package com.jimas.hive.udf;


import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * <ul>一、临时udf 函数，客户端关闭后消失
 * <li>1、mvn clean package，将jar 上传到 hive 用户家目录</li>
 * <li>2、hive</li>
 * <li>3、add jar jimas-hive-1.0-SNAPSHOT.jar</li>
 * <li>4、create temporary function myUdf as 'com.jimas.hive.udf.MyUdf';</li>
 * <li>5、select myUdf(name) from psn;</li>
 *</ul>
 * 二、永久UDF函数（需将jar 上传到hdfs）
 * <li>1、打包后 将jar 上传到 hdfs</li>
 * <li>hdfs dfs -put jimas-hive-1.0-SNAPSHOT.jar /data/jar</li>
 * <li>2、 create function myUdf as 'com.jimas.hive.udf.MyUdf' using jar "hdfs://mycluster/data/jar/jimas-hive-1.0-SNAPSHOT.jar";</li>
 * <li>3、select myUdf(name) from psn;</li>
 * @author liuqj
 */
public class MyUdf extends UDF {
    public Text evaluate(Text text) {
        if (text == null) {
            return null;
        }
        return new Text("iii" + text);
    }
}

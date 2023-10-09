## clickhouse 

### install

- 下载rpm 包
```shell

wget --content-disposition https://packagecloud.io/Altinity/clickhouse/packages/el/7/clickhouse-common-static-20.8.3.18-1.el7.x86_64.rpm/download.rpm
wget --content-disposition https://packagecloud.io/Altinity/clickhouse/packages/el/7/clickhouse-server-common-20.8.3.18-1.el7.x86_64.rpm/download.rpm
wget --content-disposition https://packagecloud.io/Altinity/clickhouse/packages/el/7/clickhouse-server-20.8.3.18-1.el7.x86_64.rpm/download.rpm
wget --content-disposition https://packagecloud.io/Altinity/clickhouse/packages/el/7/clickhouse-client-20.8.3.18-1.el7.x86_64.rpm/download.rpm

```

- 执行rpm安装
```shell
rpm -ivh ./clickhouse-*.rpm
```
#### 分布式安装

- 每台机器先单独安装clickhouse 之后在 /etc/目录下添加以下文件
> vim /etc/metrika.xml
```xml
<yandex>
    <clickhouse_remote_servers>
        <!--clickhouse_cluster_3shards_1replicas 为集群名称-->
        <clickhouse_cluster_3shards_1replicas>
            <shard>
                <internal_replication>true</internal_replication>
                <replica>
                    <host>node02</host>
                    <port>9000</port>
                </replica>
            </shard>
            <shard>
                <replica>
                    <internal_replication>true</internal_replication>
                    <host>node03</host>
                    <port>9000</port>
                </replica>
            </shard>
            <shard>
                <internal_replication>true</internal_replication>
                <replica>
                    <host>node06</host>
                    <port>9000</port>
                </replica>
            </shard>
        </clickhouse_cluster_3shards_1replicas>
    </clickhouse_remote_servers>
     <!-- 必须先安装zk-->
    <zookeeper-servers>
        <node index="1">
            <host>node02</host>
            <port>2181</port>
        </node>
        <node index="2">
            <host>node03</host>
            <port>2181</port>
        </node>
        <node index="3">
            <host>node06</host>
            <port>2181</port>
        </node>
    </zookeeper-servers>
    <!-- macros 区分每台clickhouse节点的宏配置，每台clickhouse需要配置不同名称 比如：01 02 03 -->
    <macros>
        <replica>01</replica>
    </macros>
    <networks>
        <ip>::/0</ip>
    </networks>
    <clickhouse_compression>
        <case>
            <min_part_size>10000000000</min_part_size>
            <min_part_size_ratio>0.01</min_part_size_ratio>
            <method>lz4</method>
        </case>
    </clickhouse_compression>
</yandex>

```

### 设置用户密码

- 修改用户文件
```shell
vim /etc/clickhouse-server/users.xml

```
- 修改内容
```xml
<users>
    <!-- 默认用户名为 default,如果要用户名为jimas 则修改标签为<jimas></jimas>-->
    <default>
        <!--  密码标签可为 password或者password_sha256_hex 等 其中一个 -->
        <!--    echo -n 123456789 | openssl dgst -sha256    # 将 123456789 密码转为sha256 -->
        <password_sha256_hex>15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225</password_sha256_hex>
        <!--  明文密码      -->
        <!-- <password>123456789</password> -->
    </default>
</users>

```
- 重启服务

```shell
sytemctl restart clickhouse-server
```
- 客户端连接
```shell
clickhouse-client -u default --password 123456789
```
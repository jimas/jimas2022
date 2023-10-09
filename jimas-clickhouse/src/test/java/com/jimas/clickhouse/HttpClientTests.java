package com.jimas.clickhouse;

import com.clickhouse.client.*;
import com.clickhouse.data.ClickHouseDataStreamFactory;
import com.clickhouse.data.ClickHouseFormat;
import com.clickhouse.data.ClickHousePipedOutputStream;
import com.clickhouse.data.ClickHouseRecord;
import com.clickhouse.data.format.BinaryStreamUtils;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author liuqj
 */
public class HttpClientTests {

    @Test
    public void testClickHouse() throws Exception {
        String tableName = "t_jimas_test";
        ClickHouseNode houseNode = ClickHouseNode.builder()
                .host("10.89.104.144")
                .port(ClickHouseProtocol.HTTP, 8123)
                .database("default")
                .build();
        dropAndCreateTable(houseNode, tableName);
        insert(houseNode, tableName);
        query(houseNode, tableName);
    }

    private static void dropAndCreateTable(ClickHouseNode houseNode, String tableName) throws ClickHouseException {
        try (ClickHouseClient client = ClickHouseClient.newInstance(houseNode.getProtocol())) {
            ClickHouseRequest<?> request = client.read(houseNode);
            request.query("drop table if exists " + tableName).execute().get();
            request.query("create table " + tableName + "(a String,b Nullable(String)) engine=MergeTree() order by a").execute()
                    .get();
        } catch (Exception e) {
            throw ClickHouseException.forCancellation(e, houseNode);
        }
    }

    private static Long insert(ClickHouseNode houseNode, String tableName) throws ClickHouseException {
        try (ClickHouseClient client = ClickHouseClient.newInstance(houseNode.getProtocol())) {
            ClickHouseRequest.Mutation mutation = client.read(houseNode).write().table(tableName)
                    .format(ClickHouseFormat.RowBinary);
            CompletableFuture<ClickHouseResponse> future;
            ClickHouseConfig config = mutation.getConfig();
            try (ClickHousePipedOutputStream stream = ClickHouseDataStreamFactory.getInstance().createPipedOutputStream(config, (Runnable) null)) {
                future = mutation.data(stream.getInputStream()).execute();
                for (int i = 0; i < 100; i++) {
                    BinaryStreamUtils.writeString(stream, String.valueOf(i % 16));
                    BinaryStreamUtils.writeNonNull(stream);
                    BinaryStreamUtils.writeString(stream, UUID.randomUUID().toString());
                }
            }
            try (ClickHouseResponse response = future.get()) {
                return response.getSummary().getWrittenRows();
            }

        } catch (Exception e) {
            throw ClickHouseException.of(e, houseNode);
        }
    }

    private static void query(ClickHouseNode houseNode, String tableName) throws ClickHouseException {
        try (ClickHouseClient client = ClickHouseClient.newInstance(houseNode.getProtocol())) {
            ClickHouseResponse response = client.read(houseNode).format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                    .query("select * from " + tableName).execute().get();
            for (ClickHouseRecord record : response.records()) {
                System.out.println(record.getValue("a").asString());
                System.out.println(record.getValue("b").asString());
            }
        } catch (Exception e) {
            throw ClickHouseException.of(e, houseNode);
        }
    }

}

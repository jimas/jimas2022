package com.jimas.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerTest {
    public static final String BOOTSTRAP_SERVER = "192.168.37.128:9092,192.168.37.129:9092,192.168.37.130:9092";
    Properties props;

    @Before
    public void initConfig() {
        props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test02");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //offset earliest  latest none
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    @Test
    public void testConsumer01() throws InterruptedException {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("jimas02"));
//        for (Map.Entry<String, List<PartitionInfo>> listEntry : consumer.listTopics().entrySet()) {
//            String key = listEntry.getKey();
//            List<PartitionInfo> value = listEntry.getValue();
//            for (PartitionInfo partitionInfo : value) {
        // 需要先拉取一次
//                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
//                consumer.seek(topicPartition, 20000);
//            }
//        }
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value() + "==" + record.topic() + "==" + record.partition() + "==" + record.offset());
//                consumer.commitAsync();
            }
        }
    }
}

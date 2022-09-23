package com.jimas.kafka;

import org.apache.kafka.clients.producer.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;
import java.util.UUID;

/**
 * @author liuqj
 */
public class KafkaProducerTest {
    public static final String BOOTSTRAP_SERVER = "192.168.37.128:9092,192.168.37.129:9092,192.168.37.130:9092";
    Properties props;

    @Before
    public void initConfig() {
        props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        //ack 0 If set to zero then the producer will not wait for any acknowledgment from the server at all.
        props.put(ProducerConfig.ACKS_CONFIG, "-1");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    }

    @Test
    public void testProducer01() {
        Producer<String, String> kafkaProducer = new KafkaProducer<>(props);
        while (true) {
            // kafkaProducer.send(new ProducerRecord<String, String>("jimas02", null, UUID.randomUUID().toString()));
            //Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<String, String>("jimas02", null, UUID.randomUUID().toString()));
            kafkaProducer.send(new ProducerRecord<String, String>("jimas02", null, UUID.randomUUID().toString()), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println(recordMetadata.topic() + "==" + recordMetadata.partition() + "==" + recordMetadata.offset());
                }
            });
        }
    }
}

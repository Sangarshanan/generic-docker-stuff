package com.example;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class Example {

    public static void main(String[] args) throws Exception {

        final String SOURCE_BROKERS = "localhost:9092";

        // set up the streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        env.enableCheckpointing(600000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(300000);
        env.getCheckpointConfig().setCheckpointTimeout(120000);
        env.getCheckpointConfig().setTolerableCheckpointFailureNumber(10);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableUnalignedCheckpoints();
        env.getCheckpointConfig().setCheckpointStorage("file:///tmp/flink");

        String createSql =
                ("CREATE TABLE IF NOT EXISTS test_table ( "
                        + "user_id INT, "
                        + "name STRING, "
                        + "score INT "
                        + " ) "
                        + "WITH ( "
                        + "'connector' = 'kafka', "
                        + "'topic' = 'dbserver1.public.test', "
                        + "'properties.bootstrap.servers' = 'localhost:9092', "
                        + "'properties.group.id' = 'test-consumer-group-3', "
                        + "'value.format' = 'debezium-json', "
                        + "'scan.startup.mode' = 'latest-offset' "
                        + ")");

        tableEnv.executeSql(createSql);

        tableEnv.executeSql(
                "create table if not exists sink_table (user_id INT PRIMARY KEY, name STRING,score INT)"
                        + " WITH ( "
                        + "'connector' = 'upsert-kafka', "
                        + "'topic' = 'sink-test', "
                        + "'properties.bootstrap.servers' = 'localhost:9092', "
                        + "'key.format' = 'json', "
                        + "'value.format' = 'json' "
                        + ")");

        // create an output Table
        Table first_ten = tableEnv.sqlQuery("SELECT user_id,name,score from test_table");

        first_ten.executeInsert("sink_table").print();

        env.execute();
    }
}

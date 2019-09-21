/**
 * Project Name demo
 * File Name TestHbaseDao
 * Package Name com.huxiaosu.demo.hadoop
 * Create Time 2019/8/16
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.hadoop;

import com.huxiaosu.demo.hadoop.hbase.HBaseDao;
import com.huxiaosu.demo.hadoop.utils.Base64;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import scala.Tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author liujie
 * @ClassName TestHbaseDao
 * @date 2019/8/16 09:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHbaseDao implements Serializable {
    @Autowired
    private HBaseDao hBaseDao;
    private static final String tableName = "testliujie";
    private static final String rowKey = "111111";
    private static final String[] families = {"record"};

    @Test
    public void testCreateTable() {

        this.hBaseDao.createTable(tableName, families);
    }

    @Test
    public void testSaveData() {
        String[] columns = {"age", "name"};
        String[] values = {"11", "liujie"};
        this.hBaseDao.addRecords(tableName, rowKey, families[0], columns, values);
    }

    /**
     * 测试 sprakSql 查询 Hbase
     */
//    @Test
    public static void main(String[] arg) throws Exception {
        System.setProperty("spark.serializer", "org.apache.spark.serializer.KryoSerializer");

        SparkSession spark = SparkSession.builder()
                .appName("test-sprak-sql-hbase")
                .master("local[*]")
                .getOrCreate();

        JavaSparkContext context = new JavaSparkContext(spark.sparkContext());
        Scan scan = new Scan();
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "172.25.78.211");
        configuration.set("hbase.zookeeper.property.clientPort","2181");
        configuration.set(TableInputFormat.INPUT_TABLE, "testliujie");

        ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
        String ScanToString = Base64.encodeBytes(proto.toByteArray());
        configuration.set(TableInputFormat.SCAN, ScanToString);
        JavaPairRDD<ImmutableBytesWritable, Result> myRDD =
                context.newAPIHadoopRDD(configuration, TableInputFormat.class,
                        ImmutableBytesWritable.class, Result.class);
        JavaRDD<Row> personsRDD = myRDD.map(new Function<Tuple2<ImmutableBytesWritable, Result>, Row>() {
            @Override
            public Row call(Tuple2<ImmutableBytesWritable, Result> tuple) throws Exception {
                // TODO Auto-generated method stub
                Result result = tuple._2();
                String rowkey = Bytes.toString(result.getRow());
                System.out.println(rowkey);

                String messageId = Bytes.toString(result.getValue(Bytes.toBytes("record"), Bytes.toBytes("age")));
                String readStatus = Bytes.toString(result.getValue(Bytes.toBytes("record"), Bytes.toBytes("name")));
                //这一点可以直接转化为row类型
                return RowFactory.create(rowkey, messageId, readStatus);
            }
        });
        List<StructField> structFields = new ArrayList<StructField>();
        structFields.add(DataTypes.createStructField("rowkey", DataTypes.StringType, true));
        structFields.add(DataTypes.createStructField("age", DataTypes.StringType, true));
        structFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));

        StructType schema = DataTypes.createStructType(structFields);

        Dataset stuDf = spark.createDataFrame(personsRDD, schema);
        stuDf.printSchema();
        stuDf.createOrReplaceTempView("test");
        Dataset<Row> nameDf = spark.sql("select * from test");
        nameDf.show();
    }
}

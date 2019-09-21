/**
 * Project Name demo
 * File Name HBaseDao
 * Package Name com.huxiaosu.demo.hadoop.hbase
 * Create Time 2019/5/15
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.hadoop.hbase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huxiaosu.demo.hadoop.config.HBaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description
 * Hbase 操作
 *
 * @ClassName: HBaseDao
 * @author: liujie
 * @date: 2019/5/15 14:04
 */
@Slf4j
@Service
@Scope("singleton")
public class HBaseDao implements InitializingBean {

    @Autowired
    private HBaseConfig hBaseConfig;

    private Configuration conf = null;
    private Connection connection = null;
    private static Admin admin = null;

    @Override
    public void afterPropertiesSet() {
        try {
            this.getHConnection();
        } catch (Exception e) {
            log.error("error in creating hbase connection with conf:" + this.conf, e);
            throw new InstantiationError(e.getMessage());
        }
    }

    /**
     * 创建一个HConnection
     * HConnection connection = HConnectionManager.createConnection(conf);
     * HTableInterface table = connection.getTable("mytable");
     * table.get(...); ...
     * table.close();
     * connection.close();
     */
    protected synchronized void getHConnection() throws IOException {
        if (connection == null) {
            connection = ConnectionFactory.createConnection(hBaseConfig.getConfiguration());
        }
        admin = connection.getAdmin();
    }

    protected TableName getTableName(String tableName) {
        return TableName.valueOf(tableName);
    }

    public void createTable(String tableName, String[] columnFamily) {
        TableName name = TableName.valueOf(tableName);
        try {
            if (admin.tableExists(name)) {
                log.warn("table:" + tableName + " exists, no creating");
                throw new RuntimeException(tableName + "表已存在");
            } else {
                TableDescriptorBuilder tdb = TableDescriptorBuilder.newBuilder(name);
                for (String f : columnFamily) {
                    // 列族描述起构造器
                    ColumnFamilyDescriptorBuilder cdb = ColumnFamilyDescriptorBuilder
                            .newBuilder(Bytes.toBytes(f));
                    // 获得列描述起
                    ColumnFamilyDescriptor cfd = cdb.build();
                    // 添加列族
                    tdb.setColumnFamily(cfd);
                }
                // 获得表描述器
                TableDescriptor td = tdb.build();
                // 创建表
                admin.createTable(td);

            }
        } catch (Exception e) {
            log.error("error in creating table:{}", tableName, e);
            throw new RuntimeException(tableName + "表创建失败");
        }
    }

    public void createTable(String tableName, String columnFamily) {
        TableName name = TableName.valueOf(tableName);
        try {
            if (!admin.tableExists(name)) {
                TableDescriptorBuilder tdb = TableDescriptorBuilder.newBuilder(name);
                // 列族描述起构造器
                ColumnFamilyDescriptorBuilder cdb = ColumnFamilyDescriptorBuilder
                        .newBuilder(Bytes.toBytes(columnFamily));
                // 获得列描述起
                ColumnFamilyDescriptor cfd = cdb.build();
                // 添加列族
                tdb.setColumnFamily(cfd);
                // 获得表描述器
                TableDescriptor td = tdb.build();
                // 创建表
                admin.createTable(td);
            }
        } catch (Exception e) {
            log.error("error in creating table:{}", tableName, e);
            throw new RuntimeException(tableName + "表创建失败");
        }
    }

    public void addRecords(String tableName, String rowKey, String columnFamilys, String[] columns, String[] values) {
        tableExists(tableName);
        TableName name = TableName.valueOf(tableName);
        try {
            Table table = connection.getTable(name);
            Put put = new Put(Bytes.toBytes(rowKey));
            for (int i = 0; i < columns.length; i++) {
                put.addColumn(Bytes.toBytes(columnFamilys), Bytes.toBytes(columns[i]),
                        Bytes.toBytes(values[i]));
                table.put(put);
            }
        } catch (IOException e) {
            log.error("error in add Records, table:" + tableName + " row:" + rowKey
                    + " families:" + columnFamilys + " columns:" + columns.toString()
                    + " values:" + values.toString(), e);
            throw new RuntimeException(tableName + "插入数据失败");
        }

    }

    public void addRecord(String tableName, String rowKey, String columnFamily, String column, String value) {
        TableName name = TableName.valueOf(tableName);
        try {
            Table table = connection.getTable(name);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
        } catch (IOException e) {
            log.error("error in add Records, table:" + tableName + " row:" + rowKey
                    + " families:" + columnFamily + " columns:" + column
                    + " values:" + value, e);
            throw new RuntimeException(tableName + "插入数据失败");
        }
    }

    public void tableExists(String tableName) {
        TableName name = TableName.valueOf(tableName);
        try {
            boolean tableExists = admin.tableExists(name);
            if (!tableExists) {
                throw new RuntimeException(tableName + "表不存在");
            }
        } catch (IOException e) {
            log.error("error in query table exising:{} ", tableName, e);
            throw new RuntimeException(tableName + "表不存在");
        }
    }

    public void deleteTable(String tableName) {
        tableExists(tableName);
        TableName name = TableName.valueOf(tableName);
        try {
            admin.deleteTable(name);
        } catch (IOException e) {
            log.error("error in delete table :{} ", tableName, e);
            throw new RuntimeException("删除" + tableName + "表失败");
        }
    }

    public void deleteByRowKey(String tableName, String rowKey) {
        tableExists(tableName);
        TableName name = TableName.valueOf(tableName);
        try {
            Table table = connection.getTable(name);
            Delete d = new Delete(rowKey.getBytes());
            table.delete(d);
        } catch (IOException e) {
            log.error("error in deleting row:" + rowKey + " in table:" + tableName, e);
            throw new RuntimeException(tableName + "表根据 RowKey 删除数据失败");
        }

    }

    public void deleteColumnFamily(String tableName, String rowKey, String columnFamily) {
        tableExists(tableName);
        TableName name = TableName.valueOf(tableName);
        try {
            Table table = connection.getTable(name);
            Delete d = new Delete(rowKey.getBytes()).addFamily(Bytes.toBytes(columnFamily));
            table.delete(d);
        } catch (IOException e) {
            log.error("error in deleting row:" + rowKey + " in table:" + tableName + " family:"
                    + columnFamily, e);
            throw new RuntimeException(tableName + "表删除列组失败");
        }
    }

    public void deleteColumn(String tableName, String rowKey, String columnFamily, String column) {
        tableExists(tableName);
        TableName name = TableName.valueOf(tableName);
        Table table;
        try {
            table = connection.getTable(name);
            Delete d = new Delete(rowKey.getBytes()).addColumn(Bytes.toBytes(columnFamily),
                    Bytes.toBytes(column));
            table.delete(d);
        } catch (IOException e) {
            log.error("error in deleting column:" + column + " in row:" + rowKey + " table:"
                    + tableName + " family:"
                    + columnFamily, e);
            throw new RuntimeException(tableName + "表删除列失败");
        }

    }

    public JSONObject selectRow(String tableName, String rowKey) {
        tableExists(tableName);
        TableName name = TableName.valueOf(tableName);
        try {
            Table table = connection.getTable(name);
            Get g = new Get(rowKey.getBytes());
            Result rs = table.get(g);
            return this.buildRsToJSON(rs);
        } catch (IOException e) {
            log.error("error in getting rowKey:" + rowKey + " from table:" + tableName, e);
        }

        return new JSONObject();
    }

    public String getFirstRowKey(String tableName) {
        tableExists(tableName);
        TableName name = TableName.valueOf(tableName);
        try {
            Table table = connection.getTable(name);
            Scan scan = new Scan();
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> iterator = scanner.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Result rs = iterator.next();
                if (index == 0) {
                    scanner.close();
                    String row = Bytes.toString(rs.getRow());
                    return row;
                }
            }
        } catch (IOException e) {
            log.error("error in getting first row in:" + tableName, e);
        }
        return null;
    }

    public List<JSONObject> getPagedRows(String tableName, String startRowKey, int pageSize) {
        tableExists(tableName);
        List<JSONObject> list = new ArrayList<>();
        TableName name = TableName.valueOf(tableName);
        Scan scan = new Scan();
        scan.withStartRow(Bytes.toBytes(startRowKey));
        PageFilter pageFilter = new PageFilter(pageSize + 1);
        scan.setFilter(pageFilter);
        Table table;
        try {
            table = connection.getTable(name);
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> iterator = scanner.iterator();
            while (iterator.hasNext()) {
                Result rs = iterator.next();
                list.add(this.buildRsToJSON(rs));
            }
        } catch (IOException e) {
            log.error("error in getting paged rows in " + tableName + " from " + startRowKey
                    + " with pagesize " + pageSize, e);
        }
        return list;
    }

    public List<JSONObject> selectByRowKeyPrefixFilter(String tableName, String rowKeyPrefix) {
        List<JSONObject> list = new ArrayList<>();
        try {
            Scan scan = new Scan();
            Table table = connection.getTable(TableName.valueOf(tableName));
            scan.setFilter(new PrefixFilter(rowKeyPrefix.getBytes()));
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> iterator = scanner.iterator();
            while (iterator.hasNext()) {
                Result rs = iterator.next();
                list.add(this.buildRsToJSON(rs));
            }
        } catch (Exception e) {
            log.error("rowKey 模糊查询数百 :" + tableName, e);
        }

        return list;
    }

    private JSONObject buildRsToJSON(Result rs) {
        JSONObject json = new JSONObject();
        json.put("rowKey", Bytes.toString(rs.getRow()));
        JSONArray cells = new JSONArray();
        if (rs.rawCells() != null) {

            for (Cell cell : rs.rawCells()) {
                JSONObject cellJSON = new JSONObject();

                String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(),
                        cell.getRowLength());
                String qualifier = Bytes.toString(cell.getQualifierArray(),
                        cell.getQualifierOffset(), cell.getQualifierLength());

                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                        cell.getValueLength());

                cellJSON.put("row", row);
                cellJSON.put("qualifier", qualifier);
                cellJSON.put("value", value);
                try {
                    JSONObject valueJSON = JSONObject.parseObject(value);
                    cellJSON.put("valueJSON", valueJSON);
                } catch (Exception e) {
                    log.error("error in parsing value json:" + value, e.getMessage());
                }
                cells.add(cellJSON);

            }
        }
        json.put("cells", cells);

        return json;
    }

}

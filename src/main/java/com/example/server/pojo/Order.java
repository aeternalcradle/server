package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("orderitem_table")
public class Order {
    public Integer id;

    public String name;
    public Float price;
    public Integer num;
    public Integer pid;
    public Integer bookId;
    public String time;

}

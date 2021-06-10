package com.asura.kafka.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@author zzyx 2021/6/9/009
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "books")
public class Books {
    @TableId(value = "bid", type = IdType.AUTO)
    private Integer bid;

    @TableField(value = "bookname")
    private String bookname;

    @TableField(value = "price")
    private Integer price;

    @TableField(value = "author")
    private String author;
}
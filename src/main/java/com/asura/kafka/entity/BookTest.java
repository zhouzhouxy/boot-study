package com.asura.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzyx 2021/1/2/002
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTest implements Serializable {
    private Long id;
    private String name;

}

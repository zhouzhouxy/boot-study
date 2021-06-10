package com.asura.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzyx 2021/6/8/008
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageType<T>  implements Serializable {
    private T data;
    private String message;
}

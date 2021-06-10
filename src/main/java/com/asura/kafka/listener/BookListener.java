package com.asura.kafka.listener;

import cn.hutool.json.JSONUtil;
import com.asura.kafka.entity.BookTest;
import com.asura.kafka.entity.MessageType;
import com.asura.kafka.event.BookEvent;
import com.asura.kafka.socket.SocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zzyx 2021/6/4/004
 */
@Component
@Slf4j
public class BookListener {

    @Resource
    private SocketHandler socketHandler;

    @EventListener
    public void bookListener(BookEvent event) {
        String msg = JSONUtil.toJsonStr(new MessageType<BookTest>(event.getBookTest(),"book"));
        socketHandler.sendMessageForAllClient(msg);
    }
}

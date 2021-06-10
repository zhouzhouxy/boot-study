package com.asura.kafka.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.asura.kafka.mapper.BooksMapper;
import com.asura.kafka.entity.Books;
import com.asura.kafka.service.BooksService;
/**
 *@author zzyx 2021/6/9/009
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService{

}

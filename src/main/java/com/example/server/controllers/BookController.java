package com.example.server.controllers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.mapper.BookMapper;
import com.example.server.pojo.Order;
import com.example.server.pojo.Poi;
import com.example.server.service.IBookService;
import com.example.server.service.Impl.BookServiceImpl;
import com.example.server.vo.PoiVo;
import com.example.server.pojo.Book;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

//          Controller作用:
//      	负责接收客户端请求并处理请求的分发和响应。
//          提供接口给前端或其他系统进行交互。
//         	处理请求参数的验证和转换。
//        	协调调用服务层的方法来完成业务逻辑的处理。
//        	返回响应给客户端或其他系统。

@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {
    //spring依赖注入，它用于告诉Spring容器在需要使用BookServiceImpl对象时，将其自动注入到bookService字段中
    @Autowired
    private IBookService bookService;

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        Page<Book> page = new Page<Book>(pageNum,pageSize);
        IPage<Book> pageResult = bookService.page(page);
        List voList = pageResult.getRecords().stream().map(book->{

            return book;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id){

        Book book = bookService.getById(id);
        if(book==null){
            throw  PoiException.NotFound();
        }
        return Result.success(book);
    }

    @PostMapping("add")
    public Result add(@RequestBody Book book){
        bookService.save(book);
        return Result.success(book);
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        bookService.removeById(id);
        return Result.success();
    }
    @PutMapping("edit/{id}")
    public Result edit(@RequestBody Book book , @PathVariable int id){
        book.setId(id);
        bookService.updateById(book);
        return Result.success(book);
    }

    @PutMapping("editnum/{id}")
    public Result editNum(@RequestBody Book order , @PathVariable int id){
        Book order1 = bookService.getById(id);
        order.setId(id);
        order.num=order1.num-order.num;
        bookService.updateById(order);
        return Result.success(order);
    }
}


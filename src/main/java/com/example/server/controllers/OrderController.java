package com.example.server.controllers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.pojo.Book;
import com.example.server.pojo.Order;
import com.example.server.service.IOrderService;
import com.example.server.service.Impl.OrderServiceImpl;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    //spring依赖注入，它用于告诉Spring容器在需要使用OrderServiceImpl对象时，将其自动注入到OrderService字段中
    @Autowired
    private IOrderService orderService;

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        log.info("my info,pageNum={} pageSize={}",pageNum,pageSize);
        Page<Order> page = new Page<Order>(pageNum,pageSize);
        IPage<Order> pageResult = orderService.page(page);
        List voList = pageResult.getRecords().stream().map(order->{

            return order;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id){
        log.info("my detail,id={}",id);
        Order book = orderService.getById(id);
        if(book==null){
            throw  PoiException.NotFound();
        }
        return Result.success(book);
    }

    @PostMapping("add")
    public Result add(@RequestBody Order book){
        log.info("book add,name = {} ",book.name);

        orderService.save(book);
        return Result.success(book);
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        log.info("my detail,id={}",id);
        orderService.removeById(id);
        return Result.success();
    }


    @PutMapping("edit/{id}")
    public Result edit(@RequestBody Order order , @PathVariable int id){
        log.info("poi edit,name={} price={} num={}",order.name,order.price,order.num);
        order.setId(id);
        orderService.updateById(order);
        return Result.success(order);
    }
}


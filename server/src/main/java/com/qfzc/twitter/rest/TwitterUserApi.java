package com.qfzc.twitter.rest;

import cn.hutool.core.util.StrUtil;
import com.qfzc.twitter.domain.entity.TwitterUserEntity;
import com.qfzc.twitter.infrastructure.dao.service.TwitterUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/6/5 15:03
 */
@RestController
@CrossOrigin
public class TwitterUserApi {

    @Resource
    TwitterUserService twitterUserService;

    /**
     * 根据钱包地址查询用户
     *
     * @param address
     * @return
     */
    @GetMapping("/twitter/users/{address}")
    public ResponseEntity<List<TwitterUserEntity>> watchUsers(@PathVariable(name = "address") String address) {
        return ResponseEntity.ok(twitterUserService.findUsersByAddress(address));
    }


    /**
     * 删除账户与推特用户关联关系
     *
     * @param tid
     * @param address
     * @return
     */
    @DeleteMapping("/twitter/unbind/{tid}/{address}")
    public ResponseEntity<Boolean> unbind(@PathVariable(name = "tid") String tid, @PathVariable(name = "address") String address) {
        return ResponseEntity.ok(twitterUserService.unbindTwitterUser(tid, address));
    }


    /**
     * 添加被监控用户
     *
     * @param url
     * @param address
     * @return
     */
    @PostMapping("/twitter/users")
    public ResponseEntity<String> users(String url, String address) {
        if (StrUtil.isBlank(url)) {
            throw new NullPointerException("Url cannot be empty");
        }
        if (url.lastIndexOf("/") < 0) {
            throw new NullPointerException("Incorrect url");
        }
        String name = url.substring(url.lastIndexOf("/") + 1);

        twitterUserService.saveAccount(address, name);
        return ResponseEntity.ok().build();
    }
}

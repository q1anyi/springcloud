package com.eureka.service;

import com.eureka.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author: caicy
 * @Date: 2019-07-03 11:07
 * @Description:
 */
@Service("userService")
public class UserService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    public String getCacheKey(Long id) {
        return String.valueOf(id);
    }

    /**
     * 置指定了命令的名称为getUserById，组名为userGroup，线程池名称为getUserThread。
     * 通过设置命令组，Hystrix会根据组来组织和统计命令的告警、仪表盘等信息。默认情况下，Hystrix命令通过组名来划分线程池，即组名相同的命令放到同一个线程池里，如果通过threadPoolKey设置了线程池名称，则按照线程池名称划分。
     *
     * @param id
     * @return
     */
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "getUserDefault", commandKey = "getUserById", groupKey = "userGroup",
            threadPoolKey = "getUserThread")
    public User getUser(@PathVariable Long id) {
        log.info("获取用户信息");
        return restTemplate.getForObject("http://Server-Provider/user/{id}", User.class, id);
    }

    /**
     * 此处如果不需要服务降级的话只需要在@HystrixCommand注解里加入一个ignoreExceptions = {NullPointerException.class} 这样就不会进入getUserDefault2方法了
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "getUserDefault2")
    public User getUserDefault(Long id) {
        String a = null;
        a.toString();
        User user = new User();
        user.setId(-1L);
        user.setUsername("defaultUser");
        user.setPassword("123456");
        return user;
    }

    /**
     * 对于方法抛出的异常信息，我们可以在服务降级的方法中使用Throwable对象获取
     *
     * @param id
     * @param e
     * @return
     */
    public User getUserDefault2(Long id, Throwable e) {
        System.out.println(e.getMessage());
        User user = new User();
        user.setId(-2L);
        user.setUsername("defaultUser2");
        user.setPassword("123456");
        return user;
    }

    public List<User> getUsers() {
        return this.restTemplate.getForObject("http://Server-Provider/user", List.class);
    }

    public String addUser() {
        User user = new User(1L, "mrbird", "123456");
        HttpStatus status = this.restTemplate.postForEntity("http://Server-Provider/user", user, null).getStatusCode();
        if (status.is2xxSuccessful()) {
            return "新增用户成功";
        } else {
            return "新增用户失败";
        }
    }

    /**
     * 在涉及到更新User信息的方法上，我们要及时的清除相应的缓存，否则将会导致缓存数据和实际数据不一致的问题
     *
     * @param user
     */
    @CacheRemove(commandKey = "getUserById")
    @HystrixCommand
    public void updateUser(@CacheKey("id") User user) {
        this.restTemplate.put("http://Server-Provider/user", user);
    }

    /**
     * 虽然通过请求的合并可以减轻带宽和服务的压力，但合并请求的过程也会带来额外的开销。就拿上面的testCache来说，比如我们对单个findUser的方法调用耗时5ms，
     * 那么调用4次耗时可以粗略的估算为20ms。当我们使用Hystrix的请求合并功能后，前3次请求（f1、f2和f3）进行了合并，第4次请求（f4）没有进行合并，
     * 那么耗时可以粗略的估算为3*5+100+5=120ms（100为上面timerDelayInMilliseconds中指定的时间范围，在该时间段过后，才会调用第4次请求），
     * 结果明显比单独调用4次来得高。所以实际中是否该使用Hystrix的请求合并功能，需结合实际需求进行抉择。
     *
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "findUserBatch", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    public Future<User> findUser(Long id) {
        log.info("获取单个用户信息");
//        return new AsyncResult<User>() {
//            @Override
//            public User invoke() {
//                return restTemplate.getForObject("http://Server-Provider/user/{id}", User.class, id);
//            }
//        };
        return null;
    }

    @HystrixCommand
    public List<User> findUserBatch(List<Long> ids) {
        log.info("批量获取用户信息,ids: " + ids);
        User[] users = restTemplate.getForObject("http://Server-Provider/user/users?ids={1}", User[].class, StringUtils.join(ids, ","));
        return Arrays.asList(users);
    }

    public void updateUser() {
        User user = new User(1L, "mrbird", "123456");
        this.restTemplate.put("http://Server-Provider/user", user);
    }

    public void deleteUser(@PathVariable Long id) {
        this.restTemplate.delete("http://Server-Provider/user/{1}", id);
    }
}

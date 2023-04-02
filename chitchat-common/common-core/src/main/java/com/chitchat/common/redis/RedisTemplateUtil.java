package com.chitchat.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作
 * Created by Js on 2022/8/12.
 */
@Component
@Slf4j
public class RedisTemplateUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public <T> T get(String key, Class<T> clazz) {
        Object valueObj = redisTemplate.opsForValue().get(key);
        if (clazz.isInstance(valueObj)) {
            return (T) valueObj;
        } else if (clazz == Long.class && valueObj instanceof Integer) {
            Integer obj = (Integer) valueObj;
            return (T) Long.valueOf(obj.longValue());
        }
        return null;
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    public Long decrRejectNegative(String key, long delta) {
        long newValue = 0;
        if (this.hasKey(key)){
            Long old = this.get(key, Long.class);
            if (old.longValue() <= 0){
                this.set(key, 0);
            }else {
                newValue = this.decr(key, delta);
            }
        }
        return newValue;
    }

    public Boolean setBit(String key, long offset, boolean value){
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    public Boolean getBit(String key, long offset){
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    public Long getBitCount(String key){
        Long bitCount = (Long) redisTemplate.execute((RedisCallback) cbk -> cbk.bitCount(key.getBytes()));
        return bitCount;
    }


    /*********** HASH操作 ***********/

    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Boolean hSet(String key, String hashKey, Object value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Boolean hSetAll(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    public void hSetAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void hDel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public Long hIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public Long hDecr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /***********  Set操作 ***********/

    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public Long sAdd(String key, Object value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    public Long sAdd(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /***********  ZSet 操作 ***********/

    /**
     * 单个新增or存在更新
     * zset与set最大的区别就是每个元素都有一个score，因此有个排序的辅助功能
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zSAdd(String key, Object value, double score){
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 批量新增or存在更新
     *
     * @param key
     * @param tuples
     * @return
     */
    public Long zSAdd(String key, Set<ZSetOperations.TypedTuple<Object>> tuples){
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * 使用加法操作分数
     * 存在+score，不存在新增一条 zincrby
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Double zSIncrementScore(String key, Object value, double score){
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    //通过key/value删除

    /**
     * 批量移除ZSet中的key
     *
     * @param key
     * @param values
     * @return
     */
    public Long zSRemove(String key, Object... values){
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 通过排名区间删除
     * 0 -1 表示获取全部的集合内容
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zSRemoveRange(String key, long start, long end){
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 通过分数区间删除
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zSRemoveRangeByScore(String key, double min, double max){
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取个人排行
     *
     * @param key
     * @param value
     * @return
     */
    public Long zSRank(String key, Object value){
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 获取个人分数
     *
     * @param key
     * @param value
     * @return
     */
    public Double zSScore(String key, Object value){
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 通过排名区间获取列表值集合（正序）0 -1 表示获取全部
     * 逆序reverseRange
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> zSRange(String key, long start, long end){
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 通过排名区间获取列表值和分数集合（正序）0 -1 表示获取全部的集合内容
     * 逆序reverseRangeWithScores
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zSRangeWithScores(String key, long start, long end){
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 统计分数区间的人数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zSCount(String key, long min, long max){
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 统计集合基数
     *
     * @param key
     * @return
     */
    public Long zSZCard(String key){
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 通过分数区间获取列表值集合（正序）
     * 逆序reverseRangeByScore
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> zSRangeByScore(String key, double min, double max){
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 通过分数区间获取列表值和分数集合（正序）
     * 逆序reverseRangeByScoreWithScores
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zSRangeByScoreWithScores(String key, double min, double max){
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /***********  List操作 ***********/

    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    public Long lPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public Long lPush(String key, Object value, long time) {
        Long index = redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }

    public Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    public Long lPushAll(String key, Long time, Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }

    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
}

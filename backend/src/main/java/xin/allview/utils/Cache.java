package xin.allview.utils;

import com.jfinal.plugin.redis.Redis;
import java.util.Date;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Created by neo on 1/22/17.
 *
 * Redis tools
 */
public class Cache {

  /**
   * Match for setting action result
   */
  private static final String RESULT_OK = "OK";

  private static com.jfinal.plugin.redis.Cache REDIS;

  /**
   * Initialization
   *
   * @return success or not
   */
  public static boolean init() {
    REDIS = Redis.use();

    if (null != REDIS) {
      // [Neo] check if redis permission denied
      try {
        REDIS.set("use", new Date().getTime());
      } catch (JedisConnectionException e) {
        REDIS = null;
        Log.e("redis permission denied");
      }
    }

    return null != REDIS;
  }

  public static com.jfinal.plugin.redis.Cache getRedis() {
    return REDIS;
  }

  /**
   * Get the key-value's value
   *
   * @return value
   */
  public static Object get(Object key) {
    Object result = null;

    if (null != REDIS) {
      result = REDIS.get(key);
    }

    return result;
  }

  /**
   * Set key-value
   *
   * @return success or not
   */
  public static boolean set(Object key, Object value) {
    boolean result = false;

    if (null != REDIS) {
      result = RESULT_OK.equals(REDIS.set(key, value));
    }

    return result;
  }

  /**
   * Delete the key-value
   *
   * @return success or not
   */
  public static boolean del(Object key) {
    boolean result = false;

    if (null != REDIS) {
      result = (1 == REDIS.del(key));
    }

    return result;
  }

}

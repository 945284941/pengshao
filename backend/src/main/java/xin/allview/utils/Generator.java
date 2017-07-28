package xin.allview.utils;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.redis.RedisPlugin;
import javax.sql.DataSource;

/**
 * Created by neo on 1/23/17.
 */
public class Generator {

  /**
   * Generate model classes
   * need properties:
   *
   * <pre>
   * + model_package
   * </pre>
   */
  public static boolean model(DataSource dataSource) {
    boolean result = false;
    final String modelPackage = PropKit.get("model_package").trim();

    if (null != modelPackage && modelPackage.length() > 0) {
      final String modelFolder =
          PathKit.getWebRootPath() + "/../src/main/java/" + modelPackage
              .replaceAll("\\.", "\\/");

      final String baseFolder = modelFolder + "/base";
      final String basePackage = modelPackage + ".base";

      com.jfinal.plugin.activerecord.generator.Generator gen = new com.jfinal.plugin.activerecord.generator.Generator(
          dataSource, basePackage, baseFolder, modelPackage, modelFolder);

      gen.setGenerateDaoInModel(true);
      gen.setGenerateDataDictionary(true);
      gen.setRemovedTableNamePrefixes("t_");

      gen.generate();
      result = true;
    }

    return result;
  }

  /**
   * Generate a Cache Plugin, Redis supported
   * need properties:
   *
   * <pre>
   * + redis_cache_name
   * + redis_host
   * + redis_port
   * + redis_timeout
   * + redis_pwd
   * </pre>
   */
  public static IPlugin cachePlugin() {
    RedisPlugin redis;

    final String redisPassword = PropKit.get("redis_pwd");
    if (null != redisPassword) {
      redis = new RedisPlugin(PropKit.get("redis_cache_name"),
          PropKit.get("redis_host"),
          PropKit.getInt("redis_port"), PropKit.getInt("redis_timeout"),
          redisPassword);
    } else {
      redis = new RedisPlugin(PropKit.get("redis_cache_name"),
          PropKit.get("redis_host"),
          PropKit.getInt("redis_port"), PropKit.getInt("redis_timeout"));
    }

    return redis;
  }

  /**
   * Generate a Data Source Provider, Druid+MySQL supported
   * need properties:
   *
   * <pre>
   * + db_host
   * + db_port
   * + db_name
   * + db_user
   * + db_pwd
   * + db_encode
   * </pre>
   */
  public static IDataSourceProvider dataSourceProvider() {
    final String mysqlUrlFormatter =
        "jdbc:mysql://%s:%s/%s?characterEncoding=%s&zeroDateTimeBehavior=convertToNull";
    final String jdbcUrl = String
        .format(mysqlUrlFormatter, PropKit.get("db_host"),
            PropKit.get("db_port"),
            PropKit.get("db_name"), PropKit.get("db_encode"));

    DruidPlugin druid = new DruidPlugin(jdbcUrl, PropKit.get("db_user"),
        PropKit.get("db_pwd"));
    WallFilter wall = new WallFilter();
    wall.setDbType("mysql");
    druid.addFilter(wall);
    druid.addFilter(new StatFilter());
    druid.start();

    return druid;
  }

  /**
   * Generate a Stat Handler, Druid Stat View Handler supported
   * need properties:
   *
   * <pre>
   * + druid_stat_view
   * </pre>
   */
  public static Handler statHandler() {
    final String path = PropKit.get("druid_stat_view");
    Log.i("druid stat path: " + path);
    return new DruidStatViewHandler(path);
  }
}

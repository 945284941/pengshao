package xin.allview.utils;

import com.jfinal.kit.PropKit;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by neo on 23/01/2017.
 */
public class GeneratorTest {

  @Before
  public void setUp() throws Exception {
    PropKit.use("developer.properties");
  }

  @Test
  public void model() throws Exception {
    Generator.model(Generator.dataSourceProvider().getDataSource());
  }

}
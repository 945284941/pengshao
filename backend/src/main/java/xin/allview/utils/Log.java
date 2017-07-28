package xin.allview.utils;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/**
 * Created by neo on 30/12/2016.
 *
 * Just a pretty colorful log utils
 */
public class Log {

  /**
   * Initialization, **must called** when using IntelliJ IDEA
   */
  public static void init() {
    System.setProperty("jansi.passthrough", "true");
    AnsiConsole.systemInstall();
  }

  /**
   * The Destruction, it depends on you
   */
  public static void deinit() {
    System.setProperty("jansi.passthrough", "false");
    AnsiConsole.systemUninstall();
  }

  private static Ansi genAnsi(Ansi.Color fg, String msg) {
    return Ansi.ansi().fg(fg).a(msg).reset();
  }

  /**
   * Debug log
   */
  public static void d(String msg) {
    System.out.println(genAnsi(Ansi.Color.BLUE, msg));
  }

  /**
   * Info log
   */
  public static void i(String msg) {
    System.out.println(genAnsi(Ansi.Color.GREEN, msg));
  }

  /**
   * Warning log
   */
  public static void w(String msg) {
    System.out.println(genAnsi(Ansi.Color.YELLOW, msg));
  }

  /**
   * Error log
   */
  public static void e(String msg) {
    System.out.println(genAnsi(Ansi.Color.RED, msg));
  }
}

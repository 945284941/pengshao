package xin.allview.schoolcms.tool;

import static org.fusesource.jansi.Ansi.Color;
import static org.fusesource.jansi.Ansi.ansi;

public class LogUtil {

	/**
	 * 记录普通日志
	 *
	 * @param cont
	 */
	public static void writeLog(String cont) {
		outLog(Color.GREEN,"[INOF]->"+cont+"<-");
	}

	/**
	 * 记录ERORR日志
	 *
	 * @param cont
	 */
	public static void writeErrorLog(String cont) {
		outLog(Color.RED,"[ERROR]->"+cont+"<-");
	}

	/**
	 * 记录ERROR日志
	 *
	 * @param cont
	 * @param ex
	 */
	public static void writeErrorLog(String cont, Exception ex) {
		outLog(Color.RED,"[ERROR]->"+cont+"<-"+"\n"+ex);
	}


	/**
	 * debug方法
	 *
	 * @param cont
	 */
	public static void writeDebug(String cont) {
		outLog(Color.BLUE,"[DEBUG]->"+cont+"<-");
	}


	public static void outLog(Color color, String cont){
		System.out.println(ansi().fg(color).a(cont).reset());
	}
}

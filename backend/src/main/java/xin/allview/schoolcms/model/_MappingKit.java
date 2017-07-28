package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("t_classification", "id", T_classification.class);
		arp.addMapping("t_note", "id", T_note.class);
		arp.addMapping("t_reply", "id", T_reply.class);
		arp.addMapping("t_student", "id", T_student.class);
		arp.addMapping("t_upesnuser", "userid", T_upesnUser.class);
		arp.addMapping("l_user", "id", L_user.class);
		arp.addMapping("l_userclassification", "id", L_userClassification.class);
		arp.addMapping("t_weixinuser", "userid", T_weixinuser.class);

		arp.addMapping("t_functionshop", "userid", T_functionshop.class);

		arp.addMapping("t_role","id",T_role.class);
		arp.addMapping("l_upesnuserrole","id",L_upesnuserrole.class);
		arp.addMapping("l_classificationnote","id",L_classificationnote.class);
		arp.addMapping("l_roleauthor","id",L_roleauthor.class);
		arp.addMapping("t_menu","id",T_menu.class);
		arp.addMapping("t_department","id",T_department.class);
		arp.addMapping("tb_message_people","id",TB_message_people.class);
		arp.addMapping("l_departuser","id",L_departuser.class);
		arp.addMapping("tb_message","id",TB_message.class);
	}
}

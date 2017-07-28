package xin.allview.schoolcms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseReply<M extends BaseReply<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setCreater(java.lang.String creater) {
		set("creater", creater);
	}

	public java.lang.String getCreater() {
		return get("creater");
	}

	public void setCreateTime(java.lang.String createTime) {
		set("create_time", createTime);
	}

	public java.lang.String getCreateTime() {
		return get("create_time");
	}

	public void setVersion(java.lang.Integer version) {
		set("version", version);
	}

	public java.lang.Integer getVersion() {
		return get("version");
	}

	public void setExpand(java.lang.String expand) {
		set("expand", expand);
	}

	public java.lang.String getExpand() {
		return get("expand");
	}

	public void setReplyContext(java.lang.String replyContext) {
		set("reply_context", replyContext);
	}

	public java.lang.String getReplyContext() {
		return get("reply_context");
	}

	public void setReplyNote(java.lang.Integer replyNote) {
		set("reply_note", replyNote);
	}

	public java.lang.Integer getReplyNote() {
		return get("reply_note");
	}

	public void setType(java.lang.Integer type) {
		set("type", type);
	}

	public java.lang.Integer getType() {
		return get("type");
	}

}
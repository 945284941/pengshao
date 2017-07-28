package xin.allview.schoolcms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseStudent<M extends BaseStudent<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setNo(java.lang.String no) {
		set("no", no);
	}

	public java.lang.String getNo() {
		return get("no");
	}

	public void setSectionId(java.lang.Integer sectionId) {
		set("section_id", sectionId);
	}

	public java.lang.Integer getSectionId() {
		return get("section_id");
	}

	public void setSex(java.lang.String sex) {
		set("sex", sex);
	}

	public java.lang.String getSex() {
		return get("sex");
	}

	public void setAge(java.lang.String age) {
		set("age", age);
	}

	public java.lang.String getAge() {
		return get("age");
	}

	public void setLevelId(java.lang.String levelId) {
		set("level_id", levelId);
	}

	public java.lang.String getLevelId() {
		return get("level_id");
	}

	public void setClassId(java.lang.Integer classId) {
		set("class_id", classId);
	}

	public java.lang.Integer getClassId() {
		return get("class_id");
	}

	public void setFatherTel(java.lang.String fatherTel) {
		set("father_tel", fatherTel);
	}

	public java.lang.String getFatherTel() {
		return get("father_tel");
	}

	public void setMotherTel(java.lang.String motherTel) {
		set("mother_tel", motherTel);
	}

	public java.lang.String getMotherTel() {
		return get("mother_tel");
	}

	public void setFatherName(java.lang.String fatherName) {
		set("father_name", fatherName);
	}

	public java.lang.String getFatherName() {
		return get("father_name");
	}

	public void setMotherName(java.lang.String motherName) {
		set("mother_name", motherName);
	}

	public java.lang.String getMotherName() {
		return get("mother_name");
	}

	public void setLevelName(java.lang.String levelName) {
		set("level_name", levelName);
	}

	public java.lang.String getLevelName() {
		return get("level_name");
	}

}
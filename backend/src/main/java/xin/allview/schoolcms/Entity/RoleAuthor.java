package xin.allview.schoolcms.Entity;

import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.model.T_role;

import java.util.List;

/**
 * Created by Samous on 2017/5/8 0008.
 */
public class RoleAuthor {
    private Integer id;
    private String name;
    private List<Record> username;
    private List<Record> functionname;

    public RoleAuthor(){}
    public RoleAuthor(Integer id, String name, List<Record> username, List<Record> functionname) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.functionname = functionname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record> getUsername() {
        return username;
    }

    public void setUsername(List<Record> username) {
        this.username = username;
    }

    public List<Record> getFunctionname() {
        return functionname;
    }

    public void setFunctionname(List<Record> functionname) {
        this.functionname = functionname;
    }

    @Override
    public String toString() {
        return "RoleAuthor{" +
                "id=" + id +
                ", username=" + username +
                ", functionname=" + functionname +
                '}';
    }
}

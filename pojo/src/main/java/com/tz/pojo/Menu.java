package com.tz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author TZ
 * @since 2021-04-29
 */
@TableName("t_menu")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      //主键
    private Integer id;

    // 父节点的id
    private Integer pid;

    // 节点名称
    private String name;

    // 节点附带的URL 地址，是将来点击菜单项时要跳转的地址
    private String url;

    // 节点图标的样式
    private String icon;

    // 存储子节点的集合，初始化是为了避免空指针异常
    private List<Menu> children = new ArrayList<>();

    // 控制节点是否默认为打开装，设置为true 表示默认打开
    private boolean open = true;

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Menu{" +
        "id=" + id +
        ", pid=" + pid +
        ", name=" + name +
        ", url=" + url +
        ", icon=" + icon +
        "}";
    }
}

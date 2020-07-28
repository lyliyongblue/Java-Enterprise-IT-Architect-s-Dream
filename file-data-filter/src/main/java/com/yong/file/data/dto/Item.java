package com.yong.file.data.dto;

import java.util.Objects;

/**
 * 数据条目，用于封装从文件中录入的一条完整数据
 * @author created by li.yong on 2020-07-27 23:08:40
 */
public class Item {
    private String id;
    private String groupId;
    /** 指标 */
    private float quota;

    public Item() {
    }

    public Item(String id, String groupId, float quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Float.compare(item.quota, quota) == 0 &&
                Objects.equals(id, item.id) &&
                Objects.equals(groupId, item.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, quota);
    }
}

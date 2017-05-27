package org.kmd.platform.business.taojinbao.dto;

/**
 * Created by Administrator on 2017/5/27 0027.
 */
public class Material {
    private String type;
    private int offset;
    private int count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append(String.format("\"type\":\"%s\"", this.type)).append(",");
        buffer.append(String.format("\"offset\":\"%s\"", this.offset)).append(",");
        buffer.append(String.format("\"count\":\"%s\"", this.count));
        buffer.append("}");
        return buffer.toString();
    }
}

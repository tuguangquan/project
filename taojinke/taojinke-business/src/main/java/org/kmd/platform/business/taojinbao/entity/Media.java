package org.kmd.platform.business.taojinbao.entity;

/**
 * Created by Administrator on 2017/5/27 0027.
 */
public class Media {
    private long fileLength;
    private String fileName;
    private String name;

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

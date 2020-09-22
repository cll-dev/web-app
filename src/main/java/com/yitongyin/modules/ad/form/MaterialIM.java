package com.yitongyin.modules.ad.form;

import com.yitongyin.common.utils.ExcelColumn;

public class MaterialIM {
    @ExcelColumn(value = "ID", col = 1)
    private String id;

    @ExcelColumn(value = "素材关键词（包含颜色+行业+风格+形容词不少于5组词）", col = 2)
    private String tagKey;

    @ExcelColumn(value = "关联分类", col = 3)
    private String materialType;

    @ExcelColumn(value = "关联格式", col = 4)
    private String fileType;

    @ExcelColumn(value = "素材图片路径", col = 5)
    private String coverImgPath;

    @ExcelColumn(value = "素材文件路径", col = 6)
    private String filePath;

    @ExcelColumn(value = "行业", col = 7)
    private String industryIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCoverImgPath() {
        return coverImgPath;
    }

    public void setCoverImgPath(String coverImgPath) {
        this.coverImgPath = coverImgPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIndustryIds() {
        return industryIds;
    }

    public void setIndustryIds(String industryIds) {
        this.industryIds = industryIds;
    }
}

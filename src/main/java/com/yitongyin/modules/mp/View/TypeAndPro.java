package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.util.List;

@Data
public class TypeAndPro {
    private Long typeId;
    private  String typeName;
    private List<proInfo> proInfoList;
    private Integer childNumber;

    @Data
    public class proInfo{
        private  Long proId;
        private  String proName;
        private  String des;
        private  String coverUrl;
        private  Long typId;
    }
}

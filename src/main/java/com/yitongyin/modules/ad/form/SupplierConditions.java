package com.yitongyin.modules.ad.form;

import lombok.Data;

@Data
public class SupplierConditions {
     private String supName;
     private String proName;
     private String processName;
     private Long proCatId;
     private Integer provinceId;
     private Integer cityId;
     private Integer countyId;
     private Integer collectFlag;
     private Integer distanceFlag;
     private Integer isMyCollect;
     private Integer relevantQuotationFlag;
     private String mLongitude;
     private String mLatitude;
     private Long productId;

}

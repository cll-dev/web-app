package com.yitongyin.modules.mp.View;

import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.form.ProductTag;
import lombok.Data;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

@Data
public class ProInfoAndCase {
    private String name;
    private String cover;
    private Long id;
    private String content;
    private List<AdMerchantProductEntity> brothers;
    private Integer caseCount;
    private CaseInfo cases;
    private String materialUrl;
    private Long merchantId;
    private String intro;
    private Long typeId;
    private String typeName;
    private List<DesignFee> designFees;
    private List<ProductTag> tags;
    private String outlink;
}

package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpecCollectKeyView {
    private List<Long> specValueIds;
    private BigDecimal width;
    private BigDecimal height;
    private Long designFeeId;
    private Long merchantProductId;
}

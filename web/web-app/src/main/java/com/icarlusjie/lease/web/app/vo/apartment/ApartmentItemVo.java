package com.icarlusjie.lease.web.app.vo.apartment;


import com.icarlusjie.lease.model.entity.ApartmentInfo;
import com.icarlusjie.lease.model.entity.LabelInfo;
import com.icarlusjie.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "App端公寓信息")
public class ApartmentItemVo extends ApartmentInfo {

    private List<LabelInfo> labelInfoList;

    private List<GraphVo> graphVoList;

    private BigDecimal minRent;
}

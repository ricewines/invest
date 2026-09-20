package io.github.ricewines.sys.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/// 上交所基金信息
@Data
public class SseFund {

    private String listingDate;

    @JsonProperty("LAW_FIRM")
    private String lawFirm;

    @JsonProperty("CONTACT_MOBILE")
    private String contactMobile;

    private String subClass;
    private String fundManager;
    private String companyName;

    @JsonProperty("INDEX_NAME")
    private String indexName;

    private String fundAbbr;
    private String fundType;
    private String fundCode;

    @JsonProperty("INDEX_CODE")
    private String indexCode;

    private String secNameFull;

    @JsonProperty("TRUSTEE_NAME")
    private String trusteeName;
}

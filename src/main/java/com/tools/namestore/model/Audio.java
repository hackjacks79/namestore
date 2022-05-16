package com.tools.namestore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Audio {

    @JsonProperty
    private String inboundEncodedData;
    @JsonProperty
    private String inboundDataType;
}

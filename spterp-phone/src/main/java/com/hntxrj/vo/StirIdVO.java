package com.hntxrj.vo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class StirIdVO implements Serializable {

    @Id
    private String stirId;
    private String compid;
    private String stirName;
}

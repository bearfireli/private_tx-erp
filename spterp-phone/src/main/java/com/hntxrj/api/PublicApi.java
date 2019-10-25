package com.hntxrj.api;

import com.hntxrj.server.DropDownService;
import com.hntxrj.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/public")
@RestController
public class PublicApi {

    private final DropDownService dropDownService;


    @Autowired
    public PublicApi(DropDownService dropDownService) {
        this.dropDownService = dropDownService;
    }


    @PostMapping("/getDropDown")
    public ResultVO getDropDown(Integer classId,String compid) {
        return ResultVO.create(dropDownService.getDropDown(classId,compid));
    }


}

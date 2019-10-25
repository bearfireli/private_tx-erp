package com.hntxrj.util.jdbc.procedure;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import javax.tools.JavaCompiler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lhr
 * @create 2018/2/4
 */
@Data
public class ProcedurePOJO implements java.io.Serializable{
    /**
     * 入参
     */
    private List<Param> comeParameters = new ArrayList<>();
    /**
     * 参数名
     */
    private String procedureName = "";
    /**
     * 参数出参
     */
    private List<Param> outParameters = new ArrayList<>();
    /**
     * 查询的记录集
     */
    private List resultSets = new ArrayList();

    private JSONArray resultArray = new JSONArray();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ProcedurePOJO that = (ProcedurePOJO) o;
        return Objects.equals(comeParameters, that.comeParameters) &&
                Objects.equals(procedureName, that.procedureName) &&
                Objects.equals(outParameters, that.outParameters) &&
                Objects.equals(resultSets, that.resultSets) &&
                Objects.equals(resultArray, that.resultArray);
    }

    @Override
    public int hashCode() {
        System.out.println(comeParameters);
        return Objects.hash(super.hashCode(), comeParameters, procedureName, outParameters, resultSets, resultArray);
    }
}

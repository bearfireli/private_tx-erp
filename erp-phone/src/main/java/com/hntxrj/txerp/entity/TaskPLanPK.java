package com.hntxrj.txerp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskPLanPK implements Serializable {
    private String taskId;
    private String compid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskPLanPK that = (TaskPLanPK) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(compid, that.compid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, compid);
    }
}

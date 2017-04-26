package com.github.kingschan1204.ssh.model.vo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
public class UserByPageVo {
    private List<UserVo> rows;
    private Long total;

    public List<UserVo> getRows() {
        return rows;
    }

    public void setRows(List<UserVo> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public UserByPageVo() {
    }

    public UserByPageVo(List<UserVo> rows, Long total) {

        this.rows = rows;
        this.total = total;
    }
}

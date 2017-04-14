package com.github.kingschan1204.ssh.common.paging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingschan on 2017/4/14.
 */
public class Pagination {
    private Integer limit;
    private Integer pageindex;
    private List<?> data;
    private Integer total;
    private Integer maxPage;
    private Map<String, Object> map;


    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPageindex() {
        return pageindex;
    }

    public void setPageindex(Integer pageindex) {
        this.pageindex = pageindex;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void put(String key, Object value) {
        if (null == getMap()) {
            this.map = new HashMap<String, Object>();
        }
        getMap().put(key, value);
    }
}

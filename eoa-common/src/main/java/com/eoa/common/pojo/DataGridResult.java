package com.eoa.common.pojo;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable {
	//总记录数
	private Long total;
	//每一行的数据
	private List<?> rows;
	//get set 方法
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	

}

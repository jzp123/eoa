package com.eoa.common.pojo;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable {
	//�ܼ�¼��
	private Long total;
	//ÿһ�е�����
	private List<?> rows;
	//get set ����
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

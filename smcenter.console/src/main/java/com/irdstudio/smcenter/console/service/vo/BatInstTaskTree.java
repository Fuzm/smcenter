package com.irdstudio.smcenter.console.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * <p>Description: 批次任务实例树				<p>
 * @author fuzm
 * @date 2018-06-19
 */
public class BatInstTaskTree<T> extends BaseInfo {

	private static final long serialVersionUID = 1L;	
	
	private String id;
	
	private String text;
	
	private String iconCls;
	
	private String checked;
	
	private String state;
	
	@SuppressWarnings("rawtypes")
	private List<BatInstTaskTree> children;
	
	private T attributes;
	
	public BatInstTaskTree() {
		
	}
	
	public BatInstTaskTree(String id, String text) {
		this.id = id;
		this.text = text;
		this.children = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@SuppressWarnings("rawtypes")
	public List<BatInstTaskTree> getChildren() {
		return children;
	}

	public void setChildren(@SuppressWarnings("rawtypes") List<BatInstTaskTree> children) {
		this.children = children;
	}

	public T getAttributes() {
		return attributes;
	}

	public void setAttributes(T attributes) {
		this.attributes = attributes;
	}

}
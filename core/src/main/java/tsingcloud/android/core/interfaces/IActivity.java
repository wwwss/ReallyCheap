package tsingcloud.android.core.interfaces;

import android.view.View.OnClickListener;

/**
 * 功能：界面接口，定义界面需要实现的基本功能，继承点击事件接口<br>
 * 作者：wss<br>
 * 时间：2014年11月19日<br>
 * 版本：<br>
 */
public interface IActivity extends OnClickListener {

	/**
	 * 设置界面布局文件
	 */
	void setContentView();

	/**
	 * 初始化界面布局及业务逻辑
	 */
	void initView();
	
	/**
	 * 网络切换时刷新数据
	 */
	void refreshData();
}
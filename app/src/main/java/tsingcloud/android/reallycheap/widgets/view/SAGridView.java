package tsingcloud.android.reallycheap.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决和ScrollView嵌套的冲突
 */
public class SAGridView  extends GridView {   
		  
	    public SAGridView(Context context, AttributeSet attrs) {   
	        super(context, attrs);   
	    }   
	  
	    public SAGridView(Context context) {   
	        super(context);   
	    }   
	  
	    public SAGridView(Context context, AttributeSet attrs, int defStyle) {   
	        super(context, attrs, defStyle);   
	    }   
	  
	    @Override   
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
	  
	        int expandSpec = MeasureSpec.makeMeasureSpec(   
	                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);   
	        super.onMeasure(widthMeasureSpec, expandSpec);   
	    }   
	}   


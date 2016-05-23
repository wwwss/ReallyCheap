package tsingcloud.android.model.bean;


/**
 *  轮播图实体类
 */
public class BannerBean extends BaseBean{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int shop_id;
	private int shop_product_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShop_id() {
		return shop_id;
	}

	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}

	public int getShop_product_id() {
		return shop_product_id;
	}

	public void setShop_product_id(int shop_product_id) {
		this.shop_product_id = shop_product_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private String image;




}

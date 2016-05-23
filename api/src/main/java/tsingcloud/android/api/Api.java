package tsingcloud.android.api;

/**
 * Created by admin on 2016/3/23.
 * api接口
 */
public interface Api {

//    /**
//     * 公网服务器地址
//     */
//    String SERVER = "http://jinhuola.cc/";

//    /**
//     * 测试服务器地址
//     */
//    String SERVER = "http://192.168.1.116:4000/";

    /**
     * 测试服务器地址2
     */
    String SERVER = "http://192.168.3.20:4000/";

    /**
     * 公网服务器接口地址
     */
    String SERVER_API = SERVER + "api/v1/";

    /**
     * 上传图片
     */
    String UPLOADPICTURES = SERVER_API + "users?";

    /**
     * 服务协议地址
     */
    String AGREEMENT_ADDRESS = "http://103.21.117.21/service_agreement";

    /**
     * 程序入口初始化接口
     */
    String INIT_APPLICATION = SERVER_API + "homes?";

    /**
     * 更新token接口
     */
    String UPDATE_TOKEN = SERVER_API + "shoppers/update_token";

    /**
     * 发送验证码接口
     */
    String SEND_SMS = SERVER_API + "shoppers/send_sms";

    /**
     * 登录接口
     */
    String SIGN_IN = SERVER_API + "shoppers/sign_in";

    /**
     * 更新token接口
     */
    String UPDATE_IMAGE_TOKEN = SERVER_API + "images/uptoken?";

    /**
     * 更新用户信息接口
     */
    String UPDATE_USER_INFO = SERVER_API + "shoppers";

    /**
     * 店铺列表接口
     */
    String GET_SHOPS = SERVER_API + "homes/shops";

    /**
     * 店铺列表接口
     */
    String GET_HOMEPAGE_DATA = SERVER_API + "homes/contents?";

    /**
     * 获取用户信息接口
     */
    String GET_USER_INFO = SERVER_API + "shoppers/";

    /**
     * 获取用户信息接口
     */
    String ADD_SHOPPING_CART = SERVER_API + "carts";

    /**
     * 获取产品分类接口
     */
    String GET_CLASSIFY = SERVER_API + "categories?";

    /**
     * 获取产品小分类具体分类接口
     */
    String GET_SMALL_CLASSIFY = SERVER_API + "categories/sub_categories";

    /**
     * 获取小分类产品接口
     */
    String GET_SMALL_CLASSIFY_PRODUCT = SERVER_API + "products/sub_category/";

    /**
     * 获取具体分类产品接口
     */
    String GET_DETAIL_CATEGORY_PRODUCT = SERVER_API + "products/detail_category/";

    /**
     * 获取购物车列表
     */
    String GET_CARTS = SERVER_API + "carts/";

    /**
     * 获取默认地址
     */
    String GET_DEFAULT_ADDRESSES = SERVER_API + "addresses/default/";


    /**
     * 修改购物车产品数量
     */
    String EDIT_CARTS_PRODUCT_NUM = SERVER_API + "carts/edit_product_num";

    /**
     * 删除购物车条目
     */
    String DELETE_CART_ITEMS = SERVER_API + "carts";

    /**
     * 1地址列表2添加3修改4删除用户地址接口
     */
    String ADDRESSES = SERVER_API + "addresses";

    /**
     * 订单接口
     */
    String ORDERS = SERVER_API + "orders";

    /**
     * 收藏宝贝接口
     */
    String COLLECT_BABY = SERVER_API + "favorites";
    /**
     * 清空收藏宝贝接口
     */
    String DELETE_ALL_COLLECT_BABY = SERVER_API + "favorites/delete_all";

    /**
     * 搜索接口
     */
    String SEARCH = SERVER_API + "products/search";

    /**
     * 热门搜索接口
     */
    String HOT_SEARCH = SERVER_API + "homes/top_search";

    /**
     * 确认收货接口
     */
    String CONFIRM_RECEIPT = SERVER_API + "orders/confirmed";

    /**
     * 订单详情接口
     */
    String ORDERS_DETAILS = SERVER_API + "orders/show/";

    /**
     * 产品详情接口
     */
    String PRODUCTS_DETAILS = SERVER_API + "products/show/";

    /**
     * 获取支付宝订单支付信息接口
     */
    String GET_ORDER_PAY_INFO = SERVER_API + "alipays/alipay_string";

    /**
     * 绑定推送接口
     */
    String BIND_PUSH = SERVER_API + "shoppers/client/";

    /**
     * 消息提示接口
     */
    String MESSAGE_PROMPT = SERVER_API + "messages";

    /**
     * 删除消息提示接口
     */
    String DELETE_MESSAGE_PROMPT = SERVER_API + "messages/delete_all";

    /**
     * 阅读消息提示接口
     */
    String READ_MESSAGE_PROMPT = SERVER_API + "messages/is_read";

//    /**
//     * 发送验证码
//     *
//     * @param phoneNum 手机号码
//     * @return 成功时返回：{ "event": "0", "msg":"success" }
//     */
//    public ApiResponseBean<Void> sendSmsCodeRegister(String phoneNum);


}

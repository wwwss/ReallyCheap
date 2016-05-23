package tsingcloud.android.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tsingcloud.android.model.bean.ApiResponseBean;
import tsingcloud.android.model.bean.ShopBean;

/**
 * Created by admin on 2016/4/5.
 * api接口实现类
 */
public interface ApiImpl{
    @GET("api/v1/homes/shops")
    Call<ApiResponseBean<List<ShopBean>>> getShops(
    );

//    @GET("api/data/福利/{pageCount}/{pageIndex}")
//    Observable<BaseModel<ArrayList<Benefit>>> rxBenefits(
//            @Path("pageCount") int pageCount,
//            @Path("pageIndex") int pageIndex
//    );
//    @GET("api/data/福利/{pageCount}/{pageIndex}")
//    Call<BaseModel<ArrayList<Benefit>>> defaultBenefits2(
//            @Path("pageCount") int pageCount,
//            @Path("pageIndex") int pageIndex
//    );
}

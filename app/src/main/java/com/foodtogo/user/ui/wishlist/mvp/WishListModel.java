package com.foodtogo.user.ui.wishlist.mvp;


import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.base.AppConstants;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.itemdetails.AddFavourites;
import com.foodtogo.user.model.restaurant.ResponseAddCart;
import com.foodtogo.user.model.wishlist.FavouriteRequest;
import com.foodtogo.user.model.wishlist.FavouriteResponse;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class WishListModel implements WishListContractor.Model {


    private WishListPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    WishListModel(WishListPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void removeFavourites(int position, String itemId) {
        AddFavourites addFavourites = new AddFavourites();
        addFavourites.setLang(appRepository.getLanguageCode());
        addFavourites.setProduct_id(String.valueOf(itemId));
        disposable.add(apiInterface
                .addFavourites(addFavourites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ResponseAddCart>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseAddCart> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onFavouriteResult(position, baseResponse.getMessage());
                        } else {
                            mPresenter.apiError(baseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.apiError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.apiError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.apiError(R.string.network_error);
                        } else {
                            mPresenter.apiError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void requestFavourites(int pageNo) {
        FavouriteRequest favouriteRequest = new FavouriteRequest();
        favouriteRequest.setLang(appRepository.getLanguageCode());
        favouriteRequest.setPage_no(String.valueOf(pageNo));
        disposable.add(apiInterface
                .getFavourites(favouriteRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<FavouriteResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<FavouriteResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            if(pageNo==1) {
                                mPresenter.onWishListResult(baseResponse.getData());
                            }else{
                                mPresenter.onLoadMore(baseResponse.getData());
                            }
                        } else {
                            if(pageNo==1) {
                                mPresenter.apiError(baseResponse.getMessage().equals(AppConstants.PRODUCT_NOT_AVAILABLE)?
                                        BaseApplication.getContext().getString(R.string.fav_item_not_available) :baseResponse.getMessage());
                            }else{
                                mPresenter.onLoadMoreError(baseResponse.getMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            mPresenter.apiError(NetworkUtils.getErrorMessage(responseBody));
                        } else if (e instanceof SocketTimeoutException) {
                            mPresenter.apiError(R.string.time_out_error);
                        } else if (e instanceof IOException) {
                            mPresenter.apiError(R.string.network_error);
                        } else {
                            mPresenter.apiError(e.getMessage());
                        }

                    }
                }));
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}

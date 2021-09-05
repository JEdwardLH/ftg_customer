package com.foodtogo.user.ui.viewitemdetails.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.itemdetails.AddFavourites;
import com.foodtogo.user.model.itemdetails.RequestItemDetails;
import com.foodtogo.user.model.itemdetails.RequestToppingDetail;
import com.foodtogo.user.model.itemdetails.ResponseItemDetails;
import com.foodtogo.user.model.itemdetails.ResponseToppingQuantity;
import com.foodtogo.user.model.mycart.CartResponse;
import com.foodtogo.user.model.mycart.ItemQuantityUpdateRequest;
import com.foodtogo.user.model.restaurant.RequestAddCart;
import com.foodtogo.user.model.restaurant.ResponseAddCart;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ViewItemDetailsModel implements ViewItemDetailsContractor.Model {


    private ViewItemDetailsPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    ViewItemDetailsModel(ViewItemDetailsPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestItemDetail(int itemId, int reviewPageNo) {
        RequestItemDetails requestItemDetails = new RequestItemDetails();
        requestItemDetails.setLang(appRepository.getLanguageCode());
        requestItemDetails.setItem_id(String.valueOf(itemId));
        requestItemDetails.setReview_page_no(String.valueOf(reviewPageNo));
        disposable.add(apiInterface
                .requestItemDetails(requestItemDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ResponseItemDetails>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseItemDetails> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getData());
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
    public void addFavourites(int position, String itemId) {
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
    public void requestAddCart(int storeId, int itemId, String quantity, List<Integer> choiceItem, String specialNotes) {
        RequestAddCart requestAddCart = new RequestAddCart();
        requestAddCart.setLang(appRepository.getLanguageCode());
        requestAddCart.setItem_id(String.valueOf(itemId));
        requestAddCart.setChoices_id(choiceItem);
        requestAddCart.setSt_id(String.valueOf(storeId));
        requestAddCart.setQuantity(quantity);
        requestAddCart.setSpecial_notes(specialNotes);
        disposable.add(apiInterface
                .requestAddCart(requestAddCart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ResponseAddCart>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseAddCart> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onSuccess(baseResponse.getData(), baseResponse.getMessage());
                        } else {
                            mPresenter.addCartError(baseResponse.getMessage());
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
    public void requestToppings(String storeId, String itemId, ArrayList<Integer> choiceId) {
        RequestToppingDetail requestToppingDetail=new RequestToppingDetail(itemId,storeId,appRepository.getLanguageCode(),choiceId);
        disposable.add(apiInterface
                .toppingsQuantity(requestToppingDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<ResponseToppingQuantity>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseToppingQuantity> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.updateToppings(baseResponse.getData().getQuantity());
                        } else {
                            mPresenter.addCartError(baseResponse.getMessage());
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
    public void updateCartQuantity(int cartId, int quantity) {
        ItemQuantityUpdateRequest itemQuantityUpdateRequest = new ItemQuantityUpdateRequest();
        itemQuantityUpdateRequest.setLang(appRepository.getLanguageCode());
        itemQuantityUpdateRequest.setCart_id(String.valueOf(cartId));
        itemQuantityUpdateRequest.setQuantity(String.valueOf(quantity));
        disposable.add(apiInterface
                .updateQuantityCart(itemQuantityUpdateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CartResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            //requestCart();
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
    public void close() {
        disposable.dispose();
    }
}

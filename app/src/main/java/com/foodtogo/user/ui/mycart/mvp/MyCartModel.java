package com.foodtogo.user.ui.mycart.mvp;


import com.foodtogo.user.R;
import com.foodtogo.user.data.rest.ApiClient;
import com.foodtogo.user.data.rest.ApiInterface;
import com.foodtogo.user.data.source.AppRepository;
import com.foodtogo.user.model.BaseResponse;
import com.foodtogo.user.model.mycart.CartItemRemoveRequest;
import com.foodtogo.user.model.mycart.CartRequest;
import com.foodtogo.user.model.mycart.CartResponse;
import com.foodtogo.user.model.mycart.ItemChoiceRemoveRequest;
import com.foodtogo.user.model.mycart.ItemQuantityUpdateRequest;
import com.foodtogo.user.model.mycart.PreOrderRemoveRequest;
import com.foodtogo.user.model.mycart.PreOrderRequest;
import com.foodtogo.user.util.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class MyCartModel implements MyCartContractor.Model {


    private MyCartPresenter mPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AppRepository appRepository;
    private ApiInterface apiInterface;

    MyCartModel(MyCartPresenter presenter, AppRepository appRepository1) {
        mPresenter = presenter;
        appRepository = appRepository1;
        apiInterface = ApiClient.getApiInterface();
    }


    @Override
    public void requestCart() {
        CartRequest cartRequest = new CartRequest();
        cartRequest.setLang(appRepository.getLanguageCode());
        disposable.add(apiInterface
                .requestCartDetails(cartRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CartResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            mPresenter.onCartResponse(baseResponse.getData());
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
                            requestCart();
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
    public void requestRemovePreOrder(String storeId) {
        PreOrderRemoveRequest removeRequest=new PreOrderRemoveRequest();
        removeRequest.setLang(appRepository.getLanguageCode());
        removeRequest.setStore_id(storeId);

        disposable.add(apiInterface
                .removePreOrder(removeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            requestCart();
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
    public void removeCartItem(int cartId) {
        CartItemRemoveRequest cartItemRemoveRequest = new CartItemRemoveRequest();
        cartItemRemoveRequest.setLang(appRepository.getLanguageCode());
        cartItemRemoveRequest.setCart_id(String.valueOf(cartId));
        disposable.add(apiInterface
                .removeItemCart(cartItemRemoveRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CartResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            int itemCount = appRepository.getCartCount();
                            appRepository.setCartCount(itemCount - 1);
                            requestCart();
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
    public void updatePreOrder(int storeId, String preOrderDate) {
        PreOrderRequest preOrderRequest = new PreOrderRequest();
        preOrderRequest.setLang(appRepository.getLanguageCode());
        preOrderRequest.setPre_order_date(preOrderDate);
        preOrderRequest.setStore_id(String.valueOf(storeId));
        disposable.add(apiInterface
                .updatePreOrder(preOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CartResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            requestCart();
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
    public void updateCartChoice(int productId, int cartId, List<Integer> choice, String specialRequest) {
        ItemChoiceRemoveRequest itemChoiceRemoveRequest = new ItemChoiceRemoveRequest();
        itemChoiceRemoveRequest.setLang(appRepository.getLanguageCode());
        itemChoiceRemoveRequest.setCart_id(String.valueOf(cartId));
        itemChoiceRemoveRequest.setChoice_id(choice);
        itemChoiceRemoveRequest.setProduct_id(String.valueOf(productId));
        itemChoiceRemoveRequest.setSpecial_request(specialRequest);
        disposable.add(apiInterface
                .updateChoice(itemChoiceRemoveRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CartResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            requestCart();
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
    public void removeItemChoice(int productId, int cartId, List<Integer> choice) {
        ItemChoiceRemoveRequest itemChoiceRemoveRequest = new ItemChoiceRemoveRequest();
        itemChoiceRemoveRequest.setLang(appRepository.getLanguageCode());
        itemChoiceRemoveRequest.setCart_id(String.valueOf(cartId));
        itemChoiceRemoveRequest.setChoice_id(choice);
        itemChoiceRemoveRequest.setProduct_id(String.valueOf(productId));
        disposable.add(apiInterface
                .removeItemChoice(itemChoiceRemoveRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse<CartResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CartResponse> baseResponse) {
                        if (baseResponse.getCode() == 200) {
                            requestCart();
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

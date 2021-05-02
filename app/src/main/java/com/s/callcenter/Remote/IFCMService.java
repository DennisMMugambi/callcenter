package com.s.callcenter.Remote;

import com.s.callcenter.Model.FCMResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAsiZAWYk:APA91bHR95OZ0nOVLmxuD4IphevJ8jlYXURzXH4BtHoksRcYLf5Td470V7JwlAJkHweHnNRprL2QOMz7FqGIH-SJ3vztlNt97JEucjNTAEyy7ZZV5FRrAlrVOD69CxKD_MMxcL7BtQj3"
    })
    @POST("fcm/send")
    Observable<FCMResponseModel> sendNotification(@Body FCMSendData body);
}

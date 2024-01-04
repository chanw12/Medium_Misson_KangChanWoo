package com.ll.medium.global.rsData;

import com.ll.medium.standard.base.Empty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@Getter
public class RsData<T> {
    @NonNull
    String resultCode;
    @NonNull
    int statusCode;
    @NonNull
    String msg;
    @NonNull
    T data;

    public <T> RsData<T> of(T data) {
        return of(this.resultCode, this.msg, data);
    }

    public static <T> RsData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, (T) new Empty());
    }

    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        int statusCode = Integer.parseInt(resultCode.split("-", 2)[0]);

        RsData<T> tRsData = new RsData<>(resultCode, statusCode, msg, data);

        return tRsData;
    }

    @NonNull
    public boolean isSuccess() {
        return getStatusCode() >= 200 && getStatusCode() < 400;
    }

    @NonNull
    public boolean isFail() {
        return !isSuccess();
    }
}
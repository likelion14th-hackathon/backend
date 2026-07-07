package com.todayscasting.common.response;

/**
 * ?붿껌媛?寃利앹뿉 ?ㅽ뙣???꾨뱶紐낃낵 ?먯씤???깆뿉 ?꾨떖?섎뒗 ?묐떟?낅땲??
 */
public record ValidationError(
        String field,
        String message
) {
}

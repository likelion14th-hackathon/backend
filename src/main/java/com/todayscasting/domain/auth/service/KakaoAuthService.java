package com.todayscasting.domain.auth.service;

import com.todayscasting.domain.auth.client.KakaoClient;
import com.todayscasting.domain.auth.dto.KakaoUserResponse;
import com.todayscasting.domain.auth.dto.TokenResponse;
import com.todayscasting.domain.auth.entity.Auth;
import com.todayscasting.domain.auth.repository.AuthRepository;
import com.todayscasting.domain.user.entity.User;
import com.todayscasting.domain.user.repository.UserRepository;
import com.todayscasting.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoClient kakaoClient;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse kakaoLogin(String code) {
        String kakaoAccessToken = kakaoClient.getToken(code).accessToken();
        KakaoUserResponse userInfo = kakaoClient.getUserInfo(kakaoAccessToken);

        String email = userInfo.kakaoAccount().email();
        String nickname = userInfo.kakaoAccount().profile().nickname();
        String providerId = String.valueOf(userInfo.id());

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User(email, nickname);
                    return userRepository.save(newUser);
                });

        authRepository.findByUserAndProvider(user, Auth.Provider.KAKAO)
                .orElseGet(() -> {
                    Auth newAuth = new Auth(user, Auth.Provider.KAKAO, null, providerId);
                    return authRepository.save(newAuth);
                });

        return new TokenResponse(jwtProvider.generateAccessToken(email));
    }
}
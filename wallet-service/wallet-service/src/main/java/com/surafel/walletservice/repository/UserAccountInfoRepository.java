package com.surafel.walletservice.repository;

import com.surafel.walletservice.model.UserAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountInfoRepository extends JpaRepository<UserAccountInfo, Long> {

    @Override
    Optional<UserAccountInfo> findById(Long userId);
}

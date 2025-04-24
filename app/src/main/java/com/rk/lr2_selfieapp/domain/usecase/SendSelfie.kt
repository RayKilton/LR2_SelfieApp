package com.rk.lr2_selfieapp.domain.usecase

import com.rk.lr2_selfieapp.domain.repository.SelfieRepository

class SendSelfie(private val repository: SelfieRepository) {

    operator fun invoke() {
        return repository.sendSelfie()
    }
}
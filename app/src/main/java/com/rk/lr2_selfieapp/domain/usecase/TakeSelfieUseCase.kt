package com.rk.lr2_selfieapp.domain.usecase

import com.rk.lr2_selfieapp.domain.repository.SelfieRepository

class TakeSelfieUseCase(private val repository: SelfieRepository) {

    operator fun invoke() {
        return repository.takeSelfie()
    }
}
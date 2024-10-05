package com.ra.bkuang.features.account.domain.usecase

import com.ra.bkuang.features.account.data.model.AkunModel

interface FindAllAkunUseCase {
  operator fun invoke(): List<AkunModel>
}
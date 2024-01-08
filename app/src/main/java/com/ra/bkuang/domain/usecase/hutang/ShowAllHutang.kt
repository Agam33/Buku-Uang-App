package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.util.Resource

interface ShowAllHutang {
  suspend fun invoke(): Resource<List<HutangModel>>
}
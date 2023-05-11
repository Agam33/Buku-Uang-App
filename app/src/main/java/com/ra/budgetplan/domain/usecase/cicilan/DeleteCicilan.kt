package com.ra.budgetplan.domain.usecase.cicilan

import com.ra.budgetplan.domain.model.CicilanModel

interface DeleteCicilan {
  suspend fun invoke(cicilanModel: CicilanModel)
}
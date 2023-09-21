package com.ra.budgetplan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.usecase.akun.FindCategoryByType
import com.ra.budgetplan.domain.usecase.icon.FindIconByCategory
import com.ra.budgetplan.domain.usecase.kategori.DeleteKategori
import com.ra.budgetplan.domain.usecase.kategori.SaveKategori
import com.ra.budgetplan.domain.usecase.kategori.UpdateKategori
import com.ra.budgetplan.dummy.model.IconDummy
import com.ra.budgetplan.dummy.model.KategoriDummy
import com.ra.budgetplan.util.MainDispatcherRule
import com.ra.budgetplan.util.StatusItem
import com.ra.budgetplan.util.getOrAwaitValue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.rules.TestRule

internal class CategoryViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var categoryViewModel: CategoryViewModel
  private val deleteKategori: DeleteKategori = mock()
  private val updateKategori: UpdateKategori = mock()
  private val findCategoryByType: FindCategoryByType = mock()
  private val saveKategori: SaveKategori = mock()
  private val findIconByCategory: FindIconByCategory = mock()

  @Before
  fun init() {
    categoryViewModel = CategoryViewModel(
      deleteKategori,
      updateKategori,
      findCategoryByType,
      saveKategori,
      findIconByCategory
    )
  }

  @Test
  fun `SaveKategori, should be success`() = runTest {
    val kategori = KategoriDummy.getAllKategori()[0].toModel()

    whenever(saveKategori.invoke(kategori))
      .thenReturn(Unit)

    categoryViewModel.saveKategori(kategori)

    verify(saveKategori).invoke(kategori)
  }

  @Test
  fun `UpdateKategori, should be success`() = runTest {
    val kategori = KategoriDummy.getAllKategori()[0].toModel()

    whenever(updateKategori.invoke(kategori))
      .thenReturn(Unit)

    categoryViewModel.updateCategory(kategori)

    verify(updateKategori).invoke(kategori)
  }

  @Test
  fun `DeleteKategori, should be success`() = runTest {
    val kategori = KategoriDummy.getAllKategori()[0].toModel()

    val actualStatusItem = StatusItem.SUCCESS
    whenever(deleteKategori.invoke(kategori))
      .thenReturn(flow { emit(actualStatusItem) })

   categoryViewModel.deleteCategory(kategori).test {
     val expectedVal = awaitItem()
     assertEquals(actualStatusItem, expectedVal)
     awaitComplete()
   }

    verify(deleteKategori).invoke(kategori)
  }

  @Test
  fun `SetCategories, should be success`() = runTest {
    val listKategori = KategoriDummy.getAllKategori().map { it.toModel() }

    val map = HashMap<TipeKategori, List<KategoriModel>>()
    map[TipeKategori.PENGELUARAN] = listKategori
    map[TipeKategori.PENDAPATAN] = listKategori
    map[TipeKategori.CICILAN] = listKategori

    whenever(findCategoryByType.invoke())
      .thenReturn(flow { emit(map) })

    categoryViewModel.setCategories()

    val expectedVal = categoryViewModel.mapCategory.getOrAwaitValue()

    assertEquals(map, expectedVal)
    assertEquals(map.size, expectedVal.size)
  }

  @Test
  fun `SetCurrentCategory, should be success`() {
    val icons = IconDummy.getAllIcon().map { it.toModel() }

    whenever(findIconByCategory.invoke(IconCategory.INCOME))
      .thenReturn(flow { emit(icons) })

    categoryViewModel.setCurrentCategory(TipeKategori.PENDAPATAN)

    val expectedVal = categoryViewModel.listIcon.getOrAwaitValue()
    val currentCat = categoryViewModel.currCategory.getOrAwaitValue()

    assertNotNull(currentCat)
    assertEquals(expectedVal.size, icons.size)
  }
}
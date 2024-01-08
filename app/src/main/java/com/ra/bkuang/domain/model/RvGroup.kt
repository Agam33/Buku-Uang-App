package com.ra.bkuang.domain.model

class RvGroup<K, V>: LinkedHashMap<K, V>() {

  val keyList = ArrayList<K>()

  fun addIf(key: K, value: V): V? {
    if(!containsKey(key)) {
      keyList.add(key)
      super.put(key, value)
    }
    return super.get(key)
  }

  override fun remove(key: K): V? {
    keyList.remove(key)
    return super.remove(key)
  }

  fun getValueByIndex(index: Int): V? {
    val key = keyList[index]
    return super.get(key)
  }
}
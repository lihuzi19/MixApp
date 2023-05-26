package com.lihuzi.takenotes.utils

import android.util.SparseArray

class GoodsTypeUtils {

    companion object {
        private val typeObtainArray = SparseArray<String>()
        private val typePayArray = SparseArray<String>()

        init {
            //收入
            typeObtainArray.put(-1, "工资");
            typeObtainArray.put(-2, "股票基金收益");
            typeObtainArray.put(-3, "收借款(收到)");
            typeObtainArray.put(-4, "收借款(收到)");
            typeObtainArray.put(-5, "其它");

            //支出
            typePayArray.put(1, "饮食");
            typePayArray.put(2, "出行");
            typePayArray.put(3, "房租房贷");
            typePayArray.put(4, "网络购物");
            typePayArray.put(5, "电子产品");
            typePayArray.put(6, "化妆品");
            typePayArray.put(7, "服饰");
            typePayArray.put(8, "股票基金投资");
            typePayArray.put(9, "收借款(支出)");
            typePayArray.put(10, "其它");
        }

        @JvmStatic
        fun getType(type: Int): String {
            return if (type > 0) {
                typePayArray.get(type)
            } else {
                typeObtainArray.get(type)
            }
        }

        @JvmStatic
        fun getItem(obtain: Boolean): Array<String> {
            return if (obtain) {
                Array(typeObtainArray.size()) { typeObtainArray.valueAt(it) as String }
            } else {
                Array(typePayArray.size()) { typePayArray.valueAt(it) as String }
            }
        }

        @JvmStatic
        fun getTypeWithPosition(obtain: Boolean, position: Int): Int {
            return if (obtain) {
                typeObtainArray.keyAt(position)
            } else {
                typePayArray.keyAt(position)
            }
        }
    }
}
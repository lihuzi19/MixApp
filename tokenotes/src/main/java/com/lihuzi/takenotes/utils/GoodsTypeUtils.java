package com.lihuzi.takenotes.utils;

import android.util.SparseArray;

/**
 * Created by cocav on 2017/11/15.
 */

public class GoodsTypeUtils
{
    private final static SparseArray<String> typeObtainArray;
    private final static SparseArray<String> typePayArray;

    static
    {
        typeObtainArray = new SparseArray<>();
        typeObtainArray.put(-1, "工资");
        typeObtainArray.put(-2, "股票基金收益");
        typeObtainArray.put(-3, "收借款(收到)");
        typeObtainArray.put(-4, "收借款(收到)");
        typeObtainArray.put(-5, "其它");

        typePayArray = new SparseArray<>();
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

    public static String getType(int type)
    {
        if (type > 0)
        {
            return typePayArray.get(type);
        }
        else
        {
            return typeObtainArray.get(type);
        }
    }

    public static String[] getItem(boolean obtain)
    {
        String item[];
        SparseArray array;
        if (obtain)
        {
            array = typeObtainArray;
        }
        else
        {
            array = typePayArray;
        }
        item = new String[array.size()];
        for (int i = 0; i < item.length; i++)
        {
            item[i] = (String) array.valueAt(i);
        }
        return item;
    }

    public static int getTypeWithPosition(boolean obtain, int position)
    {
        if (obtain)
        {
            return typeObtainArray.keyAt(position);
        }
        else
        {
            return typePayArray.keyAt(position);
        }
    }
}

package com.lihuzi.takenotes.utils

import java.lang.StringBuilder
import java.security.MessageDigest

/**
 *@Author lining
 *@Date 2023/6/26
 *@DESC TODO
 **/
class FindVerify {
    companion object {
        const val md5 = "8f8a9132163dc6b8b56a26787fa1a94b"

        @JvmStatic
        fun find(): ByteArray {
            return byteArrayOf(1, 2, 3, 4, 5, 6, 7)
        }

        fun findMd5(byteArray: ByteArray): String {
            var md5 = MessageDigest.getInstance("MD5")
            md5.update(byteArray)
            return toHexString(md5.digest()) ?: ""
        }

        fun toHexString(paramArrayOfByte: ByteArray?): String? {
//            return ApplySigningUtils.toHexString(paramArrayOfByte)
            return toHexStringOrigin(paramArrayOfByte)
        }

//        fun toHexStringKotlin(paramArrayOfByte: ByteArray?): String? {
//            if (paramArrayOfByte == null) {
//                return ""
//            }
//            var sb = StringBuilder()
//            paramArrayOfByte.forEach {
//                var hexString = Int.toString()
//            }
//        }

        fun toHexStringOrigin(paramArrayOfByte: ByteArray?): String? {
            if (paramArrayOfByte == null) {
                return null
            }
            var localStringBuilder = StringBuilder(2 * paramArrayOfByte.size)
            var i = 0
            while (i < paramArrayOfByte.size) {
                var str = (0xFF and paramArrayOfByte[i].toInt()).toString(16)
                if (str.length == 1) {
                    str = "0$str"
                }
                localStringBuilder.append(str)
                ++i
            }
            return localStringBuilder.toString()
        }
    }
}

package com.lihuzi.takenotes.utils

inline fun notNull(vararg args: Any?, success: (args: Any?) -> Unit, fail: () -> Unit) =
        when (args.filterNotNull().size) {
            args.size -> success(args)
            else -> fail()
        }
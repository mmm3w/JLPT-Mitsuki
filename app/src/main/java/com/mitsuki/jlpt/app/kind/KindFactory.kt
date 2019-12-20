package com.mitsuki.jlpt.app.kind

object Kind {
    //词库类型
    const val ALL = 0
    const val N1 = 1
    const val N2 = 2
    const val N3 = 3
    const val N4 = 4
    const val N5 = 5
    const val INVISIBLE = 7
    const val NUMERAL = -1     //小于0时选择不会被记录
    const val MEMORIES = -2

    //测试范围
    const val TESTING_DISPLAY_ALL = 0x0400
    const val TESTING_DISPLAY_VISIBLE = 0X0401
    const val TESTING_DISPLAY_INVISIBLE = 0X0402

    fun getKind(type: Int): KindMode = when (type) {
        ALL -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "全部单词"
        }
        N1 -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "N1单词"
        }
        N2 -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "N2单词"
        }
        N3 -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "N3单词"
        }
        N4 -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "N4单词"
        }
        N5 -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "N5单词"
        }
        NUMERAL -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "数词/量词"
        }
        INVISIBLE -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = "隐藏单词"
        }
        MEMORIES -> object : KindMode {
            override fun getMode() = type
            override fun getTitle() = ""
        }
        else -> object : KindMode {
            override fun getMode() = Kind.ALL
            override fun getTitle() = "全部单词"
        }
    }

}


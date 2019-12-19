package com.mitsuki.jlpt.app.kind

object Kind {
    const val ALL = 0
    const val N1 = 1
    const val N2 = 2
    const val N3 = 3
    const val N4 = 4
    const val N5 = 5
    const val INVISIBLE = 7
    //小于0时选择不会被存储
    const val NUMERAL = -1
    const val MEMORIES = -2

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


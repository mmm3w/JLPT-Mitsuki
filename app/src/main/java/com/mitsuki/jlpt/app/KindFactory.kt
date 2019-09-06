package com.mitsuki.jlpt.app

object Kind {
    const val ALL = 0
    const val N1 = 1
    const val N2 = 2
    const val N3 = 3
    const val N4 = 4
    const val N5 = 5
    const val NUMERAL = 6
    const val INVISIBLE = 7
    const val MEMORIES = -2
}

fun getKind(type: Int): KindMode? = when (type) {
    Kind.ALL -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "全部单词"
    }
    Kind.N1 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N1单词"
    }
    Kind.N2 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N2单词"
    }
    Kind.N3 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N3单词"
    }
    Kind.N4 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N4单词"
    }
    Kind.N5 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N5单词"
    }
    Kind.NUMERAL -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "数词/量词"
    }
    Kind.INVISIBLE -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "隐藏单词"
    }
    Kind.MEMORIES -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = ""
    }
    else -> object : KindMode {
        override fun getMode() = Kind.ALL
        override fun getTitle() = "全部单词"
    }
}
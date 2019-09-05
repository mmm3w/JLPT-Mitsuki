package com.mitsuki.jlpt.app

fun getKind(type: Int): KindMode? = when (type) {
    0 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "全部单词"
    }
    1 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N1单词"
    }
    2 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N2单词"
    }
    3 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N3单词"
    }
    4 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N4单词"
    }
    5 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "N5单词"
    }
    6 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "数词/量词"
    }
    7 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "隐藏单词"
    }
    -2 -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = ""
    }
    else -> object : KindMode {
        override fun getMode() = type
        override fun getTitle() = "全部单词"
    }
}
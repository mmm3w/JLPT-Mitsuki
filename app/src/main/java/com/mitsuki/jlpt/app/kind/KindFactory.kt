package com.mitsuki.jlpt.app.kind

object KindFactory {
//    //词库类型
//    const val ALL = 0
//    const val N1 = 1
//    const val N2 = 2
//    const val N3 = 3
//    const val N4 = 4
//    const val N5 = 5
//    const val INVISIBLE = 7
//    const val NUMERAL = -1     //小于0时选择不会被记录
//    const val MEMORIES = -2
//
//    //测试范围
//    const val TESTING_DISPLAY_ALL = 0x0400
//    const val TESTING_DISPLAY_VISIBLE = 0x0401
//    const val TESTING_DISPLAY_INVISIBLE = 0x0402

    val kindList by lazy {
        arrayListOf(
                GenericKind.ALL_STR,
                GenericKind.N1_STR,
                GenericKind.N2_STR,
                GenericKind.N3_STR,
                GenericKind.N4_STR,
                GenericKind.N5_STR
        )
    }

    val displayList by lazy {
        arrayListOf(
            GenericKind.TESTING_DISPLAY_ALL_STR,
            GenericKind.TESTING_DISPLAY_VISIBLE_STR,
            GenericKind.TESTING_DISPLAY_INVISIBLE_STR
        )
    }

    fun getKind(type: Int): GenericKind = when (type) {
        GenericKind.ALL -> GenericKind(type,GenericKind. ALL_STR)
        GenericKind.N1 -> GenericKind(type, GenericKind.N1_STR)
        GenericKind.N2 -> GenericKind(type, GenericKind.N2_STR)
        GenericKind.N3 -> GenericKind(type, GenericKind.N3_STR)
        GenericKind.N4 -> GenericKind(type, GenericKind.N4_STR)
        GenericKind.N5 -> GenericKind(type, GenericKind.N5_STR)
        GenericKind.NUMERAL -> GenericKind(type, GenericKind.NUMERAL_STR)
        GenericKind.INVISIBLE -> GenericKind(type, GenericKind.INVISIBLE_STR)
        GenericKind.MEMORIES -> GenericKind(type, GenericKind.MEMORIES_STR)
        else -> GenericKind(type, GenericKind.ALL_STR)
    }

    fun getTestingDisplay(type: Int): GenericKind = when (type) {
        GenericKind.TESTING_DISPLAY_ALL -> GenericKind(type, GenericKind.TESTING_DISPLAY_ALL_STR)
        GenericKind.TESTING_DISPLAY_VISIBLE -> GenericKind(type, GenericKind.TESTING_DISPLAY_VISIBLE_STR)
        GenericKind.TESTING_DISPLAY_INVISIBLE -> GenericKind(type, GenericKind.TESTING_DISPLAY_INVISIBLE_STR)
        else -> GenericKind(type, GenericKind.TESTING_DISPLAY_ALL_STR)
    }

    fun name2Type(name: String): Int {
        return when (name) {
            GenericKind.ALL_STR -> GenericKind.ALL
            GenericKind.N1_STR -> GenericKind.N1
            GenericKind.N2_STR -> GenericKind.N2
            GenericKind.N3_STR -> GenericKind.N3
            GenericKind.N4_STR -> GenericKind.N4
            GenericKind.N5_STR -> GenericKind.N5
            GenericKind.INVISIBLE_STR -> GenericKind.INVISIBLE
            GenericKind.NUMERAL_STR -> GenericKind.NUMERAL
            GenericKind.MEMORIES_STR -> GenericKind.MEMORIES
            GenericKind.TESTING_DISPLAY_ALL_STR -> GenericKind.TESTING_DISPLAY_ALL
            GenericKind.TESTING_DISPLAY_VISIBLE_STR -> GenericKind.TESTING_DISPLAY_VISIBLE
            GenericKind.TESTING_DISPLAY_INVISIBLE_STR -> GenericKind.TESTING_DISPLAY_INVISIBLE
            else -> Integer.MIN_VALUE
        }
    }

    fun type2Name(type: Int): String {
        return when (type) {
            GenericKind.ALL -> GenericKind.ALL_STR
            GenericKind.N1 -> GenericKind.N1_STR
            GenericKind.N2 -> GenericKind.N2_STR
            GenericKind.N3 -> GenericKind.N3_STR
            GenericKind.N4 -> GenericKind.N4_STR
            GenericKind.N5 -> GenericKind.N5_STR
            GenericKind.INVISIBLE -> GenericKind.INVISIBLE_STR
            GenericKind.NUMERAL -> GenericKind.NUMERAL_STR
            GenericKind.MEMORIES -> GenericKind.MEMORIES_STR
            GenericKind.TESTING_DISPLAY_ALL -> GenericKind.TESTING_DISPLAY_ALL_STR
            GenericKind.TESTING_DISPLAY_VISIBLE -> GenericKind.TESTING_DISPLAY_VISIBLE_STR
            GenericKind.TESTING_DISPLAY_INVISIBLE -> GenericKind.TESTING_DISPLAY_INVISIBLE_STR
            else -> ""
        }
    }
}




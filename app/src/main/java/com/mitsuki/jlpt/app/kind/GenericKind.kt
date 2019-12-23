package com.mitsuki.jlpt.app.kind

data class GenericKind (val type:Int, val name:String)
{

    companion object {
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
        const val TESTING_DISPLAY_ALL = 0x0500
        const val TESTING_DISPLAY_VISIBLE = 0x0501
        const val TESTING_DISPLAY_INVISIBLE = 0x0502

        /**********************************************************************************************/
        const val ALL_STR = "全部单词"
        const val N1_STR = "N1单词"
        const val N2_STR = "N2单词"
        const val N3_STR = "N3单词"
        const val N4_STR = "N4单词"
        const val N5_STR = "N5单词"
        const val INVISIBLE_STR = "隐藏单词"
        const val NUMERAL_STR = "数词/量词"
        const val MEMORIES_STR = ""

        const val TESTING_DISPLAY_ALL_STR = "隐藏及非隐藏单词"
        const val TESTING_DISPLAY_VISIBLE_STR = "仅限非隐藏单词"
        const val TESTING_DISPLAY_INVISIBLE_STR = "仅限隐藏单词"
    }
}

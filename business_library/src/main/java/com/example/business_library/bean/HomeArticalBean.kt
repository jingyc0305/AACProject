package com.example.business_library.bean

/**
 * @author: JingYuchun
 * @date: 2019/6/27 14:10
 * @desc: 首页文章数据实体
 */
data class HomeArticalBean(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<DatasBean>
){


    data class DatasBean(
        val apkLink: String,
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<TagsBean>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    ) {

        data class TagsBean(
            val name: String,
            val url: String
        )
    }

}


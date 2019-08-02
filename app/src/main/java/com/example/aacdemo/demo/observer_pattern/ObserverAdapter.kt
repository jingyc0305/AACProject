package com.example.aacdemo.demo.observer_pattern

import android.animation.Animator
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.aacdemo.R
/**
 * @author: JingYuchun
 * @date: 2019/8/1 10:43
 * @desc: 消息列表适配器
 */
class ObserverAdapter(data: MutableList<Message>) : BaseMultiItemQuickAdapter<Message, BaseViewHolder>(data) {

    init {
        addItemType(Message.OWENER, R.layout.text_view_1)
        addItemType(Message.TENANT_ONE, R.layout.text_view_2)
        addItemType(Message.TENANT_TWO, R.layout.text_view_3)
    }

    override fun convert(helper: BaseViewHolder, item: Message) {
        when (helper.itemViewType) {
            Message.OWENER -> helper.setText(R.id.left_tv, item.message)
            Message.TENANT_ONE -> helper.setText(R.id.right_tv, item.message)
            Message.TENANT_TWO -> helper.setText(R.id.right_tv, item.message)
        }
    }

//    override fun startAnim(anim: Animator?, index: Int) {
//        super.startAnim(anim, index)
//        if (index < data.size)
//            anim?.startDelay = index * 150L
//    }

}
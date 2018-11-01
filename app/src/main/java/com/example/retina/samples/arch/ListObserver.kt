package com.example.retina.samples.arch

import android.arch.lifecycle.Observer
import com.example.retina.samples.main.model.Post

class ListObserver(val callback:onListChanged) : Observer<List<Post>> {

    private var list:ArrayList<Post> = ArrayList()

    override fun onChanged(it: List<Post>?) {
        it?.let {
            this.list.addAll(it.toCollection(ArrayList()))
            callback.onChangedList(list)
        }
    }

}
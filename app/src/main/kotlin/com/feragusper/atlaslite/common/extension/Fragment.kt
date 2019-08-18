package com.feragusper.atlaslite.common.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.feragusper.atlaslite.common.android.BaseActivity
import com.feragusper.atlaslite.common.android.BaseFragment
import kotlinx.android.synthetic.main.activity_layout.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}
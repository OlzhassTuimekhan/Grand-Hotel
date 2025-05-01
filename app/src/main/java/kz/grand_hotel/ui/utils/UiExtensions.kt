package kz.grand_hotel.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import kz.grand_hotel.ui.utils.LoadingDialogFragment

private const val LOADER_TAG = "LOADING_DIALOG"

fun Fragment.showLoading() {
    if (childFragmentManager.findFragmentByTag(LOADER_TAG) == null) {
        LoadingDialogFragment().apply { isCancelable = false }
            .show(childFragmentManager, LOADER_TAG)
    }
}

fun Fragment.hideLoading() {
    (childFragmentManager.findFragmentByTag(LOADER_TAG) as? DialogFragment)
        ?.dismissAllowingStateLoss()
}

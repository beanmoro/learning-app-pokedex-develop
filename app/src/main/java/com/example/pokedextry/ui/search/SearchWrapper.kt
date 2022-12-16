package com.example.pokedextry.ui.search

import android.app.Activity
import com.example.pokedextry.data.network.APIHelperImpl
import com.example.pokedextry.data.network.RestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers



object SearchWrapper {

    fun inject(view : SearchContract.View) : SearchContract.Presenter {
        val presenter = SearchPresenter()
        val router = SearchRouter()
        val interactor = SearchInteractor(Dispatchers.IO)
        val presenterScope = CoroutineScope(Dispatchers.Main)

        interactor.mOutput = presenter
        interactor.apiHelper = APIHelperImpl(RestClient.instance)

        router.mActivity = view

        presenter.scope = presenterScope
        presenter.mInteractor = interactor
        presenter.mView = view
        presenter.mRouter = router

        return presenter
    }
}
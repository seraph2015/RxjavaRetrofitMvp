package org.seraph.mvprxjavaretrofit.ui.module.base;

/**
 * p层实现类的父类
 * date：2017/12/8 09:18
 * author：Seraph
 * mail：417753393@qq.com
 **/
public interface IABaseContract extends IBaseContract {


    abstract class ABaseActivityPresenter<V extends IBaseContract.IBaseActivityView> implements IBaseContract.IBaseActivityPresenter<V> {

        protected V mView;

        @Override
        public void setView(V v) {
            mView = v;
        }

        @Override
        public void onDetach() {
            //解除绑定
            mView = null;
        }
    }

    abstract class ABaseFragmentPresenter<V extends IBaseFragmentView> implements IBaseContract.IBaseFragmentPresenter<V> {

        protected V mView;

        @Override
        public void setView(V view) {
            this.mView = view;
        }

        @Override
        public void onDetach() {
            //解除绑定
            mView = null;
        }
    }

}

package org.seraph.mvprxjavaretrofit.ui.module.base;

/**
 * p层实现类的父类
 * date：2017/12/8 09:18
 * author：Seraph
 * mail：417753393@qq.com
 **/
public interface IABaseContract extends IBaseContract {


    abstract class ABasePresenter<V extends IBaseContract.IBaseView> implements IBaseContract.IBasePresenter<V> {

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

}

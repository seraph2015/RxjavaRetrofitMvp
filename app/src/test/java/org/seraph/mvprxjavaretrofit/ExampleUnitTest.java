package org.seraph.mvprxjavaretrofit;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRxJavaTest() throws Exception {
        Observable.range(0, 3).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {
                System.out.println("apply->" + integer);
                return Observable.range(0, 3);
            }
        }).subscribe(new Consumer<Object>() {
                         @Override
                         public void accept(Object o) throws Exception {
                             System.out.println("accept->" + o);
                         }
                     }

        );
    }

    @Test
    public void testRxJavaTest1() throws Exception {
        Observable.fromArray(1, 2, 3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("onNext->" + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError->" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete->");
            }
        });

    }

    @Test
    public void testRxJavaTest2() throws Exception {
        Flowable.fromArray(1, 2, 3, 4, 5, 6).subscribe(new Subscriber<Integer>() {

            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                subscription = s;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext->" + integer);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
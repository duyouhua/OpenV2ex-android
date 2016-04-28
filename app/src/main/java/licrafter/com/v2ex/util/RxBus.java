package licrafter.com.v2ex.util;/**
 * Created by Administrator on 2016/3/23.
 */

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * author: lijinxiang
 * date: 2016/3/23
 * http://www.jianshu.com/p/ca090f6e2fe2
 **/
public class RxBus {

    private static RxBus mDefaultInstance;
    // 主题
    private final Subject mBus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    // 提供了一个新的事件
    public void post(Object o) {
        mBus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T extends Object> Observable<T> toObserverable(final Class<T> eventType) {
        return mBus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return eventType.isInstance(o);
            }
        })
                .cast(eventType);
    }
}

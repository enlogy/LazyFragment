# LazyFragment
Fragment懒加载
# Fragment的懒加载实现原理和封装

## 先看效果图
![这里写图片描述](https://img-blog.csdn.net/20180808193931560?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Nya2t5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
为什么要使用懒加载
> 使用懒加载可以优化加载网络数据的时机，做到在需要时加载，不需要时不加载。 

什么时候使用懒加载
> 在使用ViewPager的时候,并且需要节省网络流量的时候使用，因为ViewPager会对fragment进行预加载

ViewPager的预加载流程
![这里写图片描述](https://img-blog.csdn.net/20180808183103909?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Nya2t5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

![这里写图片描述](https://img-blog.csdn.net/20180808183118647?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Nya2t5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)


## 实现懒加载的几个关键方法

``` python
/**
* 在fragment的生命周期之前回调
* @param isVisibleToUser 当前fragment是否可见
*/
@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);    
    }
```
``` python
 /**  返回当前fragment是否可见
     * @return The current value of the user-visible hint on this fragment.
     * @see #setUserVisibleHint(boolean)
     */
    public boolean getUserVisibleHint() {
        return mUserVisibleHint;
    }
```
## 具体实现的思路

>由于显示fragment的时候，存在viewpager的预加载机制，且左右相邻的fragment都会回调setUserVisibleHint这个方法，然后我们希望在进行加载数据的时候view是已经初始化完毕的，因此加载的第一个fragment需要在onStart()方法里面调用setUserVisibleHint(true)。在setUserVisibleHint方法里面判断是否view加载完毕和是否第一次加载，如果是进行数据的获取，否则不做处理。


### 代码实现

``` python
public abstract class BaseLazyFragment extends Fragment {
    private boolean isFirst = true;
    private boolean isPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(onLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetState();
    }

    private void lazyLoad() {
        if (!isFirst || !isPrepared) {
            return;
        }
        isFirst = false;
        initData();
    }

    private void resetState() {
        isFirst = true;
        isPrepared = false;
    }

    protected abstract void initData();

    protected abstract int onLayoutRes();

    protected abstract void initView(View view);
    
}
```


###使用
- 继承自BaseLazyFragment 的fragment都具有懒加载特性
- onLayoutRes()传入fragment要显示的布局ResId
- initView(View view) 进行view的绑定，view是onLayoutRes()传入的布局
- initData()进行数据的访问，如访问网络等，调用到此方法的时候，view都已经初始化过了

###项目地址
	- github [https://github.com/Enlogty/LazyFragment][1]
---------

[1]: https://github.com/Enlogty/LazyFragment

### 1、服务和进程优先级

服务(Service):是一个没有界面,可以长期运行在后台的应用程序;
进程:是应用程序的载体.当应用程序一启动的时候,linux系统就会创建一个进程,这个进程是用来负责运行dalvik虚拟机.而我开发的应用程序就是运行在dalvik虚拟机上的.

退出一个应用程序后,它的进程仍然运行在后台.当手机中的内存空间不够使用时,系统会按照进程的优先级,从低到高,逐级杀死进程,释放空间.

进程的优先级:

1.前台进程(Foreground process)

这个进程运行的软件,界面可见,并且用户可以操作界面.

2.可视进程(Visible process)

这个进程运行的软件,界面可见,但是不能获得焦点,用不能操作界面.比如,有一个透明的界面覆盖住了这个界面.

3.服务进程(Service process)

这个进程运行的软件已经退出,但是后台还运行着一个服务组件.

4.后台进程(Background process)

这个进程运行的软件的界面不可见,但是activity对象没有被销毁;如界面被最小化了.

5.空进程(Empty  process)

这个进程运行的软件的界面都已经不可见了,应用程已经退出了.

### 2、服务的特点

#### 一、 Service简介

Service是Android 系统中的四大组件之一（Activity、Service、BroadcastReceiver、ContentProvider），它跟Activity的级别差不多，但不能自己运行只能后台运行，并且可以和其他组件进行交互。service可以在很多场合的应用中使用，比如播放多媒体的时候用户启动了其他Activity这个时候程序要在后台继续播放，比如检测SD卡上文件的变化，再或者在后台记录你地理信息位置的改变等等，总之服务总是藏在后台的。

Service的启动有两种方式：context.startService() 和 context.bindService()

#### 二、 Service启动流程

##### 1、context.startService() 启动流程：

context.startService()  -> onCreate()  -> onStart()  -> Service running  -> context.stopService()  -> onDestroy()  -> Service stop

如果Service还没有运行，则android先调用onCreate()，然后调用onStart()；

如果Service已经运行，则只调用onStart()，所以一个Service的onStart方法可能会重复调用多次。

如果stopService的时候会直接onDestroy，如果是调用者自己直接退出而没有调用stopService的话，Service会一直在后台运行，该Service的调用者再启动起来后可以通过stopService关闭Service。

所以调用startService的生命周期为：onCreate --> onStart (可多次调用) --> onDestroy

context.bindService()启动流程：

context.bindService()  -> onCreate()  -> onBind()  -> Service running  -> onUnbind()  -> onDestroy()  -> Service stop

onBind()将返回给客户端一个IBind接口实例，IBind允许客户端回调服务的方法，比如得到Service的实例、运行状态或其他操作。这个时候把调用者（Context，例如Activity）会和Service绑定在一起，Context退出了，Srevice就会调用onUnbind->onDestroy相应退出。
所以调用bindService的生命周期为：onCreate --> onBind(只一次，不可多次绑定) --> onUnbind --> onDestory。

在Service每一次的开启关闭过程中，只有onStart可被多次调用(通过多次startService调用)，其他onCreate，onBind，onUnbind，onDestory在一个生命周期中只能被调用一次。

![开启服务](picture\开启服务.png)

##### 2、使用startService方法开启服务的特点:

1.第一次开启服务时,先创建服务对象,在开启服务,调用的方法:oncreate,onStartCommand;

2.停止服务时是把服务对象销毁了;

3.服务可以被多次开启,多次开启时,只调用onStartCommand方法;

4.服务只能被停止一次,如果多次停止不会执行任何操作;

5.界面关闭后,服务仍然运行在后台;

三、 Service生命周期

Service的生命周期并不像Activity那么复杂，它只继承了onCreate()、onStart()、onDestroy()三个方法

当我们第一次启动Service时，先后调用了onCreate()、onStart()这两个方法；当停止Service时，则执行onDestroy()方法。

这里需要注意的是，如果Service已经启动了，当我们再次启动Service时，不会在执行onCreate()方法，而是直接执行onStart()方法。

它可以通过Service.stopSelf()方法或者Service.stopSelfResult()方法来停止自己，只要调用一次stopService()方法便可以停止服务，无论调用了多少次



### 3、利用服务实现电话窃听器




六、 拓展知识（进程和声明周期）
Android操作系统尝试尽可能长时间的保持应用的进程，但当可用内存很低时最终要移走一部分进程。怎样确定那些程序可以运行，那些要被销毁，Android让每一个进程在一个重要级的基础上运行，重要级低的进程最有可能被淘汰，一共有5级，下面这个列表就是按照重要性排列的：

1 一个前台进程显示的是用户此时需要处理和显示的。下列的条件有任何一个成立，这个进程都被认为是在前台运行的。
        a 与用户正发生交互的。
        b 它控制一个与用户交互的必须的基本的服务。
        c 有一个正在调用生命周期的回调函数的service（如onCreate()、onStar()、onDestroy()）
        d 它有一个正在运行onReceive()方法的广播接收对象。
	只有少数的前台进程可以在任何给定的时间内运行，销毁他们是系统万不得已的、最后的选择——当内存不够系统继续运行下去时。通常，在这一点上，设备已经达到了内存分页状态，所以杀掉一些前台进程来保证能够响应用户的需求。

2 一个可用进程没有任何前台组件，但它仍然可以影响到用户的界面。下面两种情况发生时，可以称该进程为可用进程。
        它是一个非前台的activity，但对用户仍然可用（onPause()方法已经被调用）这是可能发生的，例如：前台的activity是一个允许上一个activity可见的对话框，即当前activity半透明，能看到前一个activity的界面，它是一个服务于可用activity的服务。

3 一个服务进程是一个通过调用startService()方法启动的服务，并且不属于前两种情况。尽管服务进程没有直接被用户看到，但他们确实是用户所关心的，比如后台播放音乐或网络下载数据。所以系统保证他们的运行，直到不能保证所有的前台可见程序都正常运行时才会终止他们。

4 一个后台进程就是一个非当前正在运行的activity（activity的onStop()方法已经被调用），他们不会对用户体验造成直接的影响，当没有足够内存来运行前台可见程序时，他们将会被终止。通常，后台进程会有很多个在运行,所以他们维护一个LRU最近使用程序列表来保证经常运行的activity能最后一个被终止。如果一个activity正确的实现了生命周期的方法，并且保存它当前状态，杀死这些进程将不会影响到用户体验。

5 一个空线程没有运行任何可用应用程序组，保留他们的唯一原因是为了设立一个缓存机制，来加快组件启动的时间。系统经常杀死这些内存来平衡系统的整个系统的资源，进程缓存和基本核心缓存之间的资源。
Android把进程里优先级最高的activity或服务，作为这个进程的优先级。例如，一个进程拥有一个服务和一个可见的activity，那么这个进程将会被定义为可见进程，而不是服务进程。

此外，如果别的进程依赖某一个进程的话，那么被依赖的进程会提高优先级。一个进程服务于另一个进程，那么提供服务的进程不会低于获得服务的进程。例如，如果进程A的一个内容提供商服务于进程B的一个客户端，或者进程A的一个service被进程B的一个组件绑定，那么进程A至少拥有和进程B一样的优先级，或者更高。

因为一个运行服务的进程的优先级高于运行后台activity的进程，一个activity会准备一个长时间运行的操作来启动一个服务，而不是启动一个线程–尤其是这个操作可能会拖垮这个activity。例如后台播放音乐的同时，通过照相机向服务器发送一张照片，启动一个服务会保证这个操作至少运行在service 进程的优先级下，无论这个activity发生了什么，广播接收者应该作为一个空服务而不是简单的把耗时的操作单独放在一个线程里。

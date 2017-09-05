### APasswordView
APasswordView是一个密码输入视图，类似于微信或支付宝的支付密码视图。
可以设置文字颜色和大小、分割线颜色、密码的长度、以及背景圆角等。

* 实现原理
> 整个密码输入框最外层是一个水平方向的LinearLayout，内部包括显示密码的View，而多个显示密码的View中，第一个位置放置的是EditText，其余均为TextView

* Download [app-debug.apk](http://ovsv7o62g.bkt.clouddn.com/app-debug.apk) 

效果图：
1. AlonePasswordView

> ![AlonePasswordView](http://ovsv7o62g.bkt.clouddn.com/098CEA0770BA23322CE7E64455B60177.png)

* 在布局中添加AlonePasswordView
```
<com.koudai.library.AlonePasswordView
        android:id="@+id/pswView1"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_margin="15dp"
        app:BaseItemBackground="@drawable/mw_input_code_bg_1"
        app:BaseItemMargin="2dp"
        app:gpvGridColor="#ffffff"
        app:gpvLineColor="@color/test"
        app:gpvLineWidth="1dp"
        app:gpvPasswordLength="6"
        app:gpvPasswordTransformation="*"
        app:gpvPasswordType="numberPassword"
        app:gpvTextColor="#000000"
        app:gpvTextSize="25sp" />
```        
2. WholePasswordView
> ![WholePasswordView](http://ovsv7o62g.bkt.clouddn.com/4FEADB1D76A9D18ADB27E44CB23F7E92.png)
* 在布局中添加WholePasswordView
```
<com.koudai.library.WholePasswordView
        android:id="@+id/pswView2"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_margin="15dp"
        app:BaseCornerRadius="5dp"
        app:gpvGridColor="#ffffff"
        app:gpvLineColor="@color/test"
        app:gpvLineWidth="1dp"
        app:gpvPasswordLength="6"
        app:gpvPasswordTransformation="*"
        app:gpvPasswordType="numberPassword"
        app:gpvTextColor="#000000"
        app:gpvTextSize="25sp" />
```
### 参数说明
```
<declare-styleable name="basePasswordView">

        <attr name="gpvTextColor" format="color|reference" /> <!-- 设置输入密码的颜色 -->
        <attr name="gpvTextSize" format="dimension" /> <!-- 设置输入密码的大小 -->

        <attr name="gpvLineColor" format="color" /> <!-- 边框颜色 -->
        <attr name="gpvGridColor" format="color" /> <!-- 背景顔色 -->
        <attr name="gpvLineWidth" format="dimension" /> <!-- 边框宽度 -->

        <attr name="gpvPasswordLength" format="integer" /> <!-- 设置密码长度 -->
        <attr name="gpvPasswordTransformation" format="string" /> <!-- 你输入密码的时候显示的符号 -->

        <attr name="gpvPasswordType" format="enum"> <!-- 输入内容的类别 -->
            <enum name="numberPassword" value="0" />
            <enum name="textPassword" value="1" />
            <enum name="textVisiblePassword" value="2" />
            <enum name="textWebPassword" value="3" />
        </attr>

        <attr name="BaseItemBackground" format="reference" /> <!-- 设置输入密码框的背景 -->
        <attr name="BaseItemMargin" format="dimension" /> <!-- 边框间距 -->
        <attr name="BaseCornerRadius" format="dimension" /> <!-- 背景圆角 -->

 </declare-styleable>
 ```           

感谢[项目GridPasswordView](https://github.com/Jungerr/GridPasswordView)，本项目核心代码来源此.
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
 #指定代码的压缩级别
    -optimizationpasses 5

    #包明不混合大小写
    -dontusemixedcaseclassnames

    #不去忽略非公共的库类
    -dontskipnonpubliclibraryclasses

     #优化  不优化输入的类文件
    -dontoptimize

     #预校验
    -dontpreverify

     #混淆时是否记录日志
    -verbose

     # 混淆时所采用的算法
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

    #保护注解
    -keepattributes *Annotation*

    # 保持哪些类不被混淆
    -keep public class * extends android.app.Fragment
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class com.android.vending.licensing.ILicensingService
    #如果有引用v4包可以添加下面这行
    -keep public class * extends android.support.v4.app.Fragment



    #忽略警告
    -ignorewarning

    ##记录生成的日志数据,gradle build时在本项目根目录输出##

    #apk 包内所有 class 的内部结构
    -dump class_files.txt
    #未混淆的类和成员
    -printseeds seeds.txt
    #列出从 apk 中删除的代码
    -printusage unused.txt
    #混淆前后的映射
    -printmapping mapping.txt

    ########记录生成的日志数据，gradle build时 在本项目根目录输出-end######


    #####混淆保护自己项目的部分代码以及引用的第三方jar包library#######

    #-libraryjars libs/umeng-analytics-v5.2.4.jar

    #三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
    #-libraryjars libs/sdk-v1.0.0.jar
    #-libraryjars libs/look-v1.0.1.jar

    #如果不想混淆 keep 掉
    -keep class com.lippi.recorder.iirfilterdesigner.** {*; }
    #友盟
    -keep class com.umeng.**{*;}
    #项目特殊处理代码

    #忽略警告
    -dontwarn com.lippi.recorder.utils**
    #保留一个完整的包
    -keep class com.lippi.recorder.utils.** {
        *;
     }

    -keep class  com.lippi.recorder.utils.AudioRecorder{*;}


    #如果引用了v4或者v7包
    -dontwarn android.support.**

    ####混淆保护自己项目的部分代码以及引用的第三方jar包library-end####

    -keep public class * extends android.view.View {
        public <init>(android.content.Context);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
        public void set*(...);
    }

    #保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
        native <methods>;
    }

    #保持自定义控件类不被混淆
    -keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
    }

    #保持自定义控件类不被混淆
    -keepclassmembers class * extends android.app.Activity {
       public void *(android.view.View);
    }

    #保持 Parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
    }

    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable

    #保持 Serializable 不被混淆并且enum 类也不被混淆
    -keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
    }

    #保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
    -keepclassmembers enum * {
      public static **[] values();
     public static ** valueOf(java.lang.String);
   }

    -keepclassmembers class * {
        public void *ButtonClicked(android.view.View);
    }

    #不混淆资源类
    -keepclassmembers class **.R$* {
        public static <fields>;
    }
        #避免混淆 异常, 内部类, 泛型 等
         -keepattributes Exceptions
        -keepattributes         InnerClasses
        -keepattributes Signature
       -keepattributes      Deprecated
       -keepattributes     SourceFile
     -keepattributes   LineNumberTable
      -keepattributes      *Annotation*
      -keepattributes      EnclosingMethod


    #如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
    #gson
    #-libraryjars libs/gson-2.2.2.jar

    # Gson specific classes
    -keep class sun.misc.Unsafe { *; }
    # Application classes that will be serialized/deserialized over Gson
    -keep class com.google.gson.examples.android.model.** { *; }

    #混淆自己的javabean
    -keep class com.betterda.xsnano.javabean.** { *; }
    -keep class com.betterda.xsnano.bus.model.** { *; }




    #百度地图避免混淆
    -keep class com.baidu.mapapi.** {*;}

    -keep class com.baidu.** {*;}
    -keep class vi.com.** {*;}
    -dontwarn com.baidu.**


    ################### region for xUtils
    -keepattributes Signature,*Annotation*
    -keep public class org.xutils.** {
        public protected *;
    }
    -keep public interface org.xutils.** {
        public protected *;
    }
    -keepclassmembers class * extends org.xutils.** {
        public protected *;
    }
    -keepclassmembers @org.xutils.db.annotation.* class * {*;}
    -keepclassmembers @org.xutils.http.annotation.* class * {*;}
    -keepclassmembers class * {
        @org.xutils.view.annotation.Event <methods>;
    }
    #################### end region

    ##mob
    -keep class android.net.http.SslError
    -keep class android.webkit.**{*;}
    -keep class cn.sharesdk.**{*;}
    -keep class cn.smssdk.**{*;}
    -keep class com.mob.**{*;}

    # Keep our interfaces so they can be used by other ProGuard rules.
    # See http://sourceforge.net/p/proguard/bugs/466/ fresco的混淆
    -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

    # Do not strip any method/class that is annotated with @DoNotStrip
    -keep @com.facebook.common.internal.DoNotStrip class *
    -keepclassmembers class * {
        @com.facebook.common.internal.DoNotStrip *;
    }

    # Keep native methods
    -keepclassmembers class * {
        native <methods>;
    }

    -dontwarn okio.**
    -dontwarn com.squareup.okhttp.**
    -dontwarn okhttp3.**
    -dontwarn javax.annotation.**
    -dontwarn com.android.volley.toolbox.**

    # Works around a bug in the animated GIF module which will be fixed in 0.12.0
    -keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
        public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
    }
    ### end

       # 百度地图 begin
    -keep class com.baidu.** { *; }
    -keep class vi.com.gdi.bgl.android.**{*;}

    # end

    #民生银行 混淆
    -keep  public class com.unionpay.uppay.net.HttpConnection {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.net.HttpParameters {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.BankCardInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.PAAInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.ResponseInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.model.PurchaseInfo {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.util.DeviceInfo {
    	public <methods>;
    }
    -keep  public class java.util.HashMap {
    	public <methods>;
    }
    -keep  public class java.lang.String {
    	public <methods>;
    }
    -keep  public class java.util.List {
    	public <methods>;
    }
    -keep  public class com.unionpay.uppay.util.PayEngine {
    	public <methods>;
    	native <methods>;
    }
    #end
#glide start
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#-keep resourcexmlelements manifest/application/meta-data@value=GlideModule

#glide end

#jpush start
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#end
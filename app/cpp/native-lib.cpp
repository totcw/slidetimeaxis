#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_lyf_bookreader_utils_NativeHelper_getAppKey(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

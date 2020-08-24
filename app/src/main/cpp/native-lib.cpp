#include <jni.h>
#include <string>
#include "offer.h"

extern "C" JNIEXPORT jint JNICALL
Java_com_example_codinginterviews_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_wanandroid_ui_arithmetic_ArithmeticDetailActivity_getResult(JNIEnv *env,
                                                                             jobject thiz) {
    // TODO: implement getResult()
    int *numbers = new int[8]{2, 3, 5, 4, 3, 2, 6, 7};
    int result = getDuplication(numbers,8);
    return result;
}
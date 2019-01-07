#include "VSDJNI.h"
#include "vsd.h"

JNIEXPORT jdouble JNICALL Java_com_nfsindustries_vsd_VSDJNI_processAudio(JNIEnv* env, jobject obj, jdoubleArray array)
{
      jdouble* body = env->GetDoubleArrayElements(array, 0);
      jdouble* inputAudioBuffer = env->GetDoubleArrayElements(array, 0);
      double stressFrequency = vsd(inputAudioBuffer, 8000);
      env->ReleaseDoubleArrayElements(array, body, 0);
      return stressFrequency;
}

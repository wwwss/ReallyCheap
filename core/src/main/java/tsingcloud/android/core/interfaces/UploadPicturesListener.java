package tsingcloud.android.core.interfaces;

public interface UploadPicturesListener {
    void onUploadSuccess(String result);
    void onUploadStart();
    void onUploadFailure();
}

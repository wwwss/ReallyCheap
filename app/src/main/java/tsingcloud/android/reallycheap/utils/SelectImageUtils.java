package tsingcloud.android.reallycheap.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tsingcloud.android.reallycheap.widgets.view.ActionSheetDialog;

/**
 * Created by admin on 2016/4/16.
 */
public class SelectImageUtils implements ActionSheetDialog.OnSheetItemClickListener {
    private Fragment fragment = null;
    private Activity activity = null;
    private ActionSheetDialog alertDialog;
    public String imagePath;
    public static final int CAMERA = 0;
    public static final int GALLERY = 1;


//    public SelectImageUtils(Fragment fragment) {
//        this.fragment = fragment;
//    }

    public  SelectImageUtils(Activity activity) {
        this.activity = activity;
    }

    public void showSelectDialog() {
        if (fragment != null)
            alertDialog = new ActionSheetDialog(fragment.getActivity()).builder();
        else
            alertDialog = new ActionSheetDialog(activity).builder();
        alertDialog.addSheetItem("拍照", null, this);
        alertDialog.addSheetItem("从相册中选取", null, this);
        alertDialog.show();
    }

    @Override
    public void onClick(int which) {
        Intent intent;
        switch (which) {
            // 拍照
            case 1:
                try {
                    intent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(getFile()));
                    if (fragment != null)
                        fragment.startActivityForResult(intent, CAMERA);
                    else
                        activity.startActivityForResult(intent, CAMERA);
                } catch (Exception e) {

                }
                break;
            // 相册
            case 2:
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                if (fragment != null)
                    fragment.startActivityForResult(intent, GALLERY);
                else
                    activity.startActivityForResult(intent, GALLERY);
                break;
        }
    }

    /**
     * 获取拍照图片临时文件
     *
     * @return 图片文件
     * @throws IOException
     */
    private File getFile() throws IOException {
        String packageName;
        if (fragment != null)
            packageName = fragment.getActivity().getPackageName();
        else
            packageName = activity.getPackageName();
        String parentPath = Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + File.separator + packageName;
        File parent = new File(parentPath);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        imagePath = parentPath + File.separator + dateFormat.format(date)
                + ".jpg";
        File mPhotoFile = new File(imagePath);
        if (!mPhotoFile.exists()) {
            mPhotoFile.createNewFile();
        }
        return mPhotoFile;
    }
}

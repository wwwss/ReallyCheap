package tsingcloud.android.reallycheap.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class PathUtils {
	
	public static String getUriPath(Intent data,Activity activity ){
		Uri uri = data.getData();
		if (uri == null) {
			return null;
		}
		String imagePath;
		String[] filePathColumns = { MediaStore.Images.Media.DATA };
		Cursor c = activity.getContentResolver().query(uri,
				filePathColumns, null, null, null);
		if (c != null) {
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			imagePath = c.getString(columnIndex);
			c.close();
		} else {
			File file = new File(uri.getPath());
			imagePath = file.getAbsolutePath();
		}
		return imagePath;
	}
}

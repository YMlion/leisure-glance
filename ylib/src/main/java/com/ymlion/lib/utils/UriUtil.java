package com.ymlion.lib.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Uri相关工具类
 *
 * @author YML
 * @date 10/19/2016
 */

public class UriUtil {

    /**
     * Gets the corresponding path to a file from the given URI
     *
     * @param uri The URI to find the file path from
     * @param contentResolver The content resolver to use to perform the query.
     * @return the file path as a string
     */
	public static String getPathFromUri(@NonNull Uri uri, ContentResolver contentResolver) {
		String filePath = null;
		String scheme = uri.getScheme();

		if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			String[] filePathColumn = { MediaStore.MediaColumns.DATA };
			Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				filePath = cursor.getString(columnIndex);
				cursor.close();
			}
		} else {
            filePath = uri.getPath();
        }
		return filePath;
	}

	/**
	 * Gets the content:// URI from the given corresponding path to a file
	 *
	 * @param context
	 *            场景
	 * @param file
	 *            文件
	 * @return content Uri
	 */
    public static Uri getContentUri(Context context, File file) {
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            if (!cursor.isClosed()) {
                cursor.close();
            }
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (file.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}

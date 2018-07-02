package cn.suanzi.newdemo.Util;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cn.suanzi.newdemo.pojo.video.VideoItem;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_MODIFIED;
import static android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Video.VideoColumns.BUCKET_ID;
import static android.provider.MediaStore.Video.VideoColumns.DURATION;

/**
 * Created by liyanfang on 2018/6/19.
 * 描述：
 */
public class DataUtils {

    public static List<VideoItem> getVideoItem (Cursor data) {
        List<VideoItem> items = new ArrayList<>();
        VideoItem item = null;
        while (data.moveToNext()) {
            String folderId = data.getString(data.getColumnIndexOrThrow(BUCKET_ID));
            String folderName = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
            int videoId = data.getInt(data.getColumnIndexOrThrow(_ID));
            String name = data.getString(data.getColumnIndexOrThrow(DISPLAY_NAME));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            long duration = data.getLong(data.getColumnIndexOrThrow(DURATION));
            long size = data.getLong(data.getColumnIndexOrThrow(SIZE));
            long modified = data.getLong(data.getColumnIndexOrThrow(DATE_MODIFIED));
            item = new VideoItem(videoId,name,path,size,modified,duration);
//            folder = new VideoFolder();
//            folder.setId(folderId);
//            folder.setName(folderName);
//            if(folders.contains(folder)){
//                folders.get(folders.indexOf(folder)).addItem(item);
//            }else{
//                folder.addItem(item);
//                folders.add(folder);
//            }
            items.add(item);
//            sum_size += size;
        }
        return items;
    }
}

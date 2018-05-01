package org.cloud.yblog.service;

import org.cloud.yblog.model.Album;
import org.cloud.yblog.model.Gallery;

import java.util.List;

/**
 * Created by d05660ddw on 2017/4/12.
 */
public interface IGalleryService extends IService<Gallery> {

    long saveOrUpdateGallery(Gallery gallery);

    List<Gallery> getAllByOrder(String orderColumn, String orderDir, int startIndex, int pageSize);

    List<Gallery> getGalleryByAlbum(Album album);

    List<Gallery> getAllGallery(int pageNum, int pageSize);
}

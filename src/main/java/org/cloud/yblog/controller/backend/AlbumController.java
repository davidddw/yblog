/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.cloud.yblog.controller.backend;

import org.cloud.yblog.constant.UrlConstants;
import org.cloud.yblog.controller.base.AdminBaseController;
import org.cloud.yblog.controller.exception.ResourceNotFoundException;
import org.cloud.yblog.model.Album;
import org.cloud.yblog.model.Gallery;
import org.cloud.yblog.model.ImageData;
import org.cloud.yblog.model.ResponseMessage;
import org.cloud.yblog.service.IAlbumService;
import org.cloud.yblog.service.IGalleryService;
import org.cloud.yblog.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by d05660ddw on 2017/3/20.
 */
@RestController
public class AlbumController extends AdminBaseController {

    @Autowired
    private IAlbumService albumService;

    @Autowired
    private IGalleryService galleryService;

    /**
     * 新增
     */
    @PostMapping("/album")
    public
    @ResponseBody
    ResponseMessage addAlbum(@RequestBody Album album) {
        long number = albumService.saveOrUpdateAlbum(album);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 更新
     */
    @PutMapping("/album/{id}")
    public
    @ResponseBody
    ResponseMessage updateAlbum(@PathVariable String id, @RequestBody Album album) {
        album.setId(Long.parseLong(id));
        long number = albumService.saveOrUpdateAlbum(album);
        return new ResponseMessage(String.valueOf(number));
    }

    /**
     * 查询
     */
    @GetMapping("/album")
    public
    @ResponseBody
    Map<String, Object> listAlbum(@RequestParam String draw,
                                  @RequestParam int startIndex,
                                  @RequestParam int pageSize,
                                  @RequestParam(value = "orderColumn", required = false, defaultValue = "id") String orderColumn,
                                  @RequestParam(value = "orderDir", required = false, defaultValue = "asc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        info.put("pageData", albumService.getAllByOrder(orderColumn, orderDir, startIndex, pageSize));
        info.put("total", albumService.getCount(new Album()));
        info.put("draw", draw);
        return info;
    }

    /**
     * 删除，除了第一个其他允许删除
     */
    @DeleteMapping("/album/{id}")
    public
    @ResponseBody
    ResponseMessage removeAlbum(@PathVariable String id) {
        Album album = albumService.getById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        return new ResponseMessage(String.valueOf(albumService.deleteById(album.getId())));
    }

    /**
     * 批量删除，除了第一个其他允许删除
     */
    @PostMapping("/album/delete")
    public
    @ResponseBody
    ResponseMessage removeAlbums(@RequestBody List<String> ids) {
        int sum = 0;
        for(String id : ids) {
            Album album = albumService.getById(Long.parseLong(id))
                    .orElseThrow(() -> new ResourceNotFoundException("Album", id));
            sum += albumService.deleteById(album.getId());
        }
        return new ResponseMessage(String.valueOf(sum));
    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadSingleFile", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public boolean uploadFileHandler(@RequestParam("avatar1") MultipartFile file, HttpServletRequest request) {
        ImageData imageData = FileUtil.uploadSingleFile(file, request, false);
//        albumService.saveOrUpdateAlbum()
//        Gallery gallery = new Gallery(imageData.getCaption(), imageData.getSize(), imageData.getImage(), imageData.getThumb());
//        long num = galleryService.saveOrUpdateGallery(gallery);
        return true;
    }

    /**
     * Upload multiple file using Spring Controller
     */
    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public boolean uploadMultipleFileHandler(@RequestParam("album") String album,@RequestParam("file") MultipartFile[] files,
                                             HttpServletRequest request) throws IOException {
        List<ImageData> list = FileUtil.uploadMultipleFiles(files, request);
        List<Long> ret = new ArrayList<>();
        for (ImageData imageData : list) {
            Album albumset = albumService.getById(Long.parseLong(album))
                    .orElseThrow(() -> new ResourceNotFoundException("Album", album));
            Gallery gallery = new Gallery(imageData.getCaption(), imageData.getSize(), imageData.getImage(), imageData.getThumb());
            gallery.setAlbum(albumset);
            ret.add(galleryService.saveOrUpdateGallery(gallery));
        }
        return ret.size() > 0;
    }

    /**
     * 查询
     */
    @GetMapping("/gallery/{id}")
    public
    @ResponseBody
    Map<String, Object> listGalleryByAlbum(@PathVariable String id) {
        Map<String, Object> info = new HashMap<>();
        Album album = albumService.getById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        List<Gallery> galleryList = galleryService.getGalleryByAlbum(album);
        String[] initialPreview = new String[galleryList.size()];
        for (int i = 0; i < galleryList.size(); i++) {
            initialPreview[i] = getBasePath() + galleryList.get(i).getThumbRelativePath();
        }
        info.put("initialPreview", initialPreview);
        List<ImageData> imageDataList = new ArrayList<>();
        for (Gallery gallery : galleryList) {
            imageDataList.add(new ImageData(gallery.getId(), gallery.getName(), gallery.getSize(), "120px", "/admin/gallery/delete"));
        }
        info.put("initialPreviewConfig", imageDataList.toArray());
        info.put("append", true);
        return info;
    }

    /**
     * 删除
     */
    @PostMapping("/gallery/delete")
    public
    @ResponseBody
    ResponseMessage removeGallery(@RequestParam String key) {
        Gallery gallery = galleryService.getById(Long.parseLong(key))
                .orElseThrow(() -> new ResourceNotFoundException("Album", key));
        FileUtil.delete(getUploadRoot() + gallery.getImageRelativePath());
        FileUtil.delete(getUploadRoot() + gallery.getThumbRelativePath());
        return new ResponseMessage(String.valueOf(galleryService.deleteById(gallery.getId())));
    }
}

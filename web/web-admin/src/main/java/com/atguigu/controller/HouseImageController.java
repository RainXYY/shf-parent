package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * Date:2022/5/24
 * Author:夏宇
 * Description:
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    private final static String PAGE_UPLOAD_SHOW = "house/upload";
    private static final String SHOW_ACTION = "redirect:/house/";


    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable("houseId") Long houseId,
                             @PathVariable("type") Integer type, Model model){
        //将houseId和type传递到图片上传页面
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return PAGE_UPLOAD_SHOW;
    }

    @ResponseBody
    @PostMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId,
                         @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) throws IOException {
        //houseId就是当前图片所属的房源的id
        //type是图片的类型:1或者2
        //multipartFile就是上传的图片
        //将图片上传到七牛云
        for (MultipartFile multipartFile : files) {
            //1. 获取当前图片名
            String originalFilename = multipartFile.getOriginalFilename();
                //并且生成一个唯一的图片名
            String uuidName = FileUtil.getUUIDName(originalFilename);
            //2. 使用工具类将文件上传到七牛云
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(),uuidName);
            //3. 获取图片路径
            String imageUrl = QiniuUtils.getUrl(uuidName);
            //封装图片信息
            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setImageName(uuidName);
            houseImage.setImageUrl(imageUrl);
            houseImage.setType(type);
            //4. 调用业务层的方法保存图片信息
            houseImageService.insert(houseImage);
        }
        return Result.ok();
    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id){
        //1. 从七牛云中删除图片
        //根据id从数据库获取图片信息(包含图片名)
        HouseImage houseImage = houseImageService.getById(id);
        //根据图片名从七牛云中删除图片
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
        //2. 从数据库中删除图片信息
        houseImageService.delete(id);
        //3. 重定向访问详情页面
        return SHOW_ACTION + houseId;
    }
}


package com.personal.info.myInfoSystem.modular.orc;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.RtfWriter2;
import com.personal.info.myInfoSystem.modular.system.entity.TextDetection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/orc/deal/")
public class DealContextController {

    private static final String filepath = "/Users/huangshuang/Documents/orc处理后的文本/中寨黄姓氏修谱.doc";

    @PostMapping("context")
    public ResponseData getPictureContext(List<TextDetection> textDetections) {

        //设置纸张大小
        Document document = new Document(PageSize.A4);

        try {
            for (int i = 1; i < textDetections.size() - 2; i++) {

                //创建word文档                                                                                             
                RtfWriter2.getInstance(document, new FileOutputStream(filepath));
                //打开文档
                document.open();
                //创建段落
                Paragraph p = new Paragraph("", new Font(Font.NORMAL, 10, Font.BOLD, new Color(0, 0, 0)));
                //设置段落为居中对齐
                p.setAlignment(Paragraph.ALIGN_CENTER);
                //写入段落
                document.add(p);
            }
            //关流
            document.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return null;
    }


}
